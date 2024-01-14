import kotlin.test.Test
import kotlin.test.assertContentEquals

class Day3Test {
    private val schematic = Schematic(
        listOf(
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
    )

    @Test
    fun testGetPartNumbers() {
        val expected = listOf(467, 35, 633, 617, 592, 755, 664, 598)
        assertContentEquals(expected, schematic.getPartNumbers())
    }

    @Test
    fun testGetGearRatios() {
        val expected = listOf(16345, 451490)
        assertContentEquals(expected, schematic.getGearRatios())
    }
}