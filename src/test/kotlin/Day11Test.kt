import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {

    @Test
    fun shortestPathBetweenGalaxies() {
        val image = listOf(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#....."
        )
        assertEquals(374, Data(image).shortestPathBetweenGalaxies())
    }
}