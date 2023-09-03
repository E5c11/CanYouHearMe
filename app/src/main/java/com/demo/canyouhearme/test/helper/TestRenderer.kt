package com.demo.canyouhearme.test.helper

import com.demo.canyouhearme.results.data.Result
import com.demo.canyouhearme.results.data.Round
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Locale
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
        _roundTriplets[_round - 1].matches(one, two, three).calculateScore(one, two, three)
    }

    private fun Boolean.calculateScore(one: Int, two: Int, three: Int) {
        insertRound(one, two, three)
        _round++
        if (this) {
            _score += _level
            if (_level != 10) _level++
            else {
                _result.score = _score
                _result.date = getDate()
            }
        }
    }

    private fun insertRound(one: Int, two: Int, three: Int) {
        val round = Round(
            difficulty = _level,
            tripletPlayed = _roundTriplets[_round - 1].joinToString(""),
            tripletAnswered = "$one$two$three"
        )
        _result.rounds.add(round)
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

    private fun List<Int>.matches(one: Int, two: Int, three: Int): Boolean =
        this[0] == one && this[1] == two && this[2] == three

    private fun getDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}