import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { card ->
            val numbers = card.drop(9).split("|")
            val winningNumbers = numbers[0].trim().split("\\s+".toRegex()).toSet()
            val myNumbers = numbers[1].trim().split("\\s+".toRegex()).toSet()
            val intersection = myNumbers.intersect(winningNumbers)
            val numFound = intersection.size.toDouble()
            if(numFound > 0) 2.0.pow(numFound - 1).toInt() else 0
        }
    }

    data class Card(val no: Int, val winningNumbers: Set<String>, val myNumbers: Set<String>, var cardsWon: List<Card>? = null)

    fun processCards(cards: List<Card>, startFrom: Int): Int {
        val newCards: List<Card> = cards.drop(startFrom).flatMap { card ->
            val numCardsWon = if(card.cardsWon == null) card.myNumbers.intersect(card.winningNumbers).size else card.cardsWon!!.size
            val cardsWon: List<Card> = if(card.cardsWon == null) cards.filter { it.no > card.no && it.no <= card.no + numCardsWon }.toSet().toList() else card.cardsWon!!
            card.cardsWon = cardsWon
            cardsWon
        }
        return if(newCards.isEmpty()) cards.size else processCards(cards + newCards, cards.size)
    }

    fun part2(input: List<String>): Int {
        val cards = input.mapIndexed { i, card ->
            val numbers = card.drop(9).split("|")
            val winningNumbers = numbers[0].trim().split("\\s+".toRegex()).toSet()
            val myNumbers = numbers[1].trim().split("\\s+".toRegex()).toSet()
            Card(i + 1, winningNumbers, myNumbers)
        }
        return processCards(cards, 0)
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}