package com.ashleyfigueira.domain.common

sealed class StackError(val throwable: Throwable) {

    class Offline(throwable: Throwable) : StackError(throwable)

    class Timeout(throwable: Throwable) : StackError(throwable)

    class Unknown(throwable: Throwable) : StackError(throwable)

    class NothingInDatabase(throwable: Throwable) : StackError(throwable)

}