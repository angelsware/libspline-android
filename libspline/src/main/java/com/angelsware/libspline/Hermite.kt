package com.angelsware.libspline

/**
 * Created by klarre
 * On 2018-05-14.
 */
abstract class Hermite(controlPoints: Array<ControlPoint>) : Curve {
    protected var mSegments: Array<Segment> = Array(controlPoints.size - 1, { Segment() })
    private var mLength = 0.0

    init {
        mSegments[0].begin = controlPoints[0]
        mSegments[0].end = controlPoints[1]
        for (i in 1..(controlPoints.size - 2)) {
            mSegments[i].begin = controlPoints[i]
            mSegments[i].end = controlPoints[i+1]
        }
    }

    override fun getWorldPosition(distance: Double): Point {
        var segment = 0
        val amount = mSegments.size - 1
        do {
            if (segment > amount) {
                return getPositionOnSegment(mSegments[amount].lengthFromBeginning / mSegments[amount].length, amount)
            }
            if ((mSegments[segment].lengthFromBeginning + mSegments[segment].length) >= distance) {
                break
            }
            ++segment
        } while (true)
        return getPositionOnSegment((distance - mSegments[segment].lengthFromBeginning) / mSegments[segment].length, segment)
    }

    override fun getPositionOnSegment(distance: Double, segment: Int): Point {
        val dMuld = distance * distance
        val fH1 = (2.0F * distance - 3.0F) * dMuld + 1.0F
        val fH2 = (distance - 2.0F) * dMuld + distance
        val fH3 = (dMuld - distance) * distance
        val fH4 = (-2.0F * distance + 3.0F) * dMuld

        val begin: ControlPoint = mSegments[segment].begin
        val end: ControlPoint = mSegments[segment].end

        val x = begin.position.x * fH1 + begin.tangent.x * fH2 + end.tangent.x * fH3 + end.position.x * fH4
        val y = begin.position.y * fH1 + begin.tangent.y * fH2 + end.tangent.y * fH3 + end.position.y * fH4
        val z = begin.position.z * fH1 + begin.tangent.z * fH2 + end.tangent.z * fH3 + end.position.z * fH4
        return Point(x, y, z)
    }

    override fun getTangent(distance: Double): Point {
        var segment = 0
        val amount = mSegments.size - 1
        do {
            if (segment > amount) {
                return getTangentOnSegment(mSegments[amount].lengthFromBeginning / mSegments[amount].length, amount)
            }
            if ((mSegments[segment].lengthFromBeginning + mSegments[segment].length) >= distance) {
                break
            }
            ++segment
        } while (true)
        return getTangentOnSegment((distance - mSegments[segment].lengthFromBeginning) / mSegments[segment].length, segment)
    }

    override fun getTangentOnSegment(distance: Double, segment: Int): Point {
        val fH1 = (6.0F * distance - 6.0F) * distance
        val fH2 = (2.0F * distance - 4.0F) * distance + 1.0F
        val fH3 = (3.0F * distance - 2.0F) * distance
        val fH4 = (-6.0F * distance + 6.0F) * distance

        val begin: ControlPoint = mSegments[segment].begin
        val end: ControlPoint = mSegments[segment].end

        val x = begin.position.x * fH1 + begin.tangent.x * fH2 + end.tangent.x * fH3 + end.position.x * fH4
        val y = begin.position.y * fH1 + begin.tangent.y * fH2 + end.tangent.y * fH3 + end.position.y * fH4
        val z = begin.position.z * fH1 + begin.tangent.z * fH2 + end.tangent.z * fH3 + end.position.z * fH4
        val point = Point(x, y, z)
        point.normalize()
        return point
    }

    final override fun calculateLength(resolution: Double) {
        var positionA: Point
        var positionB: Point
        val stepLength = 1.0 / resolution
        for (i in 0..(mSegments.size - 1)) {
            var segmentLength = 0.0
            var j = 0.0
            while (j < 1.0) {
                if (j > (1.0 - stepLength)) {
                    j = 1.0 - stepLength
                }
                positionA = getPositionOnSegment(j + stepLength, i)
                positionB = getPositionOnSegment(j, i)
                positionA.x -= positionB.x
                positionA.y -= positionB.y
                positionA.z -= positionB.z
                segmentLength += positionA.getLength()
                j += stepLength
            }

            mSegments[i].length = segmentLength
            mSegments[i].lengthFromBeginning = mLength
            mLength += segmentLength
        }
    }

    override fun getLength(): Double {
        return mLength
    }

    override fun getNumControlPoints(): Int {
        return mSegments.size + 1
    }

    override fun getSegment(segment: Int): Segment {
        return mSegments[segment]
    }
}
