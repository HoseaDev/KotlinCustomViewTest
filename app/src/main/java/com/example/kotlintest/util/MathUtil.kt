package com.example.kotlintest.util

class MathUtil {

    companion object {
        @JvmStatic
        fun checkInRound(
            sx: Float,
            sy: Float,
            r: Float,
            x: Float,
            y: Float
        ): Boolean {
            return Math.sqrt(((sx - x) * (sx - x) + (sy - y) * (sy - y)).toDouble()) < r
        }

        fun getDistance(sx: Double, sy: Double, ex: Double, ey: Double):Double {
          return  Math.sqrt(Math.pow(sy - ey, 2.toDouble()) + Math.pow(sx - ex, 2.toDouble()))
        }
    }
}