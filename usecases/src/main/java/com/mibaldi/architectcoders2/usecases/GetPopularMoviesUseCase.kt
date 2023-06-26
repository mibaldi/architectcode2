package com.mibaldi.architectcoders2.usecases

import com.mibaldi.architectcoders2.data.MoviesRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val repository: MoviesRepository){

    operator fun invoke() = repository.popularMovies
}