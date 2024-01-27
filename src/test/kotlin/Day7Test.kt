import kotlin.test.Test
import kotlin.test.assertEquals

class Day7Test {
    @Test
    fun testHandCompareTo() {
        val handOne = Hand("AAAAA")
        val handTwo = Hand("AAAAQ")
        assert(handOne > handTwo)
    }

    @Test
    fun testHandCompareToEqualHandTypes() {
        val handOne = Hand("AAAQQ")
        val handTwo = Hand("AAQQQ")
        assert(handOne > handTwo)
    }

    @Test
    fun testHandCompareToEqualHands() {
        val handOne = Hand("12345")
        val handTwo = Hand("12345")
        assert(handOne.compareTo(handTwo) == 0)
    }


    @Test
    fun testTotalWinnings() {
        val hands = listOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483"
        )
        assertEquals(6440, totalWinnings(hands))
    }

    @Test
    fun testTotalWinningsJoker() {
        val hands = listOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483"
        )
        assertEquals(5905, totalWinnings(hands, true))
    }
}