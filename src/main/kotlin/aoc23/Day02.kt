package aoc23

import kotlin.math.max

object Day02 {

    data class Drawing(val redCubes: Int = 0, val greenCubes: Int = 0, val blueCubes: Int = 0)
    data class Game(val id: Int, val drawings: List<Drawing>)

    private val redRegEx = """(\d+) red""".toRegex()
    private val greenRegEx = """(\d+) green""".toRegex()
    private val blueRegEx = """(\d+) blue""".toRegex()

    fun sumOfPossibleGameIds(input: String, allowedRed: Int = 12, allowedGreen: Int = 13, allowedBlue: Int = 14): Int {
        val games = parseInput(input)
        val filtered = games.filterValues {
            isGamePossible(it, allowedRed, allowedGreen, allowedBlue)
        }
        return filtered.keys.sum()
    }

    fun sumOfPowerOfMinimalGames(input: String): Int {
        val games = parseInput(input)
        val minimals = games.map { minimalDrawingForGame(it.value) }
        return minimals.sumOf { it.redCubes * it.greenCubes * it.blueCubes }
    }

    private fun minimalDrawingForGame(game: Game): Drawing =
        game.drawings.fold(Drawing()) { acc, nextDrawing ->
            Drawing(
                max(acc.redCubes, nextDrawing.redCubes),
                max(acc.greenCubes, nextDrawing.greenCubes),
                max(acc.blueCubes, nextDrawing.blueCubes),
            )
        }

    private fun isGamePossible(game: Game, allowedRed: Int, allowedGreen: Int, allowedBlue: Int) =
        game.drawings.all { isDrawingPossible(it, allowedRed, allowedGreen, allowedBlue) }

    private fun isDrawingPossible(drawing: Drawing, allowedRed: Int, allowedGreen: Int, allowedBlue: Int): Boolean =
        drawing.redCubes <= allowedRed && drawing.greenCubes <= allowedGreen && drawing.blueCubes <= allowedBlue


    private fun parseInput(input: String): Map<Int, Game> {
        val lines = input.trim().lines()
        val gameList = lines.map { line ->
            val id = line.substringBefore(':').substringAfter(' ').toInt()
            val parts = line.substringAfter(':').split("; ")
            val drawings = parts.map { part ->
                val red = redRegEx.find(part)?.groupValues?.get(1)?.toInt() ?: 0
                val green = greenRegEx.find(part)?.groupValues?.get(1)?.toInt() ?: 0
                val blue = blueRegEx.find(part)?.groupValues?.get(1)?.toInt() ?: 0
                Drawing(red, green, blue)
            }
            Game(id, drawings)
        }
        return gameList.associateBy { it.id }
    }
}