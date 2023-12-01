package aoc23

object Day01 {

    private val word2NumberRegex = """(one|two|three|four|five|six|seven|eight|nine|\d)""".toRegex()
    private val word2NumberRegexRev = """(eno|owt|eerht|ruof|evif|xis|neves|thgie|enin|\d)""".toRegex()

    fun calculateCalibrationSum(input: String): Int =
        input.trim().lines().sumOf { line ->
            val digits = line.filter { it.isDigit() }
            val first = digits.first()
            val last = digits.last()
            "$first$last".toInt()
        }

    fun calculateCalibrationSum2(input: String): Int =
        input.trim().lines().sumOf { line ->
            val first = findFirst(line)
            val last = findLast(line)
            "$first$last".toInt()
        }

    private fun findFirst(input: String): Char =
        word2NumberRegex.replace(input) { m ->
            when (m.value) {
                "one" -> "1"
                "two" -> "2"
                "three" -> "3"
                "four" -> "4"
                "five" -> "5"
                "six" -> "6"
                "seven" -> "7"
                "eight" -> "8"
                "nine" -> "9"
                else -> m.value
            }
        }.first { it.isDigit() }

    private fun findLast(input: String): Char =
        word2NumberRegexRev.replace(input.reversed()) { m ->
            when (m.value) {
                "eno" -> "1"
                "owt" -> "2"
                "eerht" -> "3"
                "ruof" -> "4"
                "evif" -> "5"
                "xis" -> "6"
                "neves" -> "7"
                "thgie" -> "8"
                "enin" -> "9"
                else -> m.value
            }
        }.first { it.isDigit() }
}