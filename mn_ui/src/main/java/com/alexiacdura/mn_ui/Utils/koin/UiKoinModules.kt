package com.alexiacdura.mn_ui.Utils.koin

import com.alexiacdura.mn_core.core.koin.KoinNames
import com.alexiacdura.mn_core.core.koin.KoinScopes
import com.alexiacdura.mn_core.resources.FileAssets
import com.alexiacdura.mn_ui.Utils.resources.AndroidFileAssets
import org.koin.android.BuildConfig
import org.koin.dsl.module

val uiKoinModules = module {

    single { AndroidFileAssets(get()) as FileAssets }

    single(KoinNames.MUSICNERDS_BASE_URL) { "https://mncoreservice20190513103648.azurewebsites.net/" }

    single(KoinNames.MUSICNERDS_IS_DEBUG) { BuildConfig.DEBUG }

    /**scope(KoinScopes.DISCOVER_LISTING_DETAIL_VIEW) {
    viewModel { ListingDetailViewModel(interactor = get(), application = get(), schedulersProvider = get(), router = get()) }
    }*/

}