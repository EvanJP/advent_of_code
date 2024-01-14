import kotlin.test.Test
import kotlin.test.assertEquals

class Day4Test {
    @Test
    fun testParseScratchcard() {
        val expectedScratchcard =
            Scratchcard(0, setOf(1, 2, 3, 4, 5), setOf(6, 7, 8, 9, 10, 11, 12))
        val cardString = "Card 0:  1  2  3  4  5 |  6  7  8  9 10 11 12"
        assertEquals(expectedScratchcard, Scratchcard.parse(cardString))
    }

    @Test
    fun testMatchingNumbers() {
        val winningNumbers = setOf(1, 2, 3, 4, 5)
        val currentNumbers = setOf(3, 4, 5, 6, 7, 8)
        assertEquals(
            setOf(3, 4, 5),
            Scratchcard(0, winningNumbers, currentNumbers).matchingNumbers()
        )
    }

    @Test
    fun testPoints() {
        val cardString = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        assertEquals(8, Scratchcard.parse(cardString).points())
    }

    @Test
    fun testProcessScratchcards() {
        val scratchcards =
            listOf(
                Scratchcard(1, setOf(1, 2, 3, 4, 5), setOf(2, 3)),
                Scratchcard(2, setOf(1), setOf(1)),
                Scratchcard(3, setOf(1), setOf(2))
            )
        assertEquals(7, processScratchcardsStack(scratchcards))
    }
}