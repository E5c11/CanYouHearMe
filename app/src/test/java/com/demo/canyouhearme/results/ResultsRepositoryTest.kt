package com.demo.canyouhearme.results

import app.cash.turbine.test
import com.demo.canyouhearme.common.helper.Resource
import com.demo.canyouhearme.common.helper.ResourcesReader
import com.demo.canyouhearme.results.data.Result
import com.demo.canyouhearme.results.data.exception.InsertResultException
import com.demo.canyouhearme.results.io.ResultDataSource
import com.google.gson.Gson
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

private const val RESULT_JSON = "result.json"

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ResultsRepositoryTest {

    private val local: ResultDataSource = mockk()
    private val remote: ResultDataSource = mockk()

    private val repo = ResultsRepository(local, remote)

    private val resultString = ResourcesReader.readJsonFile(RESULT_JSON)
    private val mockResult = Gson().fromJson(resultString, Result::class.java)

    @Test
    fun `A Successful insertAndObserve UPLOADS to remote THEN INSERTS to local then OBSERVES local db`() = runTest {
        coEvery { remote.insert(any()) } just Runs
        coEvery { local.insert(any()) } just Runs
        val list = listOf(mockResult)
        val resource = Resource.success(list)
        every { local.fetchAll() } returns flowOf(resource)

        repo.uploadAndObserve(mockResult).test {
            val loadingEmission = awaitItem()
            loadingEmission.status.shouldBeEqualTo(Resource.Status.LOADING)

            val listEmission = awaitItem()
            listEmission.status.shouldBeEqualTo(Resource.Status.SUCCESS)
            listEmission.data!!.shouldBeEqualTo(list)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify {
            remote.insert(any())
            local.insert(any())
            local.fetchAll()
        }
    }

    @Test
    fun `A Failed insertAndObserve FAILS to upload to remote THEN THROWS InsertResultException`() = runTest {
        coEvery { remote.insert(any()) } throws InsertResultException()

        repo.uploadAndObserve(mockResult).test {
            val loadingEmission = awaitItem()
            loadingEmission.status.shouldBeEqualTo(Resource.Status.LOADING)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify {
            remote.insert(any())
        }
    }

    @Test
    fun `A Failed insertAndObserve UPLOADS to remote THEN FAILS to insert to local THROWS InsertResultException`() = runTest {
        coEvery { remote.insert(any()) } just Runs
        coEvery { local.insert(any()) } throws InsertResultException()

        repo.uploadAndObserve(mockResult).test {
            val loadingEmission = awaitItem()
            loadingEmission.status.shouldBeEqualTo(Resource.Status.LOADING)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify {
            remote.insert(any())
            local.insert(any())
        }
    }

    @Test
    fun `A Failed insertAndObserve UPLOADS to remote THEN THEN INSERTS to local THEN FAILS to observe db THROWS InsertResultException`() = runTest {
        coEvery { remote.insert(any()) } just Runs
        coEvery { local.insert(any()) } just Runs
        every { local.fetchAll() } throws InsertResultException()

        repo.uploadAndObserve(mockResult).test {
            val loadingEmission = awaitItem()
            loadingEmission.status.shouldBeEqualTo(Resource.Status.LOADING)
            cancelAndIgnoreRemainingEvents()
        }

        coVerify {
            remote.insert(any())
            local.insert(any())
            local.fetchAll()
        }
    }

    @Test
    fun `A Successful observe runs once`() = runTest {
        every { local.fetchAll() } returns flowOf()

        repo.observe()

        verifySequence {
            local.fetchAll()
        }
    }
}