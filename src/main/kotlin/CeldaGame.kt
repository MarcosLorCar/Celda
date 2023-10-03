@file:OptIn(DelicateCoroutinesApi::class)

import entity.Player
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import util.VectorInt2
import world.CeldaWorld
import world.Section
import java.io.File
import kotlin.math.abs
import kotlin.random.Random

class CeldaGame(
    val mainPlayer: Player,
    val loadedSections: MutableCollection<Section> = mutableListOf(),
    private var worlds: MutableList<CeldaWorld> = mutableListOf(CeldaWorld("overworld", seed = Random.nextLong())),
    private val name: String,
) {
    var loadedWorld: CeldaWorld

    init {
        loadedWorld = worlds[0]
        gamePApplet.noiseSeed(loadedWorld.seed)
        val worldDir = File("data/games/$name")
        worldDir.mkdir()
        File(worldDir.path+"/${loadedWorld.name}").mkdir()
    }

    private val renderDistance: VectorInt2 = VectorInt2(2,1)
    private val sectionsToLoad = mutableListOf<VectorInt2>()

    fun loadWorld(index: Int): Unit = TODO()

    fun updateSections() {
        val unloadSections = mutableListOf<Section>()


        //Check sections to unload
        for (section in loadedSections) {
            if (abs(mainPlayer.getSectionPos().x - section.worldPos.x) > renderDistance.x || abs(mainPlayer.getSectionPos().y - section.worldPos.y) > renderDistance.y) {
                unloadSections.add(section)
            }
        }
        //Unload sections
        unloadSections.forEach { loadedSections.remove(it) }

        //Chech sections to load
        for (x in (-renderDistance.x..renderDistance.x)) {
            for (y in (-renderDistance.y..renderDistance.y)) {
                val sectionCoords = VectorInt2(mainPlayer.getSectionPos().x + x, mainPlayer.getSectionPos().y + y)
                if (loadedSections.find { it.worldPos == sectionCoords } == null)
                    sectionsToLoad.add(sectionCoords)
            }
        }

        for (s in sectionsToLoad) loadedSections.add(loadOrGenSection(s))
        sectionsToLoad.clear()
    }

    private fun loadOrGenSection(sectionCoords: VectorInt2): Section {
        val sectionFile = File("data/games/$name/${loadedWorld.name}/${sectionCoords.x}_${sectionCoords.y}.json")
        if (!sectionFile.exists()) {
            //generate it
            val newSection = Section.genSection(sectionCoords,loadedWorld.rng)
            sectionFile.createNewFile()
            sectionFile.outputStream().write(Json.encodeToString(newSection).toByteArray())
            return newSection
        }
        return Section.sectionFromFile(sectionFile)
    }

}