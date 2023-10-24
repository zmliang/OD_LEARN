package com.zml.nohttp

import com.zml.nohttp.exception.RouteException
import java.io.IOException

class ExchangeFinder(
    private val connectionPool: RealConnectionPool,
    internal val address: Address,
    private val call: RealCall,
) {
    //private var routeSelection: RouteSelector.Selection? = null
    //private var routeSelector: RouteSelector? = null
    private var refusedStreamCount = 0
    private var connectionShutdownCount = 0
    private var otherFailureCount = 0
    //private var nextRouteToTry: Route? = null

    fun find(
        client: NoHttpClient,
        chain: RealInterceptorChain
    ): ExchangeCodec{
        try {
            val resultConnection = findHealthyConnection(
                connectTimeout = chain.connectTimeoutMillis(),
                readTimeout = chain.readTimeoutMillis(),
                writeTimeout = chain.writeTimeoutMillis(),
                pingIntervalMillis = client.pingIntervalMillis,
                connectionRetryEnabled = client.retryOnConnectionFailure,
                doExtensiveHealthChecks = chain.request.method != "GET"
            )
            return resultConnection.newCodec(client, chain)
        } catch (e: RouteException) {
            //trackFailure(e.lastConnectException) todo
            throw e
        } catch (e: IOException) {
            //trackFailure(e) todo
            throw RouteException(e)
        }
    }

    @Throws(IOException::class)
    private fun findHealthyConnection(
        connectTimeout: Int,
        readTimeout: Int,
        writeTimeout: Int,
        pingIntervalMillis: Int,
        connectionRetryEnabled: Boolean,
        doExtensiveHealthChecks: Boolean
    ): RealConnection{
        while (true) {
            val candidate = findConnection(
                connectTimeout = connectTimeout,
                readTimeout = readTimeout,
                writeTimeout = writeTimeout,
                pingIntervalMillis = pingIntervalMillis,
                connectionRetryEnabled = connectionRetryEnabled
            )


//            if (candidate.isHealthy(doExtensiveHealthChecks)) {
//                return candidate
//            }
//
//
//            candidate.noNewExchanges()
//
//            if (nextRouteToTry != null) continue
//
//            val routesLeft = routeSelection?.hasNext() ?: true
//            if (routesLeft) continue
//
//            val routesSelectionLeft = routeSelector?.hasNext() ?: true
//            if (routesSelectionLeft) continue

            throw IOException("exhausted all routes")
        }
    }

    fun findConnection(
        connectTimeout: Int,
        readTimeout: Int,
        writeTimeout: Int,
        pingIntervalMillis: Int,
        connectionRetryEnabled: Boolean,
    ):RealConnection{

        return RealConnection()
    }

}