package com.alexiacdura.musicnerds.resources

import com.alexiacdura.mn_core.core.rx.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AndroidSchedulersProvider : SchedulersProvider {
    override fun main(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun newThread(): Scheduler {
        return Schedulers.newThread()
    }

    override fun trampoline(): Scheduler {
        return Schedulers.trampoline()
    }
}