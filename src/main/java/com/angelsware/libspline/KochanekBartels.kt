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
        mSegments[0].setBeginTangent(positionA)

        val amount = getNumControlPoints()

        for (i in (0..(amount - 3))) {
            positionA.x = mSegments[i].end.position.x - mSegments[i].begin.position.x
            positionA.y = mSegments[i].end.position.y - mSegments[i].begin.position.y
            positionA.z = mSegments[i].end.position.z - mSegments[i].begin.position.z

            positionB.x = mSegments[i + 1].end.position.x - mSegments[i + 1].begin.position.x
            positionB.y = mSegments[i + 1].end.position.y - mSegments[i + 1].begin.position.y
            positionB.z = mSegments[i + 1].end.position.z - mSegments[i + 1].begin.position.z

            val a = 0.5 * (1.0 - tension) * (1.0 + continuity) * (1.0 + bias)
            val b = 0.5 * (1.0 - tension) * (1.0 - continuity) * (1.0 - bias)

            positionA.x *= a
            positionA.y *= a
            positionA.z *= a
            positionB.x *= b
            positionB.y *= b
            positionB.z *= b
            positionA.x += positionB.x
            positionA.y += positionB.y
            positionA.z += positionB.z
        }

        positionA.x = mSegments[amount - 2].begin.tangent.x
        positionA.y = mSegments[amount - 2].begin.tangent.y
        positionA.z = mSegments[amount - 2].begin.tangent.z
        mSegments[amount - 2].setEndTangent(positionA)
    }
}
