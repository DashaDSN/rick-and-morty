package com.andersen.data.model

data class ServerResponse<T> (
    val info: Info,
    val results: List<T>
) {
    data class Info (
        val count: Int,
        val pages: Int,
        val next: String,
        val prev: String
    )
}
