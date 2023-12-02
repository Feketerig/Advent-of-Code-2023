import kotlin.math.max

data class Bag(
    val red: Int,
    val green: Int,
    val blue: Int
){
    fun hasMoreThan(other: Bag): Boolean {
        return red >= other.red && green >= other.green && blue >= other.blue
    }

    fun maxOf(other: Bag): Bag {
        return Bag(
            red = max(red, other.red),
            green = max(green, other.green),
            blue = max(blue, other.blue)
        )
    }

    companion object {
        fun readBag(bags: String): Bag {
            var bag = Bag(0, 0, 0)
            bags.split(",").map {
                if (it.substringAfterLast(" ") == "blue") {
                    bag = bag.copy(blue = it.drop(1).substringBefore(" ").toInt())
                } else if (it.substringAfterLast(" ") == "red") {
                    bag = bag.copy(red = it.drop(1).substringBefore(" ").toInt())
                } else if (it.substringAfterLast(" ") == "green") {
                    bag = bag.copy(green = it.drop(1).substringBefore(" ").toInt())
                }
            }
            return bag
        }
    }
}

fun main() {



    fun part1(input: List<String>): Int {
        val bigBag = Bag(12,13,14)
        val sum = input.sumOf { line ->
            val id = line.substringBefore(":").removePrefix("Game ").toInt()
            val bags = line.substringAfter(":").split(";").map{ bags ->
                Bag.readBag(bags)
            }
            val all = bags.all { bigBag.hasMoreThan(it) }
            if (all) id else 0
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val sum = input.sumOf { line ->
            val bags = line.substringAfter(":").split(";").map{ bags ->
                Bag.readBag(bags)
            }
            var max = Bag(0,0,0)
            bags.forEach {
                max = it.maxOf(max)
            }
            val power = max.red * max.blue * max.green
            power
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}