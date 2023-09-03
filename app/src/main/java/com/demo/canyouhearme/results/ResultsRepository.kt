package com.demo.canyouhearme.results

import com.demo.canyouhearme.results.io.ResultDataSource

class ResultsRepository(
    private val local: ResultDataSource,
    private val remote: ResultDataSource
) {
}