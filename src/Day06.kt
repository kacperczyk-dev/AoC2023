import kotlin.math.pow

fun main() {

    fun parseInput(input: List<String>): Map<Int, Int> {
        val times = input[0].drop(5).trim().split("\\s+".toRegex()).map { it.toInt() }
        val dist  = input[1].drop(9).trim().split("\\s+".toRegex()).map { it.toInt() }
        return times.zip(dist).toMap()
    }

    fun part1(input: List<String>): Int {
        val bestScores = parseInput(input)
        return bestScores.map { (time, dist) ->
            var waysOfWinning = 0
            (0 until time).forEach { secsHolding ->
                if(secsHolding * (time - secsHolding) > dist) waysOfWinning++
            }
            waysOfWinning
        }.reduce { acc, i ->  i * acc}
    }


    fun parseInputPartTwo(input: List<String>): Map<Long, Long> {
        val time = input[0].drop(5).trim().replace("\\s+".toRegex(), "").toLong()
        val dist  = input[1].drop(9).trim().replace("\\s+".toRegex(), "").toLong()
        return mapOf(time to dist)
    }

    fun part2(input: List<String>): Int {
        val bestScore = parseInputPartTwo(input)
        var waysOfWinning = 0
        val (time, dist) = bestScore.entries.first()
        (0 until time).forEach { secsHolding ->
            if(secsHolding * (time - secsHolding) > dist) waysOfWinning++
        }
        return waysOfWinning
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}