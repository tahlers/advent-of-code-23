package aoc23

import kotlin.math.abs

typealias RowAndColumnIndexes = Pair<Set<Long>, Set<Long>>

object Day11 {

    data class Pos(val x: Long, val y: Long)

    data class Image(val galaxies: Set<Pos>)

    fun calculateSumOfShortestPaths(input: String, expansionCount: Int = 2): Long {
        val originalImage = parseInput(input)
        val expanded = expand(originalImage, expansionCount)
        val galaxyPairs = galaxyPairs(expanded.galaxies, expanded.galaxies)
        val distances = galaxyPairs.map { (galaxy1, galaxy2) ->
            abs(galaxy1.x - galaxy2.x) + abs(galaxy1.y - galaxy2.y)
        }
        return distances.sum() / 2 // half the sum because we have double distances a->b and b->a
    }

    private fun expand(image: Image, expansionCount: Int): Image {
        val (emptyRows, emptyColumns) = emptyRowsAndColumns(image)
        val expandedGalaxies = image.galaxies.map { galaxy ->
            val emptyRowsBefore = emptyRows.filter { it < galaxy.y }.size
            val emptyColsBefore = emptyColumns.filter { it < galaxy.x }.size
            Pos(galaxy.x + (emptyColsBefore * (expansionCount - 1)).toLong(),
                galaxy.y + (emptyRowsBefore * (expansionCount - 1)).toLong())
        }
        return Image(expandedGalaxies.toSet())
    }

    private fun emptyRowsAndColumns(image: Image): RowAndColumnIndexes {
        val maxX = image.galaxies.maxOf { it.x }
        val maxY = image.galaxies.maxOf { it.y }
        val rowIndexes = (0..maxY).filter { idx -> image.galaxies.none { it.y == idx } }
        val columnIndexes = (0..maxX).filter { idx -> image.galaxies.none { it.x == idx } }
        return rowIndexes.toSet() to columnIndexes.toSet()
    }

    private fun galaxyPairs(c1: Set<Pos>, c2: Set<Pos>): Set<Pair<Pos, Pos>> {
        return c1.flatMap { left ->
            c2.mapNotNull { right ->
                if (left != right) left to right else null
            }
        }.toSet()
    }

    private fun parseInput(input: String): Image {
        val lines = input.trim().lines()
        val galaxies = lines.flatMapIndexed { y: Int, line: String ->
            line.mapIndexedNotNull { x, c ->
                if (c == '#') Pos(x.toLong(), y.toLong()) else null
            }
        }
        return Image(galaxies.toSet())
    }
}