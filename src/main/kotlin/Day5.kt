import kotlin.properties.Delegates
import kotlin.collections.HashMap
import kotlin.math.max
import kotlin.math.min

data class SourceDestinationRangeMap(
    val sourceName: String, val destinationName: String
) {
    data class SourceDestinationRange(
        val source: Long, val destination: Long, val length: Long
    ) {
        fun contains(key: Long): Boolean {
            return key >= source && key < source + length
        }

        fun getDestination(key: Long): Long? {
            if (!contains(key)) {
                return null
            }
            return destination + key - source
        }
    }

    var ranges = mutableListOf<SourceDestinationRange>()
    private val rangesSorted: List<SourceDestinationRange> by lazy {
        ranges.sortedBy { it.source }
    }

    fun addRange(source: Long, destination: Long, length: Long) {
        ranges.add(SourceDestinationRange(source, destination, length))
    }

    fun getDestination(source: Long): Long {
        val searchSource = rangesSorted.binarySearchBy(source) { it.source }
        // Found an exact match
        if (searchSource >= 0) {
            return rangesSorted[searchSource].destination
        }

        // Calculate destination of source. If null, then the source isn't
        // mapped, and it is a 1:1 destination.
        return rangesSorted.elementAtOrNull(-(searchSource + 2))
            ?.getDestination(source) ?: source
    }
}

class Almanac(val seedsAreRanges: Boolean = false) {
    private var seeds: List<Long> by Delegates.notNull()
    private var seedsRanges: List<LongRange> by Delegates.notNull()
    private var currentRangeMap: SourceDestinationRangeMap by Delegates.notNull()

    private var rangeMaps = HashMap<String, SourceDestinationRangeMap>()

    private fun parseSeeds(line: String) {
        if (seedsAreRanges) {
            seedsRanges =
                line.substringAfter(":").trim().split(" ").chunked(2) {
                    val (start, length) = it.map { s -> s.toLong() }
                    LongRange(start, start + length - 1)
                }
        } else {
            seeds =
                line.substringAfter(":").trim().split(" ").map { it.toLong() }
        }
    }

    private fun createNewRangeMap(line: String) {
        val (source, _, destination) = line.substringBefore(" ").split("-")
        currentRangeMap = SourceDestinationRangeMap(source, destination)
        rangeMaps[source] = currentRangeMap
    }

    private fun addRange(line: String) {
        val (destination, source, length) = line.split(" ").map { it.toLong() }
        currentRangeMap.addRange(source, destination, length)
    }

    fun parse(line: String) {
        if (line.isBlank()) {
            return
        }
        when {
            line.startsWith("seeds:") -> parseSeeds(line)
            line.endsWith("map:") -> createNewRangeMap(line)
            else -> addRange(line)
        }
    }

    private fun getLocation(seed: Long): Long {
        var rangeMap = rangeMaps["seed"]
        var location = seed
        if (rangeMap != null) {
            while (rangeMap!!.destinationName != "location") {
                location = rangeMap.getDestination(location)
                rangeMap = rangeMaps[rangeMap.destinationName]
            }
        } else {
            throw Exception("Could not find seed rangeMap.")
        }
        return rangeMap.getDestination(location)
    }

    private fun getLocationOfRanges(seedRange: LongRange): Set<Long> =
        rangeMaps["seed"]!!.ranges.filter {
            max(it.source, seedRange.first) <= min(
                it.source + it.length, seedRange.last
            )
        }.map { getLocation(it.source) }.toSet()

    fun lowestLocation(): Long {
        if (seedsAreRanges) {
            return seedsRanges.map { getLocationOfRanges(it) }.flatten().min()
        }
        return seeds.minOf { getLocation(it) }
    }
}

fun main() {
    fun part1(lines: List<String>): Long {
        with(Almanac()) {
            lines.forEach { parse(it) }
            return lowestLocation()
        }
    }

    fun part2(lines: List<String>): Long {
        with(Almanac(seedsAreRanges = true)) {
            lines.forEach { parse(it) }
            return lowestLocation()
        }
    }

    val lines = readInput("Day5Input")
    println("Part 1: ${part1(lines)}")
    println("Part 2: ${part2(lines)}")
}
