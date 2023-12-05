import java.math.BigInteger
import kotlin.math.pow

fun main() {

    data class ConversionRange(val dest: BigInteger, val source: BigInteger, val range: BigInteger)
    data class ConversionMap(val order: Int, val name: String, val ranges: List<ConversionRange>)


    fun createMaps(input: List<String>): List<ConversionMap> {
        val str = input.joinToString("\n")
        val mapsTxt = str.split(Regex("\\n?\\r?^$\\r?\\n?", setOf(RegexOption.MULTILINE)))
        return mapsTxt.mapIndexed { i, mapTxt ->
            val lines = mapTxt.lines()
            val mapName = lines[0].dropLast(5)
            val ranges = lines.drop(1).map { line ->
                val rangeValues = line.split(" ").map { it.toBigInteger() }
                ConversionRange(
                    rangeValues[0],
                    rangeValues[1],
                    rangeValues[2]
                )
            }
            ConversionMap(i, mapName, ranges)
        }
    }

    fun processConversion(value: BigInteger, maps: List<ConversionMap>, order: Int): BigInteger {
        val currMap = maps.first { it.order == order }
        val dest = currMap.ranges.mapNotNull { range ->
            if(value >= range.source && value < range.source + range.range) range.dest + (value - range.source) else null
        }
        val nextVal =  if(dest.size == 1) dest[0] else value
        return if(order == maps.size - 1) nextVal else processConversion(nextVal, maps, order + 1)
    }

    fun part1(input: List<String>): BigInteger {
        val seeds = input[0].drop(6).trim().split(" ").map{ it.toBigInteger() }
        val maps = createMaps(input.drop(2))
        val locations = seeds.map { seed ->
            processConversion(seed, maps, 0)
        }
        return locations.min()
    }

    fun part2(input: List<String>): Int {

    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}