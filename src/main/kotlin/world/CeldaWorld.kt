package world

import entity.Entity
import kotlin.random.Random

class CeldaWorld(
    val name: String,
    val loadedEntities: MutableList<Entity> = mutableListOf(),
    val seed: Long
) {
    val rng = Random(seed)
}