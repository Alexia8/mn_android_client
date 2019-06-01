package com.alexiacdura.mn_core.core.factory

import com.alexiacdura.mn_core.core.utils.OKHttpClientUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient.Builder
import java.util.concurrent.TimeUnit.SECONDS

/**
 * Default implementation of the OKHttpClient.Builder factory
 * It provides a default builder with common timeout values,
 * it also receive a set of initial network and application interceptors
 * Anything that is common to any module in the app was added here.
 */

class DefaultOKHttpClientBuilder(
    initialNetworkInterceptors: List<Interceptor> = listOf(),
    initialApplicationInterceptors: List<Interceptor> = listOf()
) : OKHttpClientBuilderFactory {
    private val networkInterceptors: MutableList<Interceptor> = initialNetworkInterceptors.toMutableList()
    private val applicationInterceptors: MutableList<Interceptor> = initialApplicationInterceptors.toMutableList()

    override fun addNetworkInterceptor(interceptor: Interceptor) {
        networkInterceptors.add(interceptor)
    }

    override fun addApplicationInterceptor(interceptor: Interceptor) {
        applicationInterceptors.add(interceptor)
    }

    override fun createBuilder(): Builder {
        return Builder().apply {
            connectTimeout(OKHttpClientBuilderFactory.TIMEOUT_SECONDS, SECONDS)
            readTimeout(OKHttpClientBuilderFactory.TIMEOUT_SECONDS, SECONDS)
            networkInterceptors.forEach { addNetworkInterceptor(it) }
            applicationInterceptors.forEach { addInterceptor(it) }
            OKHttpClientUtils.fixKnownBugs(this)
        }
    }
}