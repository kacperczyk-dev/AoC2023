import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicLong
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

    fun part2(input: List<String>): Long {
        var min = AtomicLong(-1L)
        val timeTaken = measureTimeMillis {
            runBlocking {
                val x = input[0].drop(6).trim().split(" ").map { it.toLong() }
                val seedRanges = x.mapIndexedNotNull { i, v -> if(i % 2 == 0 && i < x.size - 1) v until v + x[i+1] else null}
                val maps = createMaps(input.drop(2))
                seedRanges.forEach { range ->
                    async {
                        range.forEach { seed ->
                            val loc = processConversion(seed, maps, 0)
                            if(min.get() == -1L || loc < min.get()) {
                                min.set(loc)
                                println("Seed: $seed, Location: $min")
                            }
                        }
                    }
                }
            }
        }
        println("Time taken: ${timeTaken / 1000} s")
        return min.get() // correct! 79004094, takes ~ 22 minutes (1343 seconds)
    }


        // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

        val input = readInput("Day05")
        part1(input).println()
        part2(input).println()
    }