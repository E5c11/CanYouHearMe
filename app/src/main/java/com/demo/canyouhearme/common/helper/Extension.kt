package com.demo.canyouhearme.common.helper

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.demo.canyouhearme.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import kotlin.math.ceil
import kotlin.random.Random

fun Long.toSeconds() = ceil(this.toDouble() / 1000.0).toInt()

fun <T> Flow<T>.collectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend (value: T) -> Unit
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(minActiveState) {
        collect { action(it) }
    }
}

fun Int.random() = Random.nextInt(this)

fun Int.word(ctx: Context) = when (this) {
    1 -> ctx.string(R.string.one)
    2 -> ctx.string(R.string.two)
    3 -> ctx.string(R.string.three)
    4 -> ctx.string(R.string.four)
    5 -> ctx.string(R.string.five)
    6 -> ctx.string(R.string.six)
    7 -> ctx.string(R.string.seven)
    8 -> ctx.string(R.string.eight)
    9 -> ctx.string(R.string.nine)
    else -> ctx.string(R.string.ten)
}

fun Context.string(id: Int) = this.resources.getString(id)

fun Context.readJsonFromAssets(fileName: String): String? {
    return try {
        val inputStream: InputStream = this.assets.open(fileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charsets.UTF_8)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}