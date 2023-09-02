package com.demo.canyouhearme.test.helper

import com.demo.canyouhearme.results.data.Result
import java.util.Arrays
import java.util.Random
import java.util.stream.Collectors
import java.util.stream.IntStream

const val UPPER_BOUND = 11
const val FIRST_DIGIT = 1
const val LAST_DIGIT = 9

class TestRenderer(
    private val rand: Random
) {

    private var _score = 0
    private var _round = 1
    private var _level = 5
    fun getLevel() = _level

    private var _roundTriplets = ArrayList<ArrayList<Int>>()
    fun getTriplet() = _roundTriplets[_round - 1]

    private var _result = Result()
    fun getResult() = _result

    init {
        generateTriplets()
    }

    fun checkRound(one: Int, two: Int, three: Int) {
        _roundTriplets[_round - 1].matches(one, two, three).calculateScore()
    }

    private fun Boolean.calculateScore() {
        when (_round) {
            1 -> _result.one = this.score()
            2 -> _result.two = this.score()
            3 -> _result.three = this.score()
            4 -> _result.four = this.score()
            5 -> _result.five = this.score()
            6 -> _result.six = this.score()
            7 -> _result.seven = this.score()
            8 -> _result.eight = this.score()
            9 -> _result.nine = this.score()
            else -> _result.ten = this.score()
        }
        _round++
        if (this) {
            _score += _level
            if (_level <= 9) _level++
        }
    }

    private fun generateTriplets() {
        for (i in 0..9) {
            val list = ArrayList<Int>()
            val one =
                if (i == 0) rand.nextInt(UPPER_BOUND - 1) + 1
                else listOf(_roundTriplets[i-1][0]).getRandomNumber()
            list.add(one)
            val two =
                if (i == 0) listOf(one).getRandomNumber()
                else listOf(one, _roundTriplets[i-1][1]).getRandomNumber()
            list.add(two)
            val three =
                if (i == 0) listOf(one, two).getRandomNumber()
                else listOf(one, two, _roundTriplets[i-1][2]).getRandomNumber()
            list.add(three)
            _roundTriplets.add(list)
        }
    }

    private fun List<Int>.getRandomNumber(): Int {
        val range = IntStream.rangeClosed(FIRST_DIGIT, LAST_DIGIT).toArray()
        val rangeExcluding = Arrays.stream(range).boxed().collect(Collectors.toList())

        rangeExcluding.removeAll(this)
        val newRandomInt = rand.nextInt(rangeExcluding.size)

        return rangeExcluding[newRandomInt]
    }

    private fun Boolean.score(): Int {
        val roundScore = if (this) 0 else _level
        _result.total += roundScore
        return roundScore
    }

    private fun List<Int>.matches(one: Int, two: Int, three: Int): Boolean =
        this[0] == one && this[1] == two && this[2] == three
}