package com.alexiacdura.mn_core.core.utils

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import java.util.ArrayList
import java.util.concurrent.TimeUnit.MINUTES

object OKHttpClientUtils {
    private const val KEEP_ALIVE_DURATION_MIN = 5L

    @JvmStatic
    fun fixKnownBugs(builder: OkHttpClient.Builder) {
        // works around this issue: https://github.com/square/okhttp/issues/3146
        val protocolList = ArrayList<Protocol>()
        protocolList.add(Protocol.HTTP_1_1)
        builder.connectionPool(ConnectionPool(0,
            KEEP_ALIVE_DURATION_MIN, MINUTES))
            .protocols(protocolList)
    }
}