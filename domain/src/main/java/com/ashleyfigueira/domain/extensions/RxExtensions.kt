package com.ashleyfigueira.domain.extensions

import com.ashleyfigueira.domain.common.SchedulerProvider
import com.ashleyfigueira.domain.common.StackError
import com.ashleyfigueira.domain.common.StackResult
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver

fun <T> Single<T>.compose(schedulerProvider: SchedulerProvider): Single<T> {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun <T> Single<StackResult<T>>.doOnStackSuccess(onSuccess: ((T) -> Unit)) =
    doOnSuccess { if (it is StackResult.Success) onSuccess.invoke(it.data) }

fun <T> Single<StackResult<T>>.subscribeStackResult(onSuccess: ((T) -> Unit), onError: ((StackError) -> Unit)) =
    subscribeWith(object : DisposableSingleObserver<StackResult<T>>() {
        override fun onSuccess(result: StackResult<T>) {
            when (result) {
                is StackResult.Success -> onSuccess.invoke(result.data)
                is StackResult.Failure -> onError.invoke(result.error)
            }
        }

        override fun onError(e: Throwable) {
            onError.invoke(StackError.Unknown(e))
        }
    })