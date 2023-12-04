package aoc23

import java.math.BigInteger

object Day04 {

    data class Card(val id: Int, val winningNumbers: Set<Int>, val numbers: Set<Int>) {
        val hits = numbers.filter { it in winningNumbers }.size
        fun calculateScore(): BigInteger =
            if (hits == 0) BigInteger.ZERO else BigInteger.valueOf(2L).pow(hits - 1)
    }

    private val cardRegex = """Card\s+(\d+):""".toRegex()
    private val whitespaceRegex = """\s+""".toRegex()

    fun calculateScore(input: String): BigInteger {
        val cards = parseInputToCards(input)
        return cards.sumOf { it.calculateScore() }
    }

    fun calculateNumberOfScratchcards(input: String): Int {
        val cards = parseInputToCards(input)
        val resultCountMap = calculateCardCountMap(cards)
        return resultCountMap.values.sum()
    }

    private fun calculateCardCountMap(cards: List<Card>): Map<Int, Int> {
        val initial = cards.associate { it.id to 1 }
        val resultCountMap = cards.fold(initial) { countMap, card ->
            val currentCardNumbers = countMap.getOrDefault(card.id, 0)
            val toCopyIds = (card.id + 1)..(card.id + card.hits)
            val updated = toCopyIds.fold(countMap) { acc, id ->
                acc + (id to (acc.getOrDefault(id, 0) + currentCardNumbers))
            }
            updated
        }
        return resultCountMap
    }

    private fun parseInputToCards(input: String): List<Card> {
        val lines = input.trim().lines()
        return lines.map { line ->
            val id = cardRegex.find(line)?.groupValues?.get(1)?.toInt()
            val winningNumbers = line.substringAfter(':').substringBefore('|')
                .split(whitespaceRegex)
                .filter { it.trim().isNotEmpty() }
                .map { it.toInt() }
            val numbers = line.substringAfter('|').split(whitespaceRegex)
                .filter { it.trim().isNotEmpty() }
                .map { it.toInt() }
            Card(id!!, winningNumbers.toSet(), numbers.toSet())
        }
    }
}