
fun main() {
    val r = "\\d+".toRegex()

    fun part1(input: List<String>): Int {
        val specialChars = listOf("@", "#", "$", "%", "&", "*", "-", "=", "+", "/")
        val noOfLines = input.size
        val lineLength = input[0].length
        val partNumbers = input.flatMapIndexed { i, currLine ->
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
        return partNumbers.sum()
    }


    fun part2(input: List<String>): Int {
        val rStar = "\\*".toRegex()
        val gearsPerLine: MutableMap<Int, List<MatchResult>> = mutableMapOf()
        val numbersPerLine: MutableMap<Int, List<MatchResult>> = mutableMapOf()
        input.forEachIndexed { i, line ->
            numbersPerLine[i] = r.findAll(line).toList()
            gearsPerLine[i] = rStar.findAll(line).toList()
        }
        val gearRatios = gearsPerLine.flatMap { (lineNo, gears) ->
            gears.map { gear ->
                val i = gear.range.first
                val numbersOnSameLine = numbersPerLine[lineNo]
                val numbersOnPrevLine = numbersPerLine[lineNo - 1]
                val numbersOnNextLine = numbersPerLine[lineNo + 1]
                val numLeft = numbersOnSameLine?.firstOrNull { number -> number.range.last == i - 1 }?.value?.toInt()
                val numRight = numbersOnSameLine?.firstOrNull { number -> number.range.first == i + 1 }?.value?.toInt()
                val numAbove = numbersOnPrevLine?.filter { number -> (number.range.first <= i && number.range.last >= i) || number.range.last == i - 1 || number.range.first == i + 1 }?.map { it.value.toInt() } ?: emptyList()
                val numBelow = numbersOnNextLine?.filter { number -> (number.range.first <= i && number.range.last >= i) || number.range.last == i - 1 || number.range.first == i + 1 }?.map { it.value.toInt() } ?: emptyList()
                val potentialNums = listOfNotNull(numLeft, numRight) + numAbove + numBelow
                if(potentialNums.size == 2) potentialNums.first() * potentialNums.last() else 0
            }
        }
       return gearRatios.sum()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
