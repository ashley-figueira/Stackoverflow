package com.ashleyfigueira.domain.extensions

import com.ashleyfigueira.domain.common.StackError
import com.ashleyfigueira.domain.common.StackResult
import org.joda.time.DateTime

fun <T> T.toResult(): StackResult<T> = StackResult.Success(this)

fun <T> StackError.toResult(): StackResult<T> = StackResult.Failure(this)

fun DateTime.isOutOfDate(): Boolean = this.isBefore(DateTime.now().minusDays(1))