import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Read the file at `inputs/$name`.
 *
 * @return A [List<String>] for each entry is a line.
 */
fun readInput(name: String) = Path("inputs/$name").readLines()

/**
 * Return all coordinates adjacent to a point that are inbounds.
 *
 * @param x The x coordinate to check.
 * @param y The y coordinate to check.
 * @param maxX The highest point `x` can be.
 * @param maxY The highest point `y` can be.
 * @param includeDiagonals Whether to include diagonally adjacent coords.
 *
 * @return All the valid x - y coordinates.
 */
fun getNeighbors(
    x: Int, y: Int, maxX: Int, maxY: Int, includeDiagonals: Boolean = true
): Set<Pair<Int, Int>> {
    val directions = listOf(-1, 0, 1)
    return directions.flatMap { dx ->
        directions.map { dy -> x + dx to y + dy }
    }.filter { (cX, cY) ->
        cX in 0..maxX && cY in 0..maxY && !(cX == x && cY == y)
                && (includeDiagonals || cX == x || cY == y)
    }.toSet()
}