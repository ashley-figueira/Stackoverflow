package com.ashleyfigueira.domain.entities

sealed class OrderEntity {
    object Ascending : OrderEntity()
    object Descending : OrderEntity()
}