package com.angelsware.libspline

/**
 * Created by klarre
 * On 2018-05-14.
 */
interface Curve {
    fun getWorldPosition(distance: Double): Point
    fun getPositionOnSegment(distance: Double, segment: Int): Point
    fun getTangent(distance: Double): Point
    fun getTangentOnSegment(distance: Double, segment: Int): Point
    fun calculateLength(resolution: Double)
    fun getLength(): Double
    fun getNumControlPoints(): Int
    fun getSegment(segment: Int): Segment
}
