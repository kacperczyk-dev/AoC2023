// Pipe Shapes
// | is a vertical pipe connecting north and south.
// - is a horizontal pipe connecting east and west.
// L is a 90-degree bend connecting north and east.
// J is a 90-degree bend connecting north and west.
// 7 is a 90-degree bend connecting south and west.
// F is a 90-degree bend connecting south and east.
// . is ground; there is no pipe in this tile.
// S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.

enum class Pipe(val shape: String) {
    SRT("S"),
    GND("."),
    N_S("|"),
    E_W("-"),
    N_E("L"),
    N_W("J"),
    S_E("F"),
    S_W("7");


    companion object {
        fun fromShape(shape: String): Pipe? = entries.toTypedArray().find { it.shape == shape }
    }

}

enum class Direction {
    N, S, E, W;
}
fun main() {
    fun part1(input: List<String>): Int {
        var startingPoint = Pair(-1, -1)
        val pipes = input.mapIndexed { i, line ->
            val horizontal = line.toList().map { Pipe.fromShape(it.toString()) }
            if(horizontal.contains(Pipe.SRT)) startingPoint = i to horizontal.indexOf(Pipe.SRT)
            horizontal
        }
        var currCoords = Pair(startingPoint.first, startingPoint.second)
        var dir = Direction.W
        var counter = 0
        while (true) {
            val nextCoords = when(dir) {
                Direction.N -> currCoords.first - 1 to currCoords.second
                Direction.S -> currCoords.first + 1 to currCoords.second
                Direction.E -> currCoords.first to currCoords.second + 1
                Direction.W -> currCoords.first to currCoords.second - 1
            }
            val nextPipe = pipes[nextCoords.first][nextCoords.second]!!
            currCoords = nextCoords
            when(nextPipe) {
                Pipe.SRT -> break
                Pipe.GND -> break
                Pipe.N_S -> {
                    when(dir) {
                        Direction.N -> {
                            currCoords = nextCoords
                            dir = Direction.N
                            counter += 1
                        }
                        Direction.S -> {
                            currCoords = nextCoords
                            dir = Direction.S
                            counter += 1
                        }
                        else -> break
                    }
                }
                Pipe.E_W -> when(dir) {
                    Direction.E -> {
                        currCoords = nextCoords
                        dir = Direction.E
                        counter += 1
                    }
                    Direction.W -> {
                        currCoords = nextCoords
                        dir = Direction.W
                        counter += 1
                    }
                    else -> break
                }
                Pipe.N_E -> when(dir) {
                    Direction.S -> {
                        currCoords = nextCoords
                        dir = Direction.E
                        counter += 1
                    }
                    Direction.W -> {
                        currCoords = nextCoords
                        dir = Direction.N
                        counter += 1
                    }
                    else -> break
                }
                Pipe.N_W -> when(dir) {
                    Direction.S -> {
                        currCoords = nextCoords
                        dir = Direction.W
                        counter += 1
                    }
                    Direction.E -> {
                        currCoords = nextCoords
                        dir = Direction.N
                        counter += 1
                    }
                    else -> break
                }
                Pipe.S_E -> when(dir) {
                    Direction.N -> {
                        currCoords = nextCoords
                        dir = Direction.E
                        counter += 1
                    }
                    Direction.W -> {
                        currCoords = nextCoords
                        dir = Direction.S
                        counter += 1
                    }
                    else -> break
                }
                Pipe.S_W -> when(dir) {
                    Direction.N -> {
                        currCoords = nextCoords
                        dir = Direction.W
                        counter += 1
                    }
                    Direction.E -> {
                        currCoords = nextCoords
                        dir = Direction.S
                        counter += 1
                    }
                    else -> break
                }
            }
        }
        return if(counter % 2 == 0) counter / 2 else (counter + 1) / 2
    }


    fun part2(input: List<String>): Int {
        return 1
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}



//    fun findLoops(startingPoint: Pair<Int, Int>, currCoords: Pair<Int, Int>, pipes: List<List<String>>, dir: Direction, stepsCount: Int): Int {
//        if(stepsCount > 0 && currCoords.first == startingPoint.first && currCoords.second == startingPoint.second) return stepsCount
//        val sizeX = pipes[0].size
//        val sizeY = pipes.size
//        return when(dir) {
//            Direction.N -> if(startingPoint.second + 1 < sizeY) {
//                val nextDirection = Direction.fromShape(pipes[startingPoint.first][startingPoint.second + 1])!!
//                when(nextDirection) {
//                    Direction.N_S -> findLoops(startingPoint, Pair(startingPoint.first, startingPoint.second + 1), pipes, "N", stepsCount + 1)
//                    Direction.S_E -> findLoops(startingPoint, Pair(startingPoint.first, startingPoint.second + 1), pipes, "N", stepsCount + 1)
//                    Direction.S_W -> findLoops(startingPoint, Pair(startingPoint.first, startingPoint.second + 1), pipes, "N", stepsCount + 1)
//                    else -> -1
//                }
//            } else -1
//            "S" -> if(startingPoint.second - 1 >= 0) {
//                val nextDirection = Direction.fromShape(pipes[startingPoint.first][startingPoint.second - 1])!!
//                when(nextDirection) {
//                    Direction.N_S -> findLoops(startingPoint, Pair(startingPoint.first, startingPoint.second - 1), pipes, "N", stepsCount + 1)
//                    Direction.N_E -> findLoops(startingPoint, Pair(startingPoint.first, startingPoint.second - 1), pipes, "N", stepsCount + 1)
//                    Direction.N_W -> findLoops(startingPoint, Pair(startingPoint.first, startingPoint.second - 1), pipes, "N", stepsCount + 1)
//                    else -> -1
//                }
//            } else -1
//            "E" -> if(startingPoint.first + 1 < sizeX) {
//                val nextDirection = Direction.fromShape(pipes[startingPoint.first + 1][startingPoint.second])!!
//                when(nextDirection) {
//                    Direction.E_W -> findLoops(startingPoint, Pair(startingPoint.first + 1, startingPoint.second), pipes, "N", stepsCount + 1)
//                    else -> -1
//                }
//            } else -1
//            "W" -> if(startingPoint.first - 1 >= 0) {
//                val nextDirection = Direction.fromShape(pipes[startingPoint.first - 1][startingPoint.second])!!
//                when(nextDirection) {
//                    Direction.E_W -> findLoops(startingPoint, Pair(startingPoint.first - 1, startingPoint.second), pipes, "N", stepsCount + 1)
//                    else -> -1
//                }
//            } else -1
//            else -> -1
//        }
//    }
