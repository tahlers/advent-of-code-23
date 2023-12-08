package aoc23

object Day08 {

    enum class Direction {
        LEFT, RIGHT
    }

    data class Turn(val name: String, val leftName: String, val rightName: String) {
        fun doTurn(direction: Direction) = if (direction == Direction.LEFT) leftName else rightName
    }

    data class PathStep(val stepCount: Int, val currentTurn: Turn)

    data class DesertMap(val directions: List<Direction>, val turnMap: Map<String, Turn>)

    fun stepsToReachTarget(input: String): Int {
        val desertMap = parseInput(input)
        val path = generatePathSequence(desertMap.turnMap["AAA"]!!, desertMap)
        val finish = path.first { it.currentTurn.name == "ZZZ" }
        return finish.stepCount
    }

    fun stepsToReachGhostTarget(input: String): Long {
        val desertMap = parseInput(input)
        val ghostStarts = desertMap.turnMap.filterKeys { it.endsWith('A') }.values
        val cycles = ghostStarts.map { generatePathSequence(it, desertMap) }
            .map { path -> path.first { it.currentTurn.name.endsWith('Z') } }
        val cycleLengths = cycles.map { it.stepCount }
        val lcm = findLCM(cycleLengths.map { it.toLong() })
        return lcm
    }

    private fun generatePathSequence(startTurn: Turn, desertMap: DesertMap): Sequence<PathStep> {
        return generateSequence(0) { it + 1 }.runningFold(PathStep(0, startTurn)) { acc, step ->
            val currentDirIndex = step % desertMap.directions.size
            val currentDir = desertMap.directions[currentDirIndex]
            val newTurn = acc.currentTurn.doTurn(currentDir)
            PathStep(acc.stepCount + 1, desertMap.turnMap[newTurn]!!)
        }
    }

    private fun parseInput(input: String): DesertMap {
        val directions = input.lineSequence().first().map { if (it == 'L') Direction.LEFT else Direction.RIGHT }
        val lines = input.trim().lines().drop(2)
        val turnMap = lines.associate { line ->
            val name = line.substringBefore('=').trim()
            val leftName = line.substringAfter('(').substringBefore(',')
            val rightName = line.substringAfter(", ").substringBefore(')')
            name to Turn(name, leftName, rightName)
        }
        return DesertMap(directions, turnMap)
    }
}