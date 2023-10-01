package world

import entity.Entity

data class CeldaWorld(
    val name: String,
    val loadedEntities: MutableList<Entity> = mutableListOf(),
    val seed: Long
)
