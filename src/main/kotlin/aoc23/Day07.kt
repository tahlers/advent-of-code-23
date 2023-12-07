package aoc23

object Day07 {

    enum class Card {
        JOKER_PLAY, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JOKER, QUEEN, KING, ACE,
    }

    enum class HandType {
        HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND
    }

    data class Hand(val cards: List<Card>, val bid: Long, val isJokerPlay: Boolean = false) : Comparable<Hand> {

        private val type = if (isJokerPlay) calculateJokerPlayType() else calculateType()

        private fun calculateType(): HandType {
            val cardMap = cards.groupBy { it }
            return when {
                cardMap.size == 1 -> HandType.FIVE_OF_A_KIND
                cardMap.any { it.value.size == 4 } -> HandType.FOUR_OF_A_KIND
                cardMap.size == 2 -> HandType.FULL_HOUSE
                cardMap.any { it.value.size == 3 } -> HandType.THREE_OF_A_KIND
                cardMap.size == 3 -> HandType.TWO_PAIR
                cardMap.size == 4 -> HandType.ONE_PAIR
                else -> HandType.HIGH_CARD
            }
        }

        private fun calculateJokerPlayType(): HandType {
            val cardMap = cards.groupBy { it }
            val jokerCount = cardMap[Card.JOKER_PLAY]?.size ?: 0
            val normalType = calculateType()
            return when {
                normalType == HandType.FOUR_OF_A_KIND && jokerCount >= 1 -> HandType.FIVE_OF_A_KIND
                normalType == HandType.THREE_OF_A_KIND && jokerCount == 3 && cardMap.size == 2 -> HandType.FIVE_OF_A_KIND
                normalType == HandType.THREE_OF_A_KIND && jokerCount == 3 && cardMap.size == 3 -> HandType.FOUR_OF_A_KIND
                normalType == HandType.THREE_OF_A_KIND && jokerCount == 2 -> HandType.FIVE_OF_A_KIND
                normalType == HandType.THREE_OF_A_KIND && jokerCount == 1 -> HandType.FOUR_OF_A_KIND
                normalType == HandType.FULL_HOUSE && jokerCount > 0 -> HandType.FIVE_OF_A_KIND
                normalType == HandType.TWO_PAIR && jokerCount > 1 -> HandType.FOUR_OF_A_KIND
                normalType == HandType.TWO_PAIR && jokerCount == 1 -> HandType.FULL_HOUSE
                normalType == HandType.ONE_PAIR && jokerCount == 2 -> HandType.THREE_OF_A_KIND
                normalType == HandType.ONE_PAIR && jokerCount == 1 -> HandType.THREE_OF_A_KIND
                normalType == HandType.HIGH_CARD && jokerCount == 1 -> HandType.ONE_PAIR
                else -> normalType
            }
        }

        override fun compareTo(other: Hand): Int = compareBy(
            Hand::type,
            { it.cards[0] },
            { it.cards[1] },
            { it.cards[2] },
            { it.cards[3] },
            { it.cards[4] },
        ).compare(this, other)
    }

    fun calculateTotalWinnings(input: String, isJokerPlay: Boolean = false): Long {
        val hands = parseInput(input, isJokerPlay)
        val sortedWithRanks = hands.sorted().withIndex()
        val withWinnings = sortedWithRanks.map { it.value.bid * (it.index + 1) }
        return withWinnings.sum()
    }

    private fun parseInput(input: String, isJokerPlay: Boolean = false): List<Hand> {
        val lines = input.trim().lines()
        return lines.map { line ->
            val bid = line.substringAfter(' ').toLong()
            val chars = line.substringBefore(' ')
            val cards = chars.map { charToCard(it, isJokerPlay) }
            Hand(cards, bid, isJokerPlay)
        }
    }

    private fun charToCard(c: Char, isJokerPlay: Boolean = false): Card =
        when (c) {
            '2' -> Card.TWO
            '3' -> Card.THREE
            '4' -> Card.FOUR
            '5' -> Card.FIVE
            '6' -> Card.SIX
            '7' -> Card.SEVEN
            '8' -> Card.EIGHT
            '9' -> Card.NINE
            'T' -> Card.TEN
            'J' -> if (isJokerPlay) Card.JOKER_PLAY else Card.JOKER
            'Q' -> Card.QUEEN
            'K' -> Card.KING
            'A' -> Card.ACE
            else -> throw IllegalArgumentException("Should never happen here!")
        }
}