package com.demo.canyouhearme.results.io.remote

import com.demo.canyouhearme.results.data.ResultUpload
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ResultApi {
    @POST
    fun postResults(@Body results: ResultUpload): Response<ResponseBody>
}