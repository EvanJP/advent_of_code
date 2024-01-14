/**
 * A representation of the gondola's schematic.
 *
 * @property grid The string representation of the Schematic.
 */
data class Schematic(val grid: List<String>) {

    /**
     * Takes the regex and finds all matches in the grid.
     */
    private fun findAll(regex: Regex) =
        grid.flatMapIndexed { index: Int, s: String ->
            regex.findAll(s).map { index to it }
        }

    /**
     * Accesses the grid and finds all numbers.
     */
    private fun findAllNumbers(): List<Pair<Int, MatchResult>> =
        findAll("\\d+".toRegex())


    /**
     * Find all "gears", characters that equal `*`.
     */
    private fun findAllGears(): List<Pair<Int, MatchResult>> =
        findAll(Regex.fromLiteral("*"))

    /**
     * Returns whether the character in the grid is a digit or period.
     *
     * @param row The row index in the grid to check.
     * @param col The column index in the grid to check.
     */
    private fun isSpecialChar(row: Int, col: Int): Boolean {
        if (row in grid.indices && col in grid[0].indices) {
            return !(grid[row][col].isDigit() || grid[row][col] == '.')
        }
        return false
    }

    /**
     * Returns all adjacent indices around the `indexMatch`.
     */
    private fun getBoundingBox(
        indexMatch: Pair<Int, MatchResult>
    ): Pair<IntRange, IntRange> {
        val (index, match) = indexMatch
        val rowRange = ((index - 1)..(index + 1))
        val colRange = ((match.range.first - 1)..(match.range.last + 1))
        return rowRange to colRange
    }

    /**
     * Returns whether the number in the grid is a "part".
     *
     * @param indexMatch The index and `MatchResult` to check.
     */
    private fun isAPart(indexMatch: Pair<Int, MatchResult>): Boolean {
        val (rowRange, colRange) = getBoundingBox(indexMatch)
        return rowRange.any { row ->
            colRange.any { col ->
                isSpecialChar(row, col)
            }
        }
    }

    private fun IntRange.isOverlappingWith(range: IntRange): Boolean {
        return range.first() <= this.last && range.last() >= this.first
    }

    /**
     * Returns the gear ratio of the regex `MatchResult` if there are only 2
     * part numbers around it. Else return null.
     */
    private fun getGearRatio(indexMatch: Pair<Int, MatchResult>): Int? {
        val (rowRange, colRange) = getBoundingBox(indexMatch)
        val partNumbers =
            rowRange.flatMap { "\\d+".toRegex().findAll(grid[it]) }
                .filter { it.range.isOverlappingWith(colRange) }
                .map { it.value.toInt() }
        return partNumbers.takeIf { it.size == 2 }?.let { it[0] * it[1] }
    }


    /**
     * Get all numbers in the grid which count as "parts".
     *
     * "Parts" are numbers in the grid which are adjacent (diagonally included)
     * to any character that is not a period or digit.
     */
    fun getPartNumbers(): List<Int> = findAllNumbers().filter { isAPart(it) }
        .map { (_, match) -> match.value.toInt() }

    /**
     * Get all the "gear ratios" in the grid.
     *
     * "Gear Ratios" are the multiplication of part numbers when an asterisk,
     * `*`, is adjacent to only 2 part numbers.
     */
    fun getGearRatios(): List<Int> =
        findAllGears().mapNotNull { getGearRatio(it) }
}


fun main() {
    fun part1(input: List<String>): Int {
        val schematic = Schematic(input)
        return schematic.getPartNumbers().sum()
    }

    fun part2(input: List<String>): Int {
        val schematic = Schematic(input)
        return schematic.getGearRatios().sum()
    }

    val lines = readInput("Day3Input")
    println("Part 1: ${part1(lines)}")
    println("Part 2: ${part2(lines)}")
}