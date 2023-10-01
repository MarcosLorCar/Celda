package world

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import util.Biome
import util.VectorInt2
import java.io.File
import kotlin.random.Random

@Serializable
data class Section(
    val worldPos: VectorInt2,
    val tiles: Array<Tile>
) {
    companion object {
        fun emptySection(x: Int, y: Int): Section {
            val tiles = mutableListOf<Tile>()
            for (i in 0..255) {
                tiles.add(i, FloorTile(VectorInt2(i %16,i/16), Biome.OVERWORLD))
            }
            return Section(VectorInt2(x,y),tiles.toTypedArray())
        }

        @OptIn(ExperimentalSerializationApi::class)
        fun sectionFromFile(sectionFile: File): Section =
            Json.decodeFromStream(sectionFile.inputStream())

        fun genSection(sectionCoords: VectorInt2, seed: Long): Section {
            val tiles = mutableListOf<Tile>()
            for (i in 0..255) {
                val xInWorld: Int = i%16 + sectionCoords.x*16
                val yInWorld: Int = i/16 + sectionCoords.y*16
                val xInSection: Int = i%16
                val yInSection: Int = i/16
                val biome = if (Random.nextBoolean()) Biome.OVERWORLD else Biome.DESERT
                tiles.add(i, FloorTile(VectorInt2(xInSection,yInSection),biome))
            }
            return Section(VectorInt2(sectionCoords.x,sectionCoords.y),tiles.toTypedArray())
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Section

        if (worldPos != other.worldPos) return false
        if (!tiles.contentEquals(other.tiles)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = worldPos.hashCode()
        result = 31 * result + tiles.contentHashCode()
        return result
    }
}