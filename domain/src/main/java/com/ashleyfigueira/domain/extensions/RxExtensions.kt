package com.ashleyfigueira.domain.extensions

import com.ashleyfigueira.domain.common.SchedulerProvider
import com.ashleyfigueira.domain.common.StackError
import com.ashleyfigueira.domain.common.StackResult
import io.reactivex.*
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subscribers.ResourceSubscriber

fun <T> Observable<T>.compose(schedulerProvider: SchedulerProvider): Observable<T> {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun <T> Flowable<T>.compose(schedulerProvider: SchedulerProvider): Flowable<T> {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun <T> Single<T>.compose(schedulerProvider: SchedulerProvider): Single<T> {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun Completable.compose(schedulerProvider: SchedulerProvider): Completable {
    return this.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui())
}

fun <T> Maybe<T>.compose(schedulerProvider: SchedulerProvider): Maybe<T> {
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

fun <T> Observable<StackResult<T>>.subscribeStackResult(onSuccess: ((T) -> Unit), onError: ((StackError) -> Unit)) =
    subscribeWith(object : DisposableObserver<StackResult<T>>() {
        override fun onComplete() {
            //do nothing
        }

        override fun onNext(result: StackResult<T>) {
            when (result) {
                is StackResult.Success -> onSuccess.invoke(result.data)
                is StackResult.Failure -> onError.invoke(result.error)
            }
        }

        override fun onError(e: Throwable) {
            onError.invoke(StackError.Unknown(e))
        }
    })

fun <T> Flowable<StackResult<T>>.subscribeStackResult(onSuccess: ((T) -> Unit), onError: ((StackError) -> Unit)) =
    subscribeWith(object : ResourceSubscriber<StackResult<T>>() {
        override fun onComplete() {
            // Do nothing
        }

        override fun onNext(result: StackResult<T>) {
            when (result) {
                is StackResult.Success -> onSuccess.invoke(result.data)
                is StackResult.Failure -> onError.invoke(result.error)
            }
        }

        override fun onError(error: Throwable) {
            onError.invoke(StackError.Unknown(error))
        }

    })