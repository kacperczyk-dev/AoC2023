import kotlin.system.measureTimeMillis

fun main() {

    data class Node(val name: String, var left: Node? = null, var right: Node? = null)

    fun parseInput(input: List<String>): List<Node> {
        val nodes = input.drop(2).associate { line ->
            val name = line.take(3)
            name to Node(name)
        }
        nodes.forEach { (name, node) ->
            val line = input.first { line -> line.take(3) == name}
            node.left = nodes[line.drop(7).take(3)]!!
            node.right = nodes[line.drop(12).take(3)]!!
        }
        return nodes.filter{ (name, _) -> name.endsWith('A') }.values.toList()
    }

    tailrec fun traverseGraph(node: Node, directions: String, nextSteps: List<Char>, counter: Int): Int {
        if (node.name == "ZZZ") return counter
        val steps = nextSteps.drop(1).ifEmpty { directions.toList() }
        return when(nextSteps[0]) {
            'L' -> traverseGraph(node.left!!, directions, steps, counter + 1)
            else -> traverseGraph(node.right!!, directions, steps, counter + 1)
        }
    }

    fun part1(input: List<String>): Int {
        val directions = input[0]
        val root = parseInput(input).first{ node -> node.name == "AAA" }
        return traverseGraph(root, directions, directions.toList(), 0)
    }


    fun traverseGraph(node: Node, directions: String): Long {
        var counter = 0L
        var current: Node = node
        while (!current.name.endsWith('Z')) {
            current = when (directions[(counter % directions.length).toInt()]) {
                'L' -> current.left!!
                else -> current.right!!
            }
            counter++
        }
        return counter
    }

    fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }

    fun lcm(a: Long, b: Long): Long {
        return a / gcd(a, b) * b // Be careful with large numbers due to potential overflow
    }

    fun lcmOfList(numbers: List<Long>): Long {
        return numbers.reduce { acc, num -> lcm(acc, num) }
    }

    fun part2(input: List<String>): Long {
        var res = -1L
        val timeTaken = measureTimeMillis {
            val directions = input[0]
            val roots = parseInput(input)
            val results = roots.map { root ->
                traverseGraph(root, directions)
            }
            res = lcmOfList(results)
        }
        println("Time taken: ${timeTaken / 1000} s")
        return res
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
