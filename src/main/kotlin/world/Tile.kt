package world

import kotlinx.serialization.Serializable
import processing.core.PImage
import util.Biome
import util.VectorInt2

@Serializable
sealed class Tile {
    abstract val biome: Biome
    abstract val sectionPos: VectorInt2
    abstract fun getSprite(): PImage
}
