data class Schematic(val grid: List<String>) {

    private fun findAllNumbers(): List<Pair<Int, MatchResult>> =
        grid.flatMapIndexed { index: Int, s: String ->
            "\\d+".toRegex().findAll(s).map { index to it }
        }

    private fun isSpecialChar(row: Int, col: Int): Boolean {
        if (row in grid.indices && col in grid[0].indices) {
            return !(grid[row][col].isDigit() || grid[row][col] == '.')
        }
        return false
    }

    private fun isAPart(indexMatch: Pair<Int, MatchResult>): Boolean {
        val (index, match) = indexMatch
        val rowRange = ((index - 1)..(index + 1))
        val colRange = ((match.range.first - 1)..(match.range.last + 1))
        return rowRange.any { row ->
            colRange.any { col ->
                isSpecialChar(row, col)
            }
        }
    }

    fun getPartNumbers(): List<Int> = findAllNumbers().filter { isAPart(it) }
        .map { (_, match) -> match.value.toInt() }
}


fun main() {
    fun part1(input: List<String>): Int {
        val schematic = Schematic(input)
        return schematic.getPartNumbers().sum()
    }

    val lines = readInput("Day3Input")
    println("Part 1: ${part1(lines)}")
}