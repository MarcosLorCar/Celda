import entity.Player
import processing.core.PApplet
import processing.core.PImage
import util.GameView
import util.Scene
import util.Vector2

val game = CeldaClient()
lateinit var loadedGame: CeldaGame
lateinit var mainView: GameView
val sprites: MutableMap<String, PImage> = mutableMapOf()

fun main(args: Array<String>) {
    //TEMP WORLD AND PLAYER CREATION
    loadedGame = CeldaGame(
        mainPlayer = Player(Vector2(256f,256f)),
        name = "TestGame",
    )

    PApplet.runSketch(arrayOf("CeldaClient"),game)
}

fun getSprite(s: String): PImage = game.run {
    loadImage(dataPath("sprites/$s.png")) ?: sprites["null"]!!
}

class CeldaClient : PApplet() {
    override fun settings() {
        fullScreen()
    }
    private val gameScene = Scene.GAME
    private lateinit var translate: Vector2

    override fun setup() {
        mainView = GameView(zoom = 2f)
        frameRate(60F)
        loadImages()
        surface.setIcon(sprites["icon"])
        translate = Vector2(width/2f,height/2f)
    }

    private fun loadImages() {
        sprites["null"] = loadImage(dataPath("sprites/null.png"))
        sprites["icon"] = getSprite("icon")
        sprites["overworld_floor"] = getSprite("overworld/floor")
        sprites["player_idle"] = getSprite("player/move_0")
        sprites["desert_floor"] = getSprite("desert/floor")
    }

    override fun draw() {
        //reset screen
        background(255)
        translate(translate.x,translate.y)
        loadedGame.mainPlayer.position.y += if (millis()%20000<10000) 2 else -2
        //Scene
        when(gameScene) {
            Scene.GAME -> {
                loadedGame.updateSections()
                drawWorld(loadedGame,mainView)
            }
        }
    }

    private fun drawWorld(game: CeldaGame, view: GameView): Unit = with(view) {
        imageMode(CORNER)
        stroke(0)
        fill(0f,0f)
        view.centreInWorld = game.mainPlayer.position

        //Terrain
        for (section in game.loadedSections) {
            val sectionOffset = Vector2(section.worldPos.x*512f,section.worldPos.y*512f)
            for (tile in section.tiles) {
                val tileOffset = Vector2(tile.sectionPos.x*32f,tile.sectionPos.y*32f)
                image(
                    tile.getSprite(),
                    sectionOffset.x + tileOffset.x - centreInWorld.x,
                    sectionOffset.y + tileOffset.y - centreInWorld.y,
                    32f,
                    32f
                )
            }
        }

        imageMode(CENTER)

        //Entities
        for(entity in game.loadedWorld.loadedEntities) {
        }

        //Main player
        game.mainPlayer.run {
            image(
                getSprite(),
                position.x - view.centreInWorld.x,
                position.y - view.centreInWorld.y
            )
        }

        fill(255f,0f,0f)
        textSize(100f)
        text(frameRate,0f,0f)
    }
}