package com.alexiacdura.musicnerds

import android.app.Application
import com.alexiacdura.musicnerds.resources.KoinModules


/**
 * Created 15/05/2019
 * @author alexiacdura
 * @version 1.0
 *
 * Application class of Musicnerds that setups Koin in the project
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupKoin(this)
    }

    private fun setupKoin(app: App) {
        KoinModules.setup(app)
    }
}
