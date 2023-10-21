package com.zml.nohttp.interceptors

import com.zml.nohttp.*
import com.zml.nohttp.exception.RouteException
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ProtocolException
import java.net.SocketTimeoutException
import java.security.cert.CertificateException
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLPeerUnverifiedException

class RetryAndFollowUpInterceptor(private val client: NoHttpClient):Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val realChain = chain as RealInterceptorChain
        var request = realChain.request
        val call = realChain.call
        var followUpCount = 0
        var priorResponse: Response? = null
        var newExchangeFinder = true
        var recoveredFailures = listOf<IOException>()

        while (true){
            call.enterNetworkInterceptorExchange(request, newExchangeFinder)

            var response: Response
            var closeActiveExchange = true
            try {
                if (call.isCanceled()){
                    throw IOException("call is canceled")
                }
                try {
                    response = realChain.processed(request)
                    newExchangeFinder = true
                } catch (e: RouteException) {
                    // The attempt to connect via a route failed. The request will not have been sent.
                    if (!recover(e.lastConnectException, call, request, requestSendStarted = false)) {
                        throw e.firstConnectException.withSuppressed(recoveredFailures)
                    } else {
                        recoveredFailures += e.firstConnectException
                    }
                    newExchangeFinder = false
                    continue
                } catch (e: IOException) {

                    if (!recover(e, call, request, requestSendStarted = e !is ConnectionShutdownException)) {
                        throw e.withSuppressed(recoveredFailures)
                    } else {
                        recoveredFailures += e
                    }
                    newExchangeFinder = false
                    continue
                }

                if (priorResponse != null) {
                    response = response.newBuilder()
                        .priorResponse(priorResponse.newBuilder()
                            .body(null)
                            .build())
                        .build()
                }

                val exchange = call.interceptorScopedExchange
                val followUp = followUpRequest(response, exchange)

                if (followUp == null) {
                    if (exchange != null && exchange.isDuplex) {
                        call.timeoutEarlyExit()
                    }
                    closeActiveExchange = false
                    return response
                }

                val followUpBody = followUp.body
                if (followUpBody != null && followUpBody.isOneShot()) {
                    closeActiveExchange = false
                    return response
                }

                response.body?.closeQuietly()

                if (++followUpCount > MAX_FOLLOW_UPS) {
                    throw ProtocolException("Too many follow-up requests: $followUpCount")
                }

                request = followUp
                priorResponse = response

            }finally {
                call.exitNetworkInterceptorExchange(closeActiveExchange)
            }
        }
    }

    private fun requestIsOneShot(e: IOException, userRequest: Request): Boolean {
        val requestBody = userRequest.body
        return (requestBody != null && requestBody.isOneShot()) ||
                e is FileNotFoundException
    }

    private fun isRecoverable(e: IOException, requestSendStarted: Boolean): Boolean {
        // If there was a protocol problem, don't recover.
        if (e is ProtocolException) {
            return false
        }

        // If there was an interruption don't recover, but if there was a timeout connecting to a route
        // we should try the next route (if there is one).
        if (e is InterruptedIOException) {
            return e is SocketTimeoutException && !requestSendStarted
        }

        // Look for known client-side or negotiation errors that are unlikely to be fixed by trying
        // again with a different route.
        if (e is SSLHandshakeException) {
            // If the problem was a CertificateException from the X509TrustManager,
            // do not retry.
            if (e.cause is CertificateException) {
                return false
            }
        }
        if (e is SSLPeerUnverifiedException) {
            // e.g. a certificate pinning error.
            return false
        }
        // An example of one we might want to retry with a different route is a problem connecting to a
        // proxy and would manifest as a standard IOException. Unless it is one we know we should not
        // retry, we return true and try a new route.
        return true
    }


    private fun recover(e: IOException,
                        call: RealCall,
                        userRequest: Request,
                        requestSendStarted: Boolean
    ): Boolean {
        // 用户禁止重定向
        if (!client.retryOnConnectionFailure) {
            return false
        }

        if (requestSendStarted && requestIsOneShot(e, userRequest)) {
            return false
        }

        if (!isRecoverable(e, requestSendStarted)) {
            return false
        }




        return true
    }


    companion object {

        private const val MAX_FOLLOW_UPS = 20
    }
}