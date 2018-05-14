package com.angelsware.libspline

/**
 * Created by klarre
 * On 2018-05-14.
 */
open class Cardinal(controlPoints: Array<ControlPoint>, tangentStrength: Double, resolution: Double): Hermite(controlPoints) {
    init {
        calculateTangents(tangentStrength)
        calculateLength(resolution)
    }

    private fun calculateTangents(strength: Double) {
        val tangent = Point()
        tangent.x = mSegments[0].end.position.x - mSegments[0].begin.position.x
        tangent.y = mSegments[0].end.position.y - mSegments[0].begin.position.y
        tangent.z = mSegments[0].end.position.z - mSegments[0].begin.position.z
        mSegments[0].setBeginTangent(tangent)
        val amount = getNumControlPoints()

        for (i in (0..(amount - 3))) {
            tangent.x = strength * (mSegments[i+1].end.position.x - mSegments[i].begin.position.x)
            tangent.y = strength * (mSegments[i+1].end.position.y - mSegments[i].begin.position.y)
            tangent.z = strength * (mSegments[i+1].end.position.z - mSegments[i].begin.position.z)
            mSegments[i].setEndTangent(tangent)
        }

        tangent.x = mSegments[amount - 2].end.position.x - mSegments[amount - 2].begin.position.x
        tangent.y = mSegments[amount - 2].end.position.y - mSegments[amount - 2].begin.position.y
        tangent.z = mSegments[amount - 2].end.position.z - mSegments[amount - 2].begin.position.z
        mSegments[amount - 2].setEndTangent(tangent)
    }
}
