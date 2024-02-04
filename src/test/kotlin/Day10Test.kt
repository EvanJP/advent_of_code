import org.junit.jupiter.api.Test

import kotlin.test.assertEquals

class Day10Test {

    @Test
    fun computeDistances() {
        val maze = listOf(
            "..F7.",
            ".FJ|.",
            "SJ.L7",
            "|F--J",
            "LJ..."
        )
        assertEquals(8, PipeMaze(maze).computeDistances())
    }
}

/**
 * | - from
 * L - to
 *
 * from --> to = south
 * to --> from = north
 *
 * F - from
 * - - to
 * from --> to = east
 * to --> from = west
 */