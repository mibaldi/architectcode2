package com.mibaldi.architectcoders2.data.database


import com.mibaldi.architectcoders2.data.datasource.MovieLocalDataSource
import com.mibaldi.architectcoders2.data.tryCall
import com.mibaldi.architectcoders2.domain.Error
import com.mibaldi.architectcoders2.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject
import com.mibaldi.architectcoders2.data.database.Movie as DbMovie

class MovieRoomDataSource @Inject constructor(private val movieDao: MovieDao) :
    MovieLocalDataSource {

    override val movies: Flow<List<Movie>> = movieDao.getAll().map { it.toDomainModel() }
    override suspend fun mustUpdate(): Boolean {
        val all = movieDao.getAll().map { it.toDomainModel() }
        val createdDate = (all.first() as Movie).createdDate
        return createdDate < Date().time
    }

    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    override fun findById(id: Int): Flow<Movie> = movieDao.findById(id).map { it.toDomainModel() }

    override suspend fun save(movies: List<Movie>): Error? = tryCall {
        val created = Date().time
        movieDao.insertMovies(movies.fromDomainModel(created))
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}

private fun List<DbMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun DbMovie.toDomainModel(): Movie =
    Movie(
        id,
        title,
        posterPath,
        favorite,
        createdDate,
        lastUpDatedDate
    )

private fun List<Movie>.fromDomainModel(created: Long): List<DbMovie> = map { it.fromDomainModel(created) }

private fun Movie.fromDomainModel(created: Long): DbMovie = DbMovie(
    id,
    title,
    "overview",
    "releaseDate",
    posterPath,
    "backdropPath",
    "originalLanguage",
    "originalTitle",
    0.0,
    0.0,
    favorite,
    created,
    created
)