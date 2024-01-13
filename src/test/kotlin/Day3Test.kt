import kotlin.test.Test
import kotlin.test.assertContentEquals

class Day3Test {

    @Test
    fun testGetPartNumbers() {
        val testGrid = listOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598.."
        )
        val schematic = Schematic(testGrid)
        println(schematic.getPartNumbers().sum())
        val expected = listOf(467, 35, 633, 617, 592, 755, 664, 598)
        assertContentEquals(expected, schematic.getPartNumbers())
    }
}