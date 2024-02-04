import kotlin.math.abs
import kotlin.time.measureTime

private const val GALAXY = '#'
private const val SPACE = '.'

data class Data(val image: List<String>) {
    data class Galaxy(val x: Long, val y: Long) {
        fun distance(to: Galaxy): Long {
            return abs(to.y - y) + abs(to.x - x)
        }
    }

    fun shortestPathBetweenGalaxies(emptyExpansionFactor: Long = 1): Long {
        val galaxies = image.indices.flatMap { y ->
            image[y].indices.filter { x -> image[y][x] == GALAXY }.map { x ->
                Galaxy(x.toLong(), y.toLong())
            }
        }

        val emptyRows =
            image.indices.filter { r -> image[r].all { it == SPACE } }.toSet()

        val emptyColumns =
            image[0].indices.filter { c -> image.all { it[c] == SPACE } }
                .toSet()

        val galaxiesExpanded = galaxies.map {
            val x =
                it.x + emptyColumns.count { i -> i < it.x } * emptyExpansionFactor
            val y =
                it.y + emptyRows.count { i -> i < it.y } * emptyExpansionFactor
            Galaxy(x, y)
        }

        val galaxyPairs = galaxiesExpanded.flatMap { a ->
            galaxiesExpanded.filter { b -> b != a }.map { b ->
                a to b
            }
        }

        return galaxyPairs.sumOf { it.first.distance(it.second) } / 2
    }

}

fun main() {
    fun partOne(input: List<String>): Long =
        Data(input).shortestPathBetweenGalaxies()

    fun partTwo(input: List<String>): Long =
        Data(input).shortestPathBetweenGalaxies(1000000 - 1)

    val lines = readInput("Day11Input")
    val timeTaken = measureTime {
        println("Part 1: ${partOne(lines)}")
        println("Part 2: ${partTwo(lines)}")
    }
    println(timeTaken)
}