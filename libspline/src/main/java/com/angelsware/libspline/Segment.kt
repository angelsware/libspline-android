package com.angelsware.libspline

/**
 * Created by klarre
 * On 2018-05-14.
 */
class Segment {
    internal var begin = ControlPoint()
    internal var end = ControlPoint()
    internal var length = 0.0
    internal var lengthFromBeginning = 0.0

    internal fun setBeginTangent(tangent: Point) {
        begin.tangent = tangent
    }

    internal fun setEndTangent(tangent: Point) {
        end.tangent = tangent
    }
}
