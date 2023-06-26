package com.mibaldi.architectcoders2.usecases

import com.mibaldi.architectcoders2.data.MoviesRepository
import com.mibaldi.architectcoders2.domain.Error
import javax.inject.Inject

class RequestPopularMoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository ) {
    suspend operator fun invoke() : Error? {
        return moviesRepository.requestPopularMovies()
    }
}