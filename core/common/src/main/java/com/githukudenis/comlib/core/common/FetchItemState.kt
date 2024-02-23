package com.githukudenis.comlib.core.common

/**
 * project : ComLib
 * date    : Friday 23/02/2024
 * time    : 12:15 pm
 * user    : mambo
 * email   : mambobryan@gmail.com
 */

sealed interface FetchItemState<out T> {

    data object Loading : FetchItemState<Nothing>

    data class Error(val message: String) : FetchItemState<Nothing>

    data class Success<T>(val data: T) : FetchItemState<T>

}