class Tree(private val instructions: String) {
    private data class Node(
        val name: String, val left: String?, val right: String?
    ) {
        val isStart: Boolean = name.endsWith('A')
        val isEnd: Boolean = name.endsWith('Z')
    }

    private val nodes = mutableMapOf<String, Node>()

    fun addNode(nodeElement: String) {
        val from = nodeElement.substringBefore(' ')
        val left = nodeElement.substringAfter('(').substringBefore(',')
        val right = nodeElement.substringAfter(", ").substringBefore(')')
        nodes[from] = Node(from, left, right)
    }

    /**
     * Counts the number of steps from `from` to `destination`.
     */
    fun traverse(from: String, destination: String): Int {
        var steps = 0
        var currentNode = nodes[from]
        while (currentNode != null && currentNode.name != destination) {
            for (dir in instructions) {
                ++steps
                currentNode =
                    if (dir == 'L') nodes[currentNode?.left] else nodes[currentNode?.right
                    ]
            }
        }
        return steps
    }

    private fun lcm(a: Long, b: Long): Long {
        var gcd = 1
        var i = 1
        while (i <= a && i <= b) {
            if (a % i == 0.toLong() && b % i == 0.toLong()) {
                gcd = i
            }
            ++i
        }
        return a * b / gcd
    }

    private fun lcm(input: List<Long>): Long =
        input.reduce { acc, i -> lcm(acc, i) }

    fun parallelTraverse(): Long {
        var currentNodes = nodes.values.filter { it.isStart }
        var stepsPerNode: List<Long> = List(currentNodes.size) { 0 }
        var steps: Long = 0
        while (stepsPerNode.any { it == 0.toLong() }) {
            for (dir in instructions) {
                ++steps
                currentNodes =
                    currentNodes.map { if (dir == 'L') nodes[it.left]!! else nodes[it.right]!! }
                stepsPerNode =
                    currentNodes.mapIndexed { index, node ->
                        if (stepsPerNode[index] == 0.toLong() && node.isEnd) steps else stepsPerNode[index]
                    }
            }
        }
        return lcm(stepsPerNode)
    }
}

fun buildTree(input: List<String>): Tree {
    val tree = Tree(input[0])
    for (line in input.stream().skip(2)) {
        tree.addNode(line)
    }
    return tree
}

fun main() {
    fun partOne(input: List<String>): Int {
        return buildTree(input).traverse("AAA", "ZZZ")
    }

    fun partTwo(input: List<String>): Long {
        return buildTree(input).parallelTraverse()
    }

    val lines = readInput("Day8Input")
    println("Part 1: ${partOne(lines)}")
    println("Part 2: ${partTwo(lines)}")
}