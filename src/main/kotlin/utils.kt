import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Read the file at `inputs/$name`
 */
fun readInput(name: String) = Path("inputs/$name").readLines()