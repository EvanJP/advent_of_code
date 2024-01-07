import java.lang.Exception

private const val RED: Int = 12
private const val BLUE: Int = 14
private const val GREEN: Int = 13

data class CubeSet(val red: Int, val green: Int, val blue: Int) {
    companion object {
        fun parse(cubeStr: String): CubeSet {
            var red = 0
            var green = 0
            var blue = 0
            cubeStr.split(",").forEach { cube ->
                val c = cube.trim()
                val num = c.substringBefore(" ").toInt()
                when (c.substringAfter(" ")) {
                    "red" -> red = num
                    "green" -> green = num
                    "blue" -> blue = num
                    else -> throw Exception("Could not parse color")
                }
            }
            return CubeSet(red, green, blue)
        }
    }
}

private data class Game(val id: Int, val cubeSet: List<CubeSet>) {
    fun isPossible(): Boolean =
        cubeSet.all { it.red <= RED && it.green <= GREEN && it.blue <= BLUE }

    fun minCubes(): Int {
        var red = 0
        var green = 0
        var blue = 0
        cubeSet.forEach { cube ->
            red = maxOf(red, cube.red)
            green = maxOf(green, cube.green)
            blue = maxOf(blue, cube.blue)
        }
        return red * green * blue
    }

    companion object {
        fun parse(gameStr: String): Game {
            val id = gameStr.substringAfter(" ").substringBefore(":").toInt()
            val cubeSet: List<CubeSet> =
                gameStr.substringAfter(":").split(";")
                    .map { CubeSet.parse(it.trim()) }
            return Game(id, cubeSet)
        }
    }
}

fun part1(input: List<String>): Int {
    return input.map { Game.parse(it.trim()) }.filter { it.isPossible() }
        .sumOf { it.id }
}

fun part2(input: List<String>): Int {
    return input.map { Game.parse(it.trim()) }.sumOf { it.minCubes() }
}

fun main() {
    val lines = readInput("day2")
    println(part1(lines))
    println(part2(lines))
}