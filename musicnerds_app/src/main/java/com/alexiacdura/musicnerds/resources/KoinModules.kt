package com.alexiacdura.musicnerds.resources

import com.alexiacdura.mn_core.core.factory.DefaultOKHttpClientBuilder
import com.alexiacdura.mn_core.core.factory.OKHttpClientBuilderFactory
import com.alexiacdura.mn_core.core.koin.coreKoinModules
import com.alexiacdura.mn_core.core.rx.SchedulersProvider
import com.alexiacdura.mn_ui.core.koin.uiKoinModules
import com.alexiacdura.musicnerds.App
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

object KoinModules {
    @JvmField
    val appModule = module {
        single { AndroidSchedulersProvider() as SchedulersProvider }
        single {
            DefaultOKHttpClientBuilder(
                initialNetworkInterceptors = emptyList()
            ) as OKHttpClientBuilderFactory
        }
    }

    @JvmStatic
    fun setup(app: App) {
        startKoin {
            logger(AndroidLogger())
            androidContext(app)
            modules(listOf(appModule, coreKoinModules, uiKoinModules))
        }
    }
}