package com.brainsoft.utils.extensions

import kotlin.math.pow
import kotlin.math.roundToInt

fun Int.divideToPercent(divideTo: Int): Int {
    return if (divideTo == 0) 0
    else ((this / divideTo.toFloat()) * 100).toInt()
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}
