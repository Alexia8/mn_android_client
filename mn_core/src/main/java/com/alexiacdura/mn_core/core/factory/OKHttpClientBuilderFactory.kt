package com.alexiacdura.mn_core.core.factory

import okhttp3.Interceptor
import okhttp3.OkHttpClient.Builder

/**
 * Class to abstract the creation of an OKHttpClient.Builder
 * and provides functions to add interceptors before the Builder is created.
 */
interface OKHttpClientBuilderFactory {
    fun addNetworkInterceptor(interceptor: Interceptor)
    fun addApplicationInterceptor(interceptor: Interceptor)
    fun createBuilder(): Builder

    companion object {
        const val TIMEOUT_SECONDS = 60L
    }
}