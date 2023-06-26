package com.mibaldi.architectcoders2.data.server

import arrow.core.Either
import com.mibaldi.architectcoders2.data.datasource.MovieRemoteDataSource
import com.mibaldi.architectcoders2.data.tryCall
import com.mibaldi.architectcoders2.di.ApiKey
import com.mibaldi.architectcoders2.domain.Error
import com.mibaldi.architectcoders2.domain.Movie
import java.util.Date
import javax.inject.Inject

class MovieServerDataSource @Inject constructor(
    @ApiKey private val apiKey: String,
    private val remoteService: RemoteService
): MovieRemoteDataSource {
    override suspend fun findPopularMovies(region: String): Either<Error, List<Movie>> = tryCall {
        remoteService
            .listPopularMovies(apiKey,region)
            .results
            .toDomainModel()
    }
    private fun List<RemoteMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }
    private fun RemoteMovie.toDomainModel(): Movie {
        val createdDate = Date().time
        return Movie(
            id,
            title,
            "https://image.tmdb.org/t/p/w185$posterPath",
            false,
            createdDate,
            createdDate
            )
    }

}