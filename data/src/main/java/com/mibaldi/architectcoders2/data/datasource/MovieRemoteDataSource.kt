package com.mibaldi.architectcoders2.data.datasource

import arrow.core.Either
import com.mibaldi.architectcoders2.domain.Error
import com.mibaldi.architectcoders2.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): Either<Error, List<Movie>>
}