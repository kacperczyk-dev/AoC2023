import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {

    data class Node(val name: String, var left: Node? = null, var right: Node? = null)

    fun parseInput(input: List<String>): List<Node> {
        val nodes = input.drop(2).map { line ->
            val name = line.take(3)
            name to Node(name)
        }.toMap()
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

    // Function to calculate GCD using Euclidean algorithm
    fun calculateGCD(a: Int, b: Int): Int {
        var num1 = abs(a)
        var num2 = abs(b)
        while (num2 != 0) {
            val temp = num2
            num2 = num1 % num2
            num1 = temp
        }
        return num1
    }

    // Function to calculate LCM
    fun calculateLCM(a: Int, b: Int): Int {
        return if (a == 0 || b == 0) 0 else abs(a * b) / calculateGCD(a, b)
    }

    tailrec fun traverseGraph(node: Node, directions: String, nextSteps: List<Char>, counter: Int, e: Int): Int {
        var ee = e
        if (node.name.endsWith('Z')) {
            ee += 1
            if(ee == 2) {
                return counter
            }
        }
        val steps = nextSteps.drop(1).ifEmpty { directions.toList() }
        return when(nextSteps[0]) {
            'L' -> traverseGraph(node.left!!, directions, steps, counter + 1, ee)
            else -> traverseGraph(node.right!!, directions, steps, counter + 1, ee)
        }
    }

    fun part2(input: List<String>): Int {
        var res = -1
        val timeTaken = measureTimeMillis {
            val directions = input[0]
            val roots = parseInput(input)
            val results = roots.map { root ->
                traverseGraph(root, directions, directions.toList(), 0, 0)
            }
            res = results.reduce { acc, num -> calculateLCM(acc, num) }
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