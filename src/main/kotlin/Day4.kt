import kotlin.math.pow

/**
 * A representation of a scratchcard.
 *
 * @property cardNumber The current card number.
 * @property winningNumbers The numbers you want to have.
 * @property currentNumbers The numbers you do have.
 */
data class Scratchcard(
    val cardNumber: Int, val winningNumbers: Set<Int>,
    val currentNumbers: Set<Int>
) {

    /**
     * Returns all numbers in winningNumbers which match currentNumbers.
     */
    fun matchingNumbers(): Set<Int> {
        return winningNumbers.intersect(currentNumbers)
    }

    /**
     * Returns the number of points the scratchcard is worth.
     *
     * This is determined by 2 ^ (matchingNumbers().size - 1)
     */
    fun points(): Int {
        return 2.toDouble().pow(matchingNumbers().size - 1).toInt()
    }

    companion object {
        fun parse(card: String): Scratchcard {
            val id =
                card.substringAfter(" ").substringBefore(":").trim().toInt()
            val numbersSet =
                { numbers: String ->
                    numbers.trim().split("\\s+".toRegex())
                        .map { it.trim().toInt() }
                        .toSet()
                }
            val winningNumbers =
                numbersSet(card.substringAfter(":").substringBefore("|"))
            val currentNumbers = numbersSet(card.substringAfter("|"))
            return Scratchcard(id, winningNumbers, currentNumbers)
        }
    }
}

/**
 * Counts the number of scratchcards if the size of `matchingNumbers`, `N`,
 * duplicates the next `N` cards.
 *
 * As per the problem statement, this implies a valid stack of scratchcards.
 * I.e. There is no OOB behavior.
 */
fun processScratchcardsStack(scratchcards: List<Scratchcard>): Int {
    var totalCards = scratchcards.size
    val cardsToProcess = ArrayDeque(scratchcards)
    while (cardsToProcess.isNotEmpty()) {
        val scratchcard = cardsToProcess.removeLast()
        val numberOfMatches = scratchcard.matchingNumbers().size
        totalCards += numberOfMatches
        cardsToProcess.addAll(
            scratchcard.cardNumber.let {
                scratchcards.subList(it, it + numberOfMatches)
            })
    }
    return totalCards
}

fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf { Scratchcard.parse(it).points() }

    fun part2(input: List<String>): Int =
        processScratchcardsStack(input.map { Scratchcard.parse(it) })

    val lines = readInput("Day4Input")
    println("Part 1: ${part1(lines)}")
    println("Part 2: ${part2(lines)}")
}