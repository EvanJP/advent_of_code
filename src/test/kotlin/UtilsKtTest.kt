import org.junit.jupiter.api.Test

import kotlin.test.assertEquals

class UtilsKtTest {

    @Test
    fun testGetNeighborsCorner() {
        val expected: Set<Pair<Int, Int>> =
            setOf((0 to 1), (1 to 0), (1 to 1))
        assertEquals(
            expected, getNeighbors(0, 0, 5, 5, includeDiagonals = true)
        )
    }

    @Test
    fun testGetNeighborsNoDiagonals() {
        val expected: Set<Pair<Int, Int>> =
            setOf((1 to 1), (2 to 2), (0 to 2), (1 to 3))
        assertEquals(
            expected, getNeighbors(1, 2, 5, 5, includeDiagonals = false)
        )
    }
}