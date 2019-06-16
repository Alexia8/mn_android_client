package com.alexiacdura.mn_ui.core.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class RxAndroidViewModel(application: Application) : AndroidViewModel(application) {

    private val disposables = CompositeDisposable()

    fun registerDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    public override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}