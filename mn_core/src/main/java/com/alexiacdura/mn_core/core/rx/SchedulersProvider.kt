package com.alexiacdura.mn_core.core.rx

import io.reactivex.Scheduler

interface SchedulersProvider {
    fun main(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
    fun newThread(): Scheduler
    fun trampoline(): Scheduler
}