package com.demo.canyouhearme.results.io.local

import app.cash.turbine.test
import com.demo.canyouhearme.common.helper.DispatcherProvider
import com.demo.canyouhearme.common.helper.Resource
import com.demo.canyouhearme.common.helper.ResourcesReader
import com.demo.canyouhearme.results.data.Result
import com.demo.canyouhearme.results.data.exception.DeleteResultException
import com.demo.canyouhearme.results.data.exception.FetchResultException
import com.demo.canyouhearme.results.data.exception.InsertResultException
import com.demo.canyouhearme.results.data.toEntity
import com.demo.canyouhearme.results.data.toResultList
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.internal.assertFailsWith
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

private const val RESULT_JSON = "result.json"

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class LocalResultDataSourceTest {

    private val dao: ResultDao = mockk()
    private val dispatcher: DispatcherProvider = mockk()

    private val dataSource = LocalResultDataSource(dao, dispatcher)

    private val resultString = ResourcesReader.readJsonFile(RESULT_JSON)
    private val mockResult = Gson().fromJson(resultString, Result::class.java)

    @Test
    fun `A Successful insert INSERTS to the db WITHOUT THROWING an exception`() = runTest {
        coEvery { dao.insert(any()) } returns 1

        dataSource.insert(mockResult)

        coVerifySequence {
            dao.insert(any())
        }
    }

    @Test
    fun `A Failed insert INSERTS but returns 0 id throws InsertResultException`() = runTest {
        coEvery { dao.insert(any()) } returns 0

        assertFailsWith<InsertResultException> { dataSource.insert(mockResult) }

        coVerifySequence {
            dao.insert(any())
        }
    }

    @Test
    fun `A Failed insert FAILS to INSERT throws InsertResultException`() = runTest {
        coEvery { dao.insert(any()) } throws IOException()

        assertFailsWith<InsertResultException> { dataSource.insert(mockResult) }

        coVerifySequence {
            dao.insert(any())
        }
    }

    @Test
    fun `A Successful delete DELETES from the db WITHOUT THROWING an exception`() = runTest {
        coEvery { dao.delete(any()) } returns 1

        dataSource.delete(mockResult)

        coVerifySequence {
            dao.delete(any())
        }
    }

    @Test
    fun `A Failed delete DELETE but returns 0 id throws DeleteResultException`() = runTest {
        coEvery { dao.delete(any()) } returns 0

        assertFailsWith<DeleteResultException> { dataSource.delete(mockResult) }

        coVerifySequence {
            dao.delete(any())
        }
    }

    @Test
    fun `A Failed delete FAILS to DELETE throws DeleteResultException`() = runTest {
        coEvery { dao.delete(any()) } throws IOException()

        assertFailsWith<DeleteResultException> { dataSource.delete(mockResult) }

        coVerifySequence {
            dao.delete(any())
        }
    }

    @Test
    fun `A Successful fetchAll fetches from db WITHOUT THROWING an exception`() = runTest {
        val list = listOf(mockResult.toEntity())
        every { dao.fetchAll() } returns flowOf(list)
        every { dispatcher.io } returns Dispatchers.IO

        dataSource.fetchAll().test {
            val listEmission = awaitItem()
            listEmission.status.shouldBeEqualTo(Resource.Status.SUCCESS)
            listEmission.data!!.shouldBeEqualTo(list.toResultList())
            cancelAndIgnoreRemainingEvents()
        }

        verifySequence {
            dao.fetchAll()
            dispatcher.io
        }
    }

    @Test
    fun `A Failed fetchAll FAILS to fetch and RETURNS FetchResultException`() = runTest {
        every { dao.fetchAll() } throws IOException()
        every { dispatcher.io } returns Dispatchers.IO

        dataSource.fetchAll().test {
            val listEmission = awaitItem()
            listEmission.status.shouldBeEqualTo(Resource.Status.ERROR)
            listEmission.error!!.shouldBeInstanceOf<FetchResultException>()
            cancelAndIgnoreRemainingEvents()
        }

        verifySequence {
            dao.fetchAll()
            dispatcher.io
        }
    }
}