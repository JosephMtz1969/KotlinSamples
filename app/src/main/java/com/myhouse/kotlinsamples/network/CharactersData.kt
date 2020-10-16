package com.myhouse.kotlinsamples.network

data class Characters(
    val info: Info,
    val results: List<CharacterResult>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)

data class CharacterResult(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val image: String,
    val url: String,
    val created: String
)