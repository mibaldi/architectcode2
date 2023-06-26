package com.mibaldi.architectcoders2.data

import com.mibaldi.architectcoders2.data.datasource.MovieLocalDataSource
import com.mibaldi.architectcoders2.data.datasource.MovieRemoteDataSource
import com.mibaldi.architectcoders2.domain.Error
import javax.inject.Inject

class MoviesRepository  @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource){
    suspend fun requestPopularMovies(): Error? {
        if (localDataSource.isEmpty() || localDataSource.mustUpdate()) {
            val movies = remoteDataSource.findPopularMovies("US")
            movies.fold(ifLeft = { return it }) {
                localDataSource.save(it)
            }
        }
        return null
    }

    val popularMovies get() = localDataSource.movies

}