fun main() {

    tailrec fun createSequences(seq: List<Int>, acc: MutableList<List<Int>>): List<List<Int>> {
        acc.add(seq)
        if(seq.firstOrNull { it != 0 } == null) return acc
        val nextSeq = (0 until seq.size - 1).map { i ->
            val curr = seq[i]
            val next = seq[i + 1]
            next - curr
        }
        return createSequences(nextSeq, acc)
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { readings ->
            val sequences = createSequences(readings.trim().split(" ").map { it.toInt() }, mutableListOf())
            val seqReversed = sequences.reversed().map { seq -> seq.reversed() }.map { seq -> seq[0]}.toMutableList()
            seqReversed.indices.forEach { i ->
                if(i != 0) seqReversed[i] = seqReversed[i] + seqReversed[i - 1]
            }
            seqReversed.last()
        }
    }


    fun part2(input: List<String>): Int {
        return input.sumOf { readings ->
            val sequences = createSequences(readings.trim().split(" ").map { it.toInt() }, mutableListOf())
            val seqReversed = sequences.reversed().map { seq -> seq[0]}.toMutableList()
            seqReversed.indices.forEach { i ->
                if(i != 0) seqReversed[i] = seqReversed[i] - seqReversed[i - 1]
            }
            seqReversed.last()
        }
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
