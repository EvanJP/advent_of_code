class Hand(
    private val labels: String, private val jokerWildcard: Boolean = false
) : Comparable<Hand> {
    private val labelRanks: List<Char> =
        listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    private val labelRanksJokerWildcard: List<Char> =
        listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

    private enum class HandType(val value: Int) {
        FIVE_OF_A_KIND(6),
        FOUR_OF_A_KIND(5),
        FULL_HOUSE(4),
        THREE_OF_A_KIND(3),
        TWO_PAIR(2),
        ONE_PAIR(1),
        HIGH_CARD(0)
    }

    private fun getCardCounts() = labels.groupingBy { it }.eachCount()

    private val handType: HandType by lazy {
        val cardCounts = getCardCounts()
        with(cardCounts.values) {
            when {
                any { it == 5 } -> HandType.FIVE_OF_A_KIND
                any { it == 4 } -> HandType.FOUR_OF_A_KIND
                any { it == 3 } && any { it == 2 } -> HandType.FULL_HOUSE
                any { it == 3 } -> HandType.THREE_OF_A_KIND
                count { it == 2 } == 2 -> HandType.TWO_PAIR
                count { it == 2 } == 1 -> HandType.ONE_PAIR
                else -> HandType.HIGH_CARD
            }
        }
    }

    private val handTypeWithJokerWildcard: HandType by lazy {
        val (maxType, maxCount) = with(getCardCounts()) {
            val highestCount = filter { it.key != 'J' }.maxByOrNull { it.value }
            val jokerCount = this['J'] ?: 0
            (highestCount?.key ?: 'J') to (highestCount?.value
                ?: 0) + jokerCount
        }
        val cardCounts =
            labels.filter { it != 'J' && it != maxType }.groupingBy { it }
                .eachCount()
        with(cardCounts.values) {
            when {
                maxCount == 5 -> HandType.FIVE_OF_A_KIND
                maxCount == 4 -> HandType.FOUR_OF_A_KIND
                maxCount == 3 && any { it == 2 } -> HandType.FULL_HOUSE
                maxCount == 3 -> HandType.THREE_OF_A_KIND
                maxCount == 2 && any { it == 2 } -> HandType.TWO_PAIR
                maxCount == 2 -> HandType.ONE_PAIR
                else -> HandType.HIGH_CARD
            }
        }
    }


    /**
     * Compares according to the [HandType]. If equal, then compare according
     * to the *first unequal* card, if it exists. Else, return equals.
     */
    override fun compareTo(other: Hand): Int {
        val handValue =
            if (jokerWildcard) handTypeWithJokerWildcard.value else handType.value
        val otherHandValue =
            if (jokerWildcard) other.handTypeWithJokerWildcard.value else other.handType.value
        val compHands = handValue.compareTo(otherHandValue)
        if (compHands != 0) {
            return compHands
        }
        val diffCard =
            labels.zip(other.labels).firstOrNull { it.first != it.second }
        val compRanks =
            if (jokerWildcard) labelRanksJokerWildcard else labelRanks
        return (compRanks.indexOf(diffCard?.first) - compRanks.indexOf(
            diffCard?.second
        ))
    }
}

fun getHandsAndBids(hands: List<String>): List<Pair<String, Int>> = hands.map {
    it.split(" ").let { (hand, bid) -> hand to bid.toInt() }
}

fun totalWinnings(hands: List<String>, jokerWildcard: Boolean = false): Int =
    getHandsAndBids(hands).sortedBy { (hand, _) -> Hand(hand, jokerWildcard) }
        .mapIndexed { index, (_, bid) -> (index + 1) * bid }.sum()

fun main() {
    fun partOne(input: List<String>): Int {
        return totalWinnings(input)
    }

    fun partTwo(input: List<String>): Int {
        return totalWinnings(input, true)
    }

    val lines = readInput("Day7Input")
    println("Part 1: ${partOne(lines)}")
    println("Part 2: ${partTwo(lines)}")
}