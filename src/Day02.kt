
fun main() {
    data class Selection(val red: Int, val blue: Int, val green: Int)
    data class Game(val no: Int, val selections: List<Selection>)

    fun parseGames(games: List<String>): List<Game> {
        return games.mapIndexed { idx, inputLine ->
            val selections = inputLine.substring(inputLine.indexOf(":") + 1)
                    .trim()
                    .split(";")
                    .map { selectionStr ->
                        val colorPattern = """(\d+)\s*(\w+)""".toRegex()
                        val colorMap = mutableMapOf<String, Int>()
                        colorPattern.findAll(selectionStr).forEach { matchResult ->
                            val (number, color) = matchResult.destructured
                            colorMap[color.trim()] = number.toInt()
                        }
                        Selection(red = colorMap["red"] ?: 0, blue = colorMap["blue"] ?: 0, green = colorMap["green"] ?: 0)
                    }
            Game(idx + 1, selections)
        }
    }

    // The bag contains only 12 red cubes, 13 green cubes, and 14 blue cubes.
    // Sum the game numbers of games that are possible with the above number of cubues.
    fun part1(input: List<String>): Int {
        val games = parseGames(input)
        val possibleGames = games.filter { game ->
            game.selections.filter { selection ->
                selection.red > 12 || selection.blue > 14 || selection.green > 13
            }.isEmpty()
        }
        return possibleGames.sumOf { it.no } //1931
    }

    // in each game you played, what is the fewest number of cubes of each color that could have been in the bag to make the game possible?
    // power = leastR * leastB * leastG
    // return sum of power
    fun part2(input: List<String>): Int {
        val games = parseGames(input)
        return games.map { game ->
            var leastR = 0
            var leastB = 0
            var leastG = 0
            game.selections.forEach { selection ->
                leastR = if(selection.red > leastR) selection.red else leastR
                leastB = if(selection.blue > leastB) selection.blue else leastB
                leastG = if(selection.green > leastG) selection.green else leastG
            }
            leastR * leastB * leastG
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
