fun main() {
    fun part1(input: List<String>): Int {
        val sum = input.sumOf { line ->
            val digits = line.filter { it.isDigit() }
            val number = digits.first().digitToInt() * 10 + digits.last().digitToInt()
            number
        }
        return sum
    }

    val digits = listOf(
        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
    )

    fun part2(input: List<String>): Int {
        val sum = input.sumOf {line ->
            val firstDigitIndex = line.indexOfFirst { it.isDigit() }
            val lastDigitIndex = line.indexOfLast { it.isDigit() }
            val firstWriteOutDigitIndex = line.indexOfAny(digits)
            val lastWriteOutDigitIndex = line.lastIndexOfAny(digits)

            val first = if ((firstDigitIndex < firstWriteOutDigitIndex && firstDigitIndex != -1) || firstWriteOutDigitIndex == -1){
                line[firstDigitIndex].digitToInt()
            }else{
                digits.indexOfFirst { it.startsWith(line.substring(firstWriteOutDigitIndex, firstWriteOutDigitIndex + 2)) } + 1
            }

            val last = if (lastDigitIndex > lastWriteOutDigitIndex || lastWriteOutDigitIndex == -1){
                line[lastDigitIndex].digitToInt()
            }else{
                digits.indexOfFirst { it.startsWith(line.substring(lastWriteOutDigitIndex, lastWriteOutDigitIndex + 2)) } + 1
            }

            val number = first * 10 + last
            number
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
