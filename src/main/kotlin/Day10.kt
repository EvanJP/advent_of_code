import kotlin.math.max

class PipeMaze(grid: List<String>) {

    /**
     * The [Coord] that contains the `S` [Tile].
     */
    private lateinit var startCoord: Coord
    private val coords: List<List<Coord>> = parseCoords(grid)

    enum class Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    interface TileInterface {
        fun validEntrance(dir: Direction): Boolean
        fun validExit(dir: Direction): Boolean = validEntrance(dir)
    }

    enum class Tile : TileInterface {
        VERTICAL_PIPE {
            override fun validEntrance(dir: Direction): Boolean =
                (dir == Direction.NORTH) || (dir == Direction.SOUTH)
        },
        HORIZONTAL_PIPE {
            override fun validEntrance(dir: Direction): Boolean =
                (dir == Direction.EAST) || (dir == Direction.WEST)
        },
        NE_BEND {
            override fun validEntrance(dir: Direction): Boolean =
                (dir == Direction.SOUTH) || (dir == Direction.WEST)

            override fun validExit(dir: Direction): Boolean =
                (dir == Direction.NORTH) || (dir == Direction.EAST)
        },
        NW_BEND {
            override fun validEntrance(dir: Direction): Boolean =
                (dir == Direction.SOUTH) || (dir == Direction.EAST)

            override fun validExit(dir: Direction): Boolean =
                (dir == Direction.NORTH) || (dir == Direction.WEST)
        },
        SE_BEND {
            override fun validEntrance(dir: Direction): Boolean =
                (dir == Direction.NORTH) || (dir == Direction.EAST)

            override fun validExit(dir: Direction): Boolean =
                (dir == Direction.SOUTH) || (dir == Direction.WEST)
        },
        SW_BEND {
            override fun validEntrance(dir: Direction): Boolean =
                (dir == Direction.NORTH) || (dir == Direction.WEST)

            override fun validExit(dir: Direction): Boolean =
                (dir == Direction.SOUTH) || (dir == Direction.EAST)
        },
        GROUND {
            override fun validEntrance(dir: Direction): Boolean = false
        },
        START {
            override fun validEntrance(dir: Direction): Boolean = true
        }
    }

    private fun toTile(ch: Char): Tile = when (ch) {
        '|' -> Tile.VERTICAL_PIPE
        '-' -> Tile.HORIZONTAL_PIPE
        'L' -> Tile.NE_BEND
        'J' -> Tile.NW_BEND
        '7' -> Tile.SE_BEND
        'F' -> Tile.SW_BEND
        '.' -> Tile.GROUND
        'S' -> Tile.START
        else -> throw IllegalStateException("Unknown Shape: $ch")
    }

    private data class Coord(
        val x: Int, val y: Int, val tile: Tile, var distFromStart: Int
    ) {
        fun directionTo(coord: Coord): Direction {
            return when {
                coord.y < y -> Direction.NORTH
                coord.y > y -> Direction.SOUTH
                coord.x < x -> Direction.WEST
                coord.x > x -> Direction.EAST
                else -> throw IllegalStateException(
                    "Invalid direction from $this to $coord"
                )
            }
        }
    }

    private fun parseCoords(input: List<String>): List<List<Coord>> =
        input.indices.map { y ->
            input[y].indices.map { x ->
                val tile = toTile(input[y][x])
                if (tile == Tile.START) {
                    startCoord = Coord(x, y, tile, 0)
                }
                Coord(x, y, tile, 0)
            }
        }

    /**
     * For every coordinate, update its `distFromStart` property.
     *
     * Uses BFS to parse the graph.
     *
     *
     * @return The greatest distance of all coords from [startCoord].
     */
    fun computeDistances(): Int {
        val queue = ArrayDeque<Coord>()
        val visited = mutableSetOf<Coord>()
        var maxDist = 0

        queue.add(startCoord)
        while (queue.isNotEmpty()) {
            val coord = queue.removeFirst()
            visited.add(coord)
            maxDist = max(maxDist, coord.distFromStart)
            getNeighbors(
                coord.x, coord.y, coords[0].size - 1, coords.size - 1,
                includeDiagonals = false
            ).map { (x, y) -> coords[y][x] }
                .filter { !visited.contains(it) }
                .filter { to ->
                    to.tile.validEntrance(coord.directionTo(to)) &&
                            coord.tile.validExit(coord.directionTo(to))
                }
                .forEach {
                    it.distFromStart = coord.distFromStart + 1
                    queue.add(it)
                }
        }
        return maxDist
    }
}

fun main() {
    fun partOne(input: List<String>): Int = PipeMaze(input).computeDistances()

    val lines = readInput("Day10Input")
    println("Part 1: ${partOne(lines)}")
}