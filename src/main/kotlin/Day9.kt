data class Report(val history: List<Int>) {
    private fun extrapolate(currentHistory: List<Int>, forwards: Boolean): Int {
        if (currentHistory.all { it == currentHistory[0] }) {
            return currentHistory[0]
        }
        val next =
            extrapolate(currentHistory.zipWithNext { a, b -> b - a }, forwards)
        if (forwards) {
            return currentHistory.last() + next
        }
        return currentHistory.first() - next
    }

    /**
     * Finds the next value in the `history`. If `forward` is false, i.e.
     * "backwards", then return the value before the first value in `history`.
     */
    fun extrapolate(forwards: Boolean = true): Int {
        return extrapolate(history, forwards)
    }

    companion object {
        fun parse(history: String): Report {
            return Report(history.split(" ").map { it.toInt() })
        }
    }
}

fun main() {
    fun partOne(input: List<String>): Int {
        return input.sumOf { Report.parse(it).extrapolate() }
    }

    fun partTwo(input: List<String>): Int {
        return input.sumOf { Report.parse(it).extrapolate(forwards = false) }
    }

    val lines = readInput("Day9Input")
    println("Part 1: ${partOne(lines)}")
    println("Part 2: ${partTwo(lines)}")

}