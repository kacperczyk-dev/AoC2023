import kotlin.math.abs

fun main() {

    fun transpose(matrix: List<List<String>>): MutableList<MutableList<String>> {
        val rows = matrix.size
        val cols = matrix.maxOfOrNull { it.size } ?: 0

        return List(cols) { col ->
            List(rows) { row ->
                matrix.getOrNull(row)?.getOrNull(col) ?: ""
            }.toMutableList()
        }.toMutableList()
    }


    fun part1(input: List<String>): Int {
        var matrix = input.map {
            it.toList().map { it.toString() }.toMutableList()
        }.toMutableList()
        var rowSize = input.size
        matrix.mapIndexedNotNull { i, row ->
            if(!row.joinToString("").contains("#")) i else null
        }.forEach { i -> matrix.add(i, MutableList(rowSize) { "0" }) }

        val transposedMatrix = transpose(matrix)
        rowSize = transposedMatrix[0].size
        transposedMatrix.mapIndexedNotNull { i, row ->
            if(!row.joinToString("").contains("#")) i else null
        }.forEach { i -> transposedMatrix.add(i, MutableList(rowSize) { "0" }) }

        matrix = transpose(transposedMatrix)

        val indicesOfGalaxies = matrix.flatMapIndexed { i, row ->
           row.mapIndexedNotNull { j, s ->
               if(s == "#") i to j else null
           }
        }

        val distances = indicesOfGalaxies.flatMapIndexed { i, (x1, y1) ->
            indicesOfGalaxies.mapIndexedNotNull { j, (x2, y2) ->
                if(i != j && j > i) abs((x2 - x1)) + abs((y2 - y1)) else null
            }
        }

        matrix.forEach{ println(it.joinToString("")) }


        return distances.sum()
    }


    fun part2(input: List<String>): Int {
        return 1
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}


