import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.math.pow

/**
 * Solves the quadratic equation to find all the ways to win.
 */
fun waysToWin(time: Double, distance: Double): Int {
    val rightNumerator = sqrt(time.pow(2) - (4 * -1 * -distance))
    val lowerBound = ceil((-time + rightNumerator) / -2)
    val upperBound = floor((-time - rightNumerator) / -2)
    return (upperBound - lowerBound + 1).toInt()
}

fun main() {
    fun part1(input: List<String>): Int {
        val getNums =
            { s: String ->
                "\\d+".toRegex().findAll(s).map { it.value.toDouble() }.toList()
            }
        val times: List<Double> = getNums(input[0])
        val distances: List<Double> = getNums(input[1])
        return times.zip(distances).map { waysToWin(it.first, it.second) }
            .fold(1) { acc, next ->
                acc * next
            }
    }

    fun part2(input: List<String>): Int {
        val getNum =
            { s: String ->
                "\\d+".toRegex().findAll(s).joinToString("") { it.value }
                    .toDouble()
            }
        return waysToWin(getNum(input[0]), getNum(input[1]))
    }

    val lines = readInput("Day6Input")
    println("Part 1: ${part1(lines)}")
    println("Part 2: ${part2(lines)}")
}