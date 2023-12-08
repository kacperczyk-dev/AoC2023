fun main() {

    data class Node(val name: String, var left: Node? = null, var right: Node? = null)

    fun parseInput(input: List<String>): Node {
        val nodes = input.drop(2).map { line ->
            val name = line.take(3)
            name to Node(name)
        }.toMap()
        nodes.forEach { (name, node) ->
            val line = input.first { line -> line.take(3) == name}
            node.left = nodes[line.drop(7).take(3)]!!
            node.right = nodes[line.drop(12).take(3)]!!
        }
        return nodes["AAA"]!!
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
        val root = parseInput(input)
        return traverseGraph(root, directions, directions.toList(), 0)
    }


    fun part2(input: List<String>): Int {
        val directions = input[0]
       return 1
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}