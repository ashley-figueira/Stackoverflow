package com.ashleyfigueira.data.common

import androidx.room.EmptyResultSetException
import com.ashleyfigueira.domain.common.Mapper
import com.ashleyfigueira.domain.common.StackError
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ErrorMapper @Inject constructor() : Mapper<Throwable, StackError>() {

    override fun mapFrom(from: Throwable): StackError {
        return when (from) {
            is UnknownHostException -> StackError.Offline(from)
            is SocketTimeoutException -> StackError.Timeout(from)
            is EmptyResultSetException -> StackError.NothingInDatabase(from)
            else -> StackError.Unknown(from)
        }
    }
}