import kotlin.math.pow

enum class HandType(val strength: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1)
}

fun main() {

    fun mapCardToValue(card: Char, jokerRule: Boolean): Int {
        return when (card) {
            'A' -> 99
            'K' -> 98
            'Q' -> 97
            'J' -> if(jokerRule) 1 else 96
            'T' -> 85
            else -> Character.getNumericValue(card)
        }
    }

    data class Hand(val cards: List<Char>, val bid: Int, val type: HandType, val jokerRule: Boolean = false) : Comparable<Hand> {
        fun calcCardsStrength(): Int {
            return cards.sumOf { card ->
                mapCardToValue(card, jokerRule)
            }
        }

        override fun compareTo(other: Hand): Int {
            return when {
                this.type != other.type -> this.type.strength.compareTo(other.type.strength)
                else -> {
                    var res = 0
                    for (i in this.cards.indices) {
                        res = mapCardToValue(this.cards[i], jokerRule).compareTo(mapCardToValue(other.cards[i], jokerRule))
                        if (res != 0) break
                    }
                    res
                }
            }
        }
    }

    fun parseHands(input: List<String>, jokerRule: Boolean = false): List<Hand> {
        return input.map { hand ->
            val (cards, bid) = hand.trim().split(" ")
            val cardsList = cards.toList()
            val cardCount = if(jokerRule) {
                cardsList.filterNot {it == 'J'}.groupingBy { it }.eachCount().values.toMutableList()
            } else {
                cardsList.groupingBy { it }.eachCount().values.toMutableList()
            }
            if(jokerRule) {
                val max = cardCount.maxOrNull()
                val indexOfMax = if(max != null) cardCount.indexOf(max) else 0
                if(cardCount.isEmpty()) cardCount.add(5) else cardCount[indexOfMax] = cardCount[indexOfMax] + (5 - cardCount.sum())
            }
            val type = when(cardCount.size) {
                1 -> HandType.FIVE_OF_A_KIND
                2 -> when {
                    4 in cardCount -> HandType.FOUR_OF_A_KIND
                    else -> HandType.FULL_HOUSE
                }
                3 -> when {
                    3 in cardCount -> HandType.THREE_OF_A_KIND
                    else -> HandType.TWO_PAIR
                }
                4 -> HandType.ONE_PAIR
                else -> HandType.HIGH_CARD
            }
            Hand(cardsList, bid.toInt(), type, jokerRule)
        }
    }

    fun part1(input: List<String>): Int {
        val hands = parseHands(input).sorted()
        return hands.mapIndexed { i, hand->
            (i + 1) * hand.bid
        }.sum()
    }


    fun part2(input: List<String>): Int {
        val hands = parseHands(input, true).sorted()
        return hands.mapIndexed { i, hand->
            (i + 1) * hand.bid
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}