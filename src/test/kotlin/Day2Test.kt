import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CubeSetTest {
    @Test
    fun testParseValidString() {
        val testCubeSet = CubeSet(1, 2, 3)
        val validString = "1 red, 2 green, 3 blue"
        assertEquals(testCubeSet, CubeSet.parse(validString))
    }

    @Test
    fun testParseInvalidString() {
        val invalidString = "red 1, green 2, blue 3"
        assertFailsWith<Exception>(message = "Could not parse color", block = {
            val shouldNotBeCreated = CubeSet.parse(invalidString)
        })
    }
}

class GameTest {

    @Test
    fun testIsPossible() {
        val possibleCubeSets = listOf(CubeSet(0, 2, 3), CubeSet(4, 5, 6))
        val possibleGame = Game(0, possibleCubeSets)
        assertTrue(possibleGame.isPossible())
    }

    @Test
    fun testIsNotPossible() {
        val impossibleCubeSets = listOf(CubeSet(0, 2, 3), CubeSet(14, 5, 6))
        val impossibleGame = Game(0, impossibleCubeSets)
        assertFalse(impossibleGame.isPossible())
    }


    @Test
    fun minCubes() {
        val cubeSets = listOf(CubeSet(1, 4, 5), CubeSet(2, 2, 7))
        val game = Game(0, cubeSets)
        assertEquals(2 * 4 * 7, game.minCubes())
    }
}

class Day2KtTest {

    @Test
    fun part1() {
    }

    @Test
    fun part2() {
    }
}

