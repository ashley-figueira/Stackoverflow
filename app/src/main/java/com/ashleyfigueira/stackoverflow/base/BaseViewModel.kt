package com.ashleyfigueira.stackoverflow.base

import androidx.lifecycle.*
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<V: ScreenState<*>> : ViewModel(), DefaultLifecycleObserver {

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val loadingMap: MutableMap<Any, Boolean> = mutableMapOf()

    protected val _screenState = MutableLiveData<V>()
    val screenState: LiveData<V> get() = _screenState

    fun setLifeCycleOwner(owner : LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun Disposable.addToComposite(): Disposable {
        disposable.add(this)
        return this
    }

    protected fun <T> Flowable<T>.addToLoadingState(): Flowable<T> {
        return this
            .doOnSubscribe {
                loadingMap[this] = true
                notifyLoadingState(loadingMap.any { it.value })
            }
            .doOnNext {
                loadingMap[this] = false
                notifyLoadingState(loadingMap.any { it.value })
            }
    }

    private fun notifyLoadingState(isLoading: Boolean) {
        _screenState.value = if (isLoading) ScreenState.loading<Any>(true) as V else ScreenState.loading<Any>(false) as V
    }
}