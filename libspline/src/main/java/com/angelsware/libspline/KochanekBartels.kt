package com.angelsware.libspline

/**
 * Created by klarre
 * On 2018-05-14.
 */
class KochanekBartels(controlPoints: Array<ControlPoint>, tension: Double, continuity: Double, bias: Double, resolution: Double): Hermite(controlPoints) {
    init {
        calculateTangents(tension, continuity, bias)
        calculateLength(resolution)
    }

    private fun calculateTangents(tension: Double, continuity: Double, bias: Double) {
        val positionA = Point()
        val positionB = Point()

        positionA.x = mSegments[0].end.position.x - mSegments[0].begin.position.x
        positionA.y = mSegments[0].end.position.y - mSegments[0].begin.position.y
        positionA.z = mSegments[0].end.position.z - mSegments[0].begin.position.z
        positionA.normalize();
        mSegments[0].setBeginTangent(positionA)

        val amount = getNumControlPoints()

        for (i in (0..(amount - 3))) {
            val pA = Point()
            val pB = Point()
            pA.x = mSegments[i].end.position.x - mSegments[i].begin.position.x
            pA.y = mSegments[i].end.position.y - mSegments[i].begin.position.y
            pA.z = mSegments[i].end.position.z - mSegments[i].begin.position.z

            pB.x = mSegments[i + 1].end.position.x - mSegments[i + 1].begin.position.x
            pB.y = mSegments[i + 1].end.position.y - mSegments[i + 1].begin.position.y
            pB.z = mSegments[i + 1].end.position.z - mSegments[i + 1].begin.position.z

            val a = 0.5 * (1.0 - tension) * (1.0 + continuity) * (1.0 + bias)
            val b = 0.5 * (1.0 - tension) * (1.0 - continuity) * (1.0 - bias)

            pA.x *= a
            pA.y *= a
            pA.z *= a
            pB.x *= b
            pB.y *= b
            pB.z *= b
            pA.x += pB.x
            pA.y += pB.y
            pA.z += pB.z
            pA.normalize();
            mSegments[i].setEndTangent(pA);
        }

        val endPosition = Point()
        endPosition.x = mSegments[amount - 2].begin.tangent.x
        endPosition.y = mSegments[amount - 2].begin.tangent.y
        endPosition.z = mSegments[amount - 2].begin.tangent.z
        endPosition.normalize();
        mSegments[amount - 2].setEndTangent(endPosition)
    }
}
