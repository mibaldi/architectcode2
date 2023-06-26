package com.mibaldi.architectcoders2.domain

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val favorite: Boolean,
    val createdDate: Long,
    val lastUpDatedDate: Long
)
