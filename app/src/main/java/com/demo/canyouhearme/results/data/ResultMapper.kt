package com.demo.canyouhearme.results.data

fun Result.toUpload() = ResultUpload(
    score = this.score,
    rounds = this.rounds
)

fun Result.toEntity() = ResultEntity(
    score = this.score,
    rounds = this.rounds,
    date = this.date
)

fun ResultEntity.toResult() = Result(
    score = this.score,
    rounds = ArrayList(this.rounds),
    date = this.date
)

fun List<ResultEntity>.toResultList() = this.mapNotNull { it.toResult() }