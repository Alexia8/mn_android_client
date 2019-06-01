package com.alexiacdura.mn_core.core.factory

import com.alexiacdura.mn_core.core.factory.interceptors.LocalDateTimeTypeAdapter
import com.alexiacdura.mn_core.core.factory.interceptors.UrlTypeAdapter
import com.alexiacdura.mn_core.core.rx.SchedulersProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.threeten.bp.LocalDateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

/**
 * This implements the [ServiceFactory] by providing basic interceptors and GSON adapters
 */
internal open class DefaultServiceFactory<T>(
    private val schedulersProvider: SchedulersProvider,
    private val clientBuilderFactory: OKHttpClientBuilderFactory,
    private val serviceClass: Class<T>,
    private val baseUrl: String
) : ServiceFactory<T> {
    override fun createService(jsonAdapters: List<Any>): T {
        val okHttpClient = createOKHttpClient()
        val retrofit = createRetrofit(okHttpClient)
        return retrofit.create(serviceClass)
    }

    private fun createOKHttpClient(): OkHttpClient {
        return clientBuilderFactory.createBuilder().build()
    }

    private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val rxCallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(schedulersProvider.io())
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(buildJsonConverter()))
            .addCallAdapterFactory(rxCallAdapterFactory)
            .build()
    }

    open fun buildJsonConverter(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(URL::class.java, UrlTypeAdapter())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
            .create()
    }
}