package com.jumpingphantom.flow.util

import java.util.Locale
import kotlin.math.floor

object Util {
    fun formatCurrency(input: Float): String {
        val rounded = floor(input)

        if (input == 0f) return "0"

        if (input / floor(input) == 1.0f) {
            return String.format(Locale.US, "%,d", rounded.toInt())
        }

        return String.format(Locale.US, "%,.02f", input)
    }
}
