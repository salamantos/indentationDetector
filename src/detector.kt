import java.io.File
import java.io.InputStream

fun getIndentation(str: String): Pair<Char, Int> {
    var count = 0
    val indentSymbol = ' '
    for (symbol in str) {
        if (symbol == indentSymbol) {
            count++
        } else {
            break
        }
    }
    return Pair(indentSymbol, count)
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Missing required parameter: filepath")
        return
    }
    val fileName = args[0]
    val inputStream: InputStream = File(fileName).inputStream()
    val indentsTable = hashMapOf('t' to hashMapOf(0 to 0)) //= Array(100, { _ -> 0 })  // arr[i]=count: отступ размером i встречается count раз
    var previousIndent = 0
    inputStream.bufferedReader().useLines { lines ->
        lines.forEach {
            val (indentSymbol, currentIndent) = getIndentation(it)
            val diff = Math.abs(previousIndent - currentIndent)
            // println("spacing: $currentIndent")
            if (diff > 0) {
                // indentsTable[diff] += 1
                if (indentsTable[indentSymbol]!![diff] != null) {
                    val diffIndent = indentsTable[indentSymbol]!![diff]
                    indentsTable[indentSymbol]!![diff] = diffIndent!! + 1
                } else {
                    indentsTable[indentSymbol]!![diff] = 1
                }
            }
            previousIndent = currentIndent
        }
    }

    var maxCount = 0
    var indent = 0
    for (symbol in indentsTable.keys) {
        for (key in indentsTable[symbol]!!.keys) {
            println("Symb '$symbol', size $key: count " + indentsTable[symbol]!![key])
            if (indentsTable[symbol]!![key]!! > maxCount) {
                maxCount = indentsTable[symbol]!![key]!!
                indent = key
            }
        }
    }
    println(indent)
}