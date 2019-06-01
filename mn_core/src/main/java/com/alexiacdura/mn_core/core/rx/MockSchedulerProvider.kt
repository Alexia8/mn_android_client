package com.alexiacdura.mn_core.core.rx

import com.alexiacdura.mn_core.core.rx.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class MockSchedulerProvider : SchedulersProvider {
    override fun main(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun trampoline(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun newThread(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }
}