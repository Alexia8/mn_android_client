package com.alexiacdura.mn_ui.Utils.koin

import android.arch.lifecycle.LifecycleOwner
import org.koin.android.scope.bindScope
import org.koin.android.viewmodel.ext.android.getKoin
import org.koin.core.qualifier.Qualifier

fun LifecycleOwner.bindNamedScope(qualifier: Qualifier) {
    val scope = getKoin().getOrCreateScope(getScopeId(), qualifier)
    bindScope(scope)
}

private fun LifecycleOwner.getScopeId() = toString()