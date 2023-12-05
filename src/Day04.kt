import kotlin.math.pow

fun main() {
    fun countWinners(line: String): Int {
        val (winning_numbers, your_numbers) = line.substringAfter(": ").split(" | ").map { numbers ->
            numbers.windowed(2, 3).map { it.trim().toInt() }.toSet()
        }
        val winners = your_numbers.count { winning_numbers.contains(it) }
        return winners
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val winners = countWinners(line)
            2.0.pow(winners - 1).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val cardCounts = IntArray(input.size) { 1 }
        input.forEachIndexed { index, game ->
            repeat(countWinners(game)) { i ->
                cardCounts[index + 1 + i] += cardCounts[index]
            }
        }
        return cardCounts.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
