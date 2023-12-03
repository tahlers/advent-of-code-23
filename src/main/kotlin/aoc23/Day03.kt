package aoc23

object Day03 {

    data class Pos(val x: Int, val y: Int)
    data class Part(val number: Int, val startPos: Pos, val endPos: Pos) {
        private val xRange = (startPos.x - 1)..(endPos.x + 1)
        val adjacentPositions = xRange.map { Pos(it, startPos.y - 1) } +
                listOf(Pos(startPos.x - 1, startPos.y), Pos(endPos.x + 1, endPos.y)) +
                xRange.map { Pos(it, startPos.y + 1) }
    }

    data class Symbol(val symbolChar: Char, val pos: Pos)
    data class Schematic(val parts: List<Part> = emptyList(), val symbols: Set<Symbol> = emptySet())

    private val numbersRegex = """(\d+)""".toRegex()
    private val symbolRegex = """[^0-9.]""".toRegex()

    fun calculateSumAdjacentToSymbols(input: String): Int {
        val schematic = parseSchematic(input)
        val adjacentParts = findAdjacentToSymbols(schematic)
        return adjacentParts.sumOf { it.number }
    }

    fun calculateSumOfGearRatios(input: String): Int {
        val schematic = parseSchematic(input)
        val gearCandidates = schematic.symbols.filter { it.symbolChar == '*' }
        val gearRatios = gearCandidates.map { gear ->
            val adjacentParts = schematic.parts.filter { it.adjacentPositions.contains(gear.pos) }
            if (adjacentParts.size > 1) {
                adjacentParts.fold(1) {acc, part -> acc * part.number}
            } else {
                0
            }
        }
        return gearRatios.sum()
    }

    private fun findAdjacentToSymbols(schematic: Schematic): List<Part> {
        val symbolPositions = schematic.symbols.map { it.pos }
        return schematic.parts.filter { part ->
            part.adjacentPositions.any { symbolPositions.contains(it) }
        }
    }

    private fun parseSchematic(input: String): Schematic {
        val lines = input.trim().lines()
        val folded = lines.fold(Schematic() to 0) { acc, line ->
            val numberMatches = numbersRegex.findAll(line)
            val parts = numberMatches.map { m ->
                Part(m.value.toInt(), Pos(m.range.first, acc.second), Pos(m.range.last, acc.second))
            }
            val symbols = symbolRegex.findAll(line).map { m ->
                Symbol(m.value.first(), Pos(m.range.first, acc.second))
            }
            val updatedSchematic = Schematic(acc.first.parts + parts, acc.first.symbols + symbols)
            updatedSchematic to acc.second + 1
        }
        return folded.first
    }
}