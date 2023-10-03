package entity

import processing.core.PImage
import sprites
import util.CardinalDirection
import util.Vector2

class Player(position: Vector2) : Entity(position) {
    private var moving: Boolean = false
    private var direction: CardinalDirection = CardinalDirection.DOWN
    override fun getSprite(): PImage = if (!moving) {
        sprites["player_idle"]!!
    } else {
        sprites["null"]!!
    }
}
