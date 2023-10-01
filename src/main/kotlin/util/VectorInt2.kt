package util

import kotlinx.serialization.Serializable
import kotlin.math.sqrt

@Serializable
data class VectorInt2(var x: Int, var y: Int) {
    operator fun minus(other: VectorInt2) =
        VectorInt2(x-other.x,y-other.y)

    fun mag(): Double =
        sqrt((x*x+y*y).toDouble())
}
