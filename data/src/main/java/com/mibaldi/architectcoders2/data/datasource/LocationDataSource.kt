package com.mibaldi.architectcoders2.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}