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
        val beginTangent = Point()
        beginTangent.x = mSegments[0].end.position.x - mSegments[0].begin.position.x
        beginTangent.y = mSegments[0].end.position.y - mSegments[0].begin.position.y
        beginTangent.z = mSegments[0].end.position.z - mSegments[0].begin.position.z
        beginTangent.normalize();
        mSegments[0].setBeginTangent(beginTangent)
        val amount = getNumControlPoints()

        for (i in (0..(amount - 3))) {
            val t = Point()
            t.x = strength * (mSegments[i+1].end.position.x - mSegments[i].begin.position.x)
            t.y = strength * (mSegments[i+1].end.position.y - mSegments[i].begin.position.y)
            t.z = strength * (mSegments[i+1].end.position.z - mSegments[i].begin.position.z)
            t.normalize();
            mSegments[i].setEndTangent(t)
        }

        val endTangent = Point()
        endTangent.x = mSegments[amount - 2].end.position.x - mSegments[amount - 2].begin.position.x
        endTangent.y = mSegments[amount - 2].end.position.y - mSegments[amount - 2].begin.position.y
        endTangent.z = mSegments[amount - 2].end.position.z - mSegments[amount - 2].begin.position.z
        endTangent.normalize();
        mSegments[amount - 2].setEndTangent(endTangent)
    }
}
