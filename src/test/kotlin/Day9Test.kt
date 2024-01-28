import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day9Test {

    @Test
    fun testExtrapolateForwards() {
        val testHistory = listOf(10, 13, 16, 21, 30, 45)
        assertEquals(68, Report(testHistory).extrapolate())
    }

    @Test
    fun testExtrapolateBackwards() {
        val testHistory = listOf(10, 13, 16, 21, 30, 45)
        assertEquals(5, Report(testHistory).extrapolate(forwards = false))
    }
}