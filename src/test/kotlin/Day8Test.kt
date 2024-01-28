import org.junit.jupiter.api.Test

import kotlin.test.assertEquals

class Day8Test {

    @Test
    fun testStepsFromAAAToZZZ() {
        val input = listOf(
            "LLR", "", "AAA = (BBB, BBB)", "BBB = (AAA, ZZZ)",
            "ZZZ = (ZZZ, ZZZ)"
        )
        assertEquals(6, buildTree(input).traverse("AAA", "ZZZ"))
    }

    @Test
    fun testParallelTraverse() {
        val input = listOf(
            "LR",
            "",
            "11A = (11B, XXX)",
            "11B = (XXX, 11Z)",
            "11Z = (11B, XXX)",
            "22A = (22B, XXX)",
            "22B = (22C, 22C)",
            "22C = (22Z, 22Z)",
            "22Z = (22B, 22B)",
            "XXX = (XXX, XXX)",
        )
        assertEquals(6, buildTree(input).parallelTraverse())
    }
}