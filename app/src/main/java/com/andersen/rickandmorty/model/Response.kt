package com.andersen.rickandmorty.model

data class ServerResponse<T> (
    val info: Info,
    val results: T
)

data class Info (
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)