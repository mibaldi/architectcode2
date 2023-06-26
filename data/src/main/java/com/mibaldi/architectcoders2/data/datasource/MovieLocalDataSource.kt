package com.mibaldi.architectcoders2.data.datasource

import com.mibaldi.architectcoders2.domain.Error
import com.mibaldi.architectcoders2.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    val movies: Flow<List<Movie>>
    suspend fun mustUpdate():Boolean
    suspend fun isEmpty(): Boolean
    fun findById(id: Int): Flow<Movie>
    suspend fun save(movies: List<Movie>): Error?
}