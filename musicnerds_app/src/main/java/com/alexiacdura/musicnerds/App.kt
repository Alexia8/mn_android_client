package com.alexiacdura.musicnerds

import android.app.Application
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


/**
 * Created 18/08/2018
 * @author alexiacdura
 * @version 1.0
 *
 * Application class subscribes scheduler and where we we should check network connectivity, add other services and initialize repositories (DAO) for data offline
 * Is initiated in MainActivity
 */

class App : Application() {

    private var scheduler: Scheduler? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    /**
     * Return method that returns Application scheduler
     *
     * @return Scheduler
     */
    fun subscribeScheduler(): Scheduler? {
        if (scheduler == null) {
            scheduler = Schedulers.io()
        }
        return scheduler
    }

    companion object {

        lateinit var instance: App
            private set

    }
}
