package com.angelsware.libspline

/**
 * Created by klarre
 * On 2018-05-14.
 */
class Point(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {
    fun getLength(): Double {
        return Math.sqrt(x * x + y * y + z * z)
    }

    fun normalize() {
        val length = getLength()
        x /= length
        y /= length
        z /= length
    }
}

class ControlPoint(var position: Point = Point(), var tangent: Point = Point())
