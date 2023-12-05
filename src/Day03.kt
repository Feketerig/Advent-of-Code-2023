import kotlin.math.abs

fun main() {

    data class Position(val x: Int, val y: Int)

    fun Position.neighbours(other: Position): Boolean{
        return abs(x- other.x) <= 1 && abs(y - other.y) <= 1
    }

    open class Entity(val positions: List<Position>)

    fun Entity.neighbours(other: Entity): Boolean{
        return positions.any { pos -> other.positions.any { pos.neighbours(it) } }
    }

    class Number(x: Int, y: Int, val value: String)
        :Entity(List(value.length) { i -> Position(x + i, y) })

    fun Number.getIntValue(): Int {
        return value.toInt()
    }

    class Symbol(x: Int, y: Int, val value: Char)
        :Entity(listOf(Position(x, y)))

    fun parse(input: List<String>): Pair<List<Symbol>, List<Number>>{
        val symbols = mutableListOf<Symbol>()
        val numbers = mutableListOf<Number>()

        input.forEachIndexed { y, line ->
            var currentNumber = ""
            line.forEachIndexed { x, char ->
                when(char){
                    '.' -> Unit
                    in '0'..'9' -> currentNumber += char
                    else -> symbols += Symbol(x, y, char)
                }
                if (char !in '0'..'9' && currentNumber.isNotEmpty()){
                    numbers += Number(x - currentNumber.length, y, currentNumber)
                    currentNumber = ""
                }
            }
            if (currentNumber.isNotEmpty()){
                numbers += (Number(line.length - currentNumber.length, y, currentNumber))
            }
        }
        return Pair(symbols, numbers)
    }

    fun part1(input: List<String>): Int {
        val (symbols, numbers) = parse(input)

        return numbers
            .filter { number -> symbols.any { symbol -> symbol.neighbours(number) } }
            .sumOf { it.getIntValue() }
    }

    fun part2(input: List<String>): Int {
        val (symbols, numbers) = parse(input)

        return symbols.filter { symbol -> symbol.value == '*' }.sumOf { symbol ->
            val neighbours = numbers.filter { it.neighbours(symbol) }
            if (neighbours.size == 2) {
                val (first, second) = neighbours
                first.getIntValue() * second.getIntValue()
            } else {
                0
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
