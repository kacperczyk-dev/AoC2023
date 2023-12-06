import java.util.*
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {

    data class ConversionRange(val dest: Long, val source: Long, val range: Long)
    data class ConversionMap(val order: Int, val name: String, val ranges: List<ConversionRange>)


    fun createMaps(input: List<String>): List<ConversionMap> {
        val str = input.joinToString("\n")
        val mapsTxt = str.split(Regex("\\n?\\r?^$\\r?\\n?", setOf(RegexOption.MULTILINE)))
        return mapsTxt.mapIndexed { i, mapTxt ->
            val lines = mapTxt.lines()
            val mapName = lines[0].dropLast(5)
            val ranges = lines.drop(1).map { line ->
                val rangeValues = line.split(" ").map { it.toLong() }
                ConversionRange(
                    rangeValues[0], rangeValues[1], rangeValues[2]
                )
            }.sortedBy { it.dest }
            ConversionMap(i, mapName, ranges)
        }
    }

    fun processConversion(value: Long, maps: List<ConversionMap>, order: Int): Long {
        val currMap = maps.first { it.order == order }
        val dest = currMap.ranges.mapNotNull { range ->
            if (value >= range.source && value < range.source + range.range) {
                range.dest + (value - range.source)
            } else null
        }
        val nextVal = if (dest.size == 1) dest[0] else value
        return if (order == maps.size - 1) nextVal else processConversion(nextVal, maps, order + 1)
    }

    fun part1(input: List<String>): Long {
        val seeds = input[0].drop(6).trim().split(" ").map { it.toLong() }
        val maps = createMaps(input.drop(2))
        val locations = seeds.map { seed ->
            processConversion(seed, maps, 0)
        }
        return locations.min()
    }

    fun doRangesOverlap(range1: LongRange, range2: LongRange): Boolean {
        return range1.first <= range2.last && range2.first <= range1.last
    }

    fun processReverse(maps: List<ConversionMap>, order: Int): ConversionRange? {
        for (currRange in maps[order].ranges) {
            for (prevRange in maps[order - 1].ranges) {
                if (doRangesOverlap(currRange.source until currRange.source + currRange.range, prevRange.dest until prevRange.dest + prevRange.range)) {
                    if (order == 1) return prevRange
                    return processReverse(maps, order - 1)
                }
            }
        }
        return null
    }

    fun part2(input: List<String>): Long {
        var min: Long = -1L
        val timeTaken = measureTimeMillis {
            val x = input[0].drop(6).trim().split(" ").map { it.toLong() }
            val maps = createMaps(input.drop(2))
            val humToLoc = maps[6]
            val seedRange = processReverse(maps, 6)!!
            println(seedRange)
            (seedRange.source until seedRange.source + seedRange.range).forEach { seed ->
                println(seed)
                val currVal = processConversion(seed, maps, 0)
                if (min == -1L || currVal < min) {
                    min = currVal
                }
            }
        }

        println("Time taken: ${timeTaken / 1000} s")
        return min // 616794786

        // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

        val input = readInput("Day05")
        part1(input).println()
        part2(input).println()
    }