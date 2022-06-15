package com.example.core

sealed class FilterOrder{
    object Ascending :FilterOrder()
    object Descending :FilterOrder()
}
