package com.ashleyfigueira.data.common

import com.ashleyfigueira.domain.common.Mapper
import com.ashleyfigueira.domain.entities.OrderEntity
import javax.inject.Inject

class OrderEntityMapper @Inject constructor() : Mapper<OrderEntity, String>() {
    override fun mapFrom(from: OrderEntity): String {
        return when (from) {
            OrderEntity.Ascending -> "asc"
            OrderEntity.Descending -> "desc"
        }
    }
}