
fun main() {

    // Get first and last digit in a string, concatenate them together eg 1 & 1 will be 11 and then sum all such numbers
    fun part1(input: List<String>): Int {
        val r = "\\d".toRegex()
        val numbers = input.map {
            val firstNum = r.find(it)!!.value
            val lastNum = r.find(it.reversed())!!.value
            "$firstNum$lastNum".toInt()
        }
        return numbers.sum()
    }

    // Same as part one but now we could also have digits spelled out as words
    fun part2(input: List<String>): Int {
        val spelledDigits = mapOf("zero" to "0", "one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5", "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9")
        val r = "\\d|(?=(zero|one|two|three|four|five|six|seven|eight|nine))".toRegex()
        val numbers = input.map {
            val firstNum = r.findAll(it).first().groupValues.joinToString("")
            val lastNum = r.findAll(it).last().groupValues.joinToString("")
            val fn = if(spelledDigits.containsKey(firstNum)) {
                spelledDigits[firstNum]
            } else firstNum
            val ln = if(spelledDigits.containsKey(lastNum)) {
                spelledDigits[lastNum]
            } else lastNum
            val x = "$fn$ln".toInt()
            x

        }
        return numbers.sum()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
