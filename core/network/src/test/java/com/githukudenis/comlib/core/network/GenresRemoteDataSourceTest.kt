package com.githukudenis.comlib.core.network

import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class GenresRemoteDataSourceTest {

    @get:Rule
    val mainCoroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var fakeGenresRemoteDataSource: FakeGenresRemoteDataSource

    @Before
    fun setup() {
        fakeGenresRemoteDataSource = FakeGenresRemoteDataSource()
    }

    @Test
    fun getGenres() = runTest {
        val result = fakeGenresRemoteDataSource.getGenres()
        assertEquals(result.size, 2)
    }

    @Test
    fun getGenreById() = runTest {
        val result = fakeGenresRemoteDataSource.getGenreById("2")
        assertEquals(result?.name, "Non-fiction")

    }
}