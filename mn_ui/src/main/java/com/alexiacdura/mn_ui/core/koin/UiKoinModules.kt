package com.alexiacdura.mn_ui.core.koin

import com.alexiacdura.mn_core.core.koin.KoinNames
import com.alexiacdura.mn_core.resources.FileAssets
import com.alexiacdura.mn_ui.BuildConfig
import com.alexiacdura.mn_ui.MusicnerdsRouter
import com.alexiacdura.mn_ui.core.utils.resources.AndroidFileAssets
import com.alexiacdura.mn_ui.ui.feed.FeedViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiKoinModules = module {
    single { AndroidFileAssets(get()) as FileAssets }

    single { MusicnerdsRouter() }

    single(KoinNames.MUSICNERDS_BASE_URL) { "https://mncoreservice20190513103648.azurewebsites.net/" }

    single(KoinNames.MUSICNERDS_IS_DEBUG) { BuildConfig.DEBUG }

    viewModel { FeedViewModel(interactor = get(), application = get(), schedulersProvider = get(), router = get()) }
}