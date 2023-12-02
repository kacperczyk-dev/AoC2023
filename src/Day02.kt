
fun main() {
    data class Selection(val red: Int, val blue: Int, val green: Int)
    data class Game(val no: Int, val selections: List<Selection>)

    // The bag contains only 12 red cubes, 13 green cubes, and 14 blue cubes.
    // Sum the game numbers of games that are possible with the above number of cubues.
    fun part1(input: List<String>): Int {
        val games = input.mapIndexed { idx, inputLine ->
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
        val possibleGames = games.filter { game ->
            game.selections.filter { selection ->
                selection.red > 12 || selection.blue > 14 || selection.green > 13
            }.isEmpty()
        }
        return possibleGames.sumOf { it.no } //1931
    }

    //
    fun part2(input: List<String>): Int {
        return 2
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
