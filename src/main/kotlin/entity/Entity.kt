package entity

import processing.core.PImage
import sprites
import util.Vector2
import util.VectorInt2
import kotlin.math.floor

open class Entity(var position: Vector2) {
    fun getSectionPos(): VectorInt2 =
        VectorInt2(floor(position.x/512f).toInt(), floor(position.y/512f).toInt())
    open fun getSprite(): PImage = sprites["null"]!!
}
