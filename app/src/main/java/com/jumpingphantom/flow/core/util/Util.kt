package com.jumpingphantom.flow.core.util

import java.util.Locale
import kotlin.math.floor

fun formatCurrency(input: Double): String {
    val rounded = floor(input)

    if (input == 0.0) return "0"

    if (input / floor(input) == 1.0) {
        return String.format(Locale.US, "%,d", rounded.toInt())
    }

    return String.format(Locale.US, "%,.02f", input)
}
