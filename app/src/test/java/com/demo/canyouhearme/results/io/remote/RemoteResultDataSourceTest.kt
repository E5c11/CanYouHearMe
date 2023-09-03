package com.demo.canyouhearme.results.io.remote

import com.demo.canyouhearme.common.helper.ResourcesReader
import com.demo.canyouhearme.results.data.Result
import com.demo.canyouhearme.results.data.exception.CannotDeleteRemoteException
import com.demo.canyouhearme.results.data.exception.CannotFetchRemoteException
import com.demo.canyouhearme.results.data.exception.ResultUploadException
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.internal.assertFailsWith
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

private const val RESULT_JSON = "result.json"

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RemoteResultDataSourceTest {

    private val api: ResultApi = mockk()

    private val dataSource = RemoteResultDataSource(api)

    private val resultString = ResourcesReader.readJsonFile(RESULT_JSON)
    private val mockResult = Gson().fromJson(resultString, Result::class.java)

    @Test
    fun `A Successful insert UPLOADS the result WITHOUT THROWING errors`() = runTest {
        val response = Response.success("".toResponseBody(null))
        coEvery { api.postResults(any()) } returns response

        dataSource.insert(mockResult)

        coVerifySequence {
            api.postResults(any())
        }
    }

    @Test
    fun `A Failed insert UPLOADS the result THEN returns 400 THEN throws ResultUploadException`() = runTest {
        val response = Response.error<ResponseBody>(401, "".toResponseBody(null))
        coEvery { api.postResults(any()) } returns response

        assertFailsWith<ResultUploadException> { dataSource.insert(mockResult) }

        coVerifySequence {
            api.postResults(any())
        }
    }

    @Test
    fun `A Failed insert fails to upload THEN throws ResultUploadException`() = runTest {
        coEvery { api.postResults(any()) } throws ResultUploadException()

        assertFailsWith<ResultUploadException> { dataSource.insert(mockResult) }

        coVerifySequence {
            api.postResults(any())
        }
    }

    @Test
    fun `Delete should always FAIL with CannotDeleteRemoteException`() = runTest {
        assertFailsWith<CannotDeleteRemoteException> { dataSource.delete(mockResult) }
    }

    @Test
    fun `FetchAll should always FAIL with CannotFetchRemoteException`() = runTest {
        assertFailsWith<CannotFetchRemoteException> { dataSource.fetchAll() }
    }
}