package world

import kotlinx.serialization.Serializable
import processing.core.PImage
import sprites
import util.Biome
import util.VectorInt2

@Serializable
data class FloorTile(
    override val sectionPos: VectorInt2,
    override val biome: Biome
) : Tile() {
    override fun getSprite(): PImage =
        when(biome) {
            Biome.OVERWORLD -> sprites["overworld_floor"]!!
            Biome.DESERT -> sprites["desert_floor"]!!
        }
}