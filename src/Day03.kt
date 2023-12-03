
fun main() {

    fun part1(input: List<String>): Int {
        val specialChars = listOf("@", "#", "$", "%", "&", "*", "-", "=", "+", "/")
        val r = "\\d+".toRegex()
        val noOfLines = input.size
        val lineLength = input[0].length
        val x= input.flatMapIndexed { i, currLine ->
                val prevLine = if(i != 0) input[i - 1] else null
                val nextLine = if(i < noOfLines - 1) input[i + 1] else null
                val numbers = r.findAll(currLine)
                numbers.map { mr ->
                    val searchLeft = mr.range.first - 1 >= 0
                    val searchRight = mr.range.last + 1 < lineLength
                    val range = IntRange(if(searchLeft) mr.range.first - 1 else mr.range.first, if(searchRight) mr.range.last + 1 else mr.range.last)
                    if(searchLeft && specialChars.contains(currLine[range.first].toString())) {
                        mr.value.toInt()
                    } else if(searchRight && specialChars.contains(currLine[range.last].toString())) {
                        mr.value.toInt()
                    } else if(prevLine != null && prevLine.subSequence(range.first, range.last + 1).any { c -> specialChars.contains(c.toString())}) {
                        mr.value.toInt()
                    } else if(nextLine != null && nextLine.subSequence(range.first, range.last + 1).any { c -> specialChars.contains(c.toString()) }) {
                        mr.value.toInt()
                    } else null
                }.filterNotNull()
        }
        return x.sum()
    }


    fun part2(input: List<String>): Int {
       return 1
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
