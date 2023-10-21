package com.zml.nohttp

import com.zml.nohttp.exception.RouteException
import java.io.IOException

class ExchangeFinder(
    private val connectionPool: RealConnectionPool,
    internal val address: Address,
    private val call: RealCall,
) {
    private var routeSelection: RouteSelector.Selection? = null
    private var routeSelector: RouteSelector? = null
    private var refusedStreamCount = 0
    private var connectionShutdownCount = 0
    private var otherFailureCount = 0
    private var nextRouteToTry: Route? = null

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
            trackFailure(e.lastConnectException)
            throw e
        } catch (e: IOException) {
            trackFailure(e)
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

            // Confirm that the connection is good.
            if (candidate.isHealthy(doExtensiveHealthChecks)) {
                return candidate
            }

            // If it isn't, take it out of the pool.
            candidate.noNewExchanges()

            // Make sure we have some routes left to try. One example where we may exhaust all the routes
            // would happen if we made a new connection and it immediately is detected as unhealthy.
            if (nextRouteToTry != null) continue

            val routesLeft = routeSelection?.hasNext() ?: true
            if (routesLeft) continue

            val routesSelectionLeft = routeSelector?.hasNext() ?: true
            if (routesSelectionLeft) continue

            throw IOException("exhausted all routes")
        }
    }

}