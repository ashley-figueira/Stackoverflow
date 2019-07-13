package com.ashleyfigueira.domain.common

sealed class StackResult<T> {

    class Success<T>(val data: T) : StackResult<T>() {
        override fun equals(other: Any?): Boolean {
            return other is Success<*> && other.hashCode() == hashCode()
        }

        override fun hashCode(): Int {
            return data?.hashCode() ?: 0
        }
    }

    class Failure<T>(val error: StackError) : StackResult<T>()
}