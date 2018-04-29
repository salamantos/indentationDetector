import java.io.File
import java.io.InputStream

fun getIndentation(str: String): Int {
    var count = 0
    val indentSymbol = ' '
    for (symbol in str) {
        if (symbol == indentSymbol) {
            count++
        } else {
            break
        }
    }
    return count
}

fun main(args: Array<String>) {
    val fileName: String? = readLine()
    val inputStream: InputStream = File(fileName).inputStream()
    // Используем массив вместо map, поскольку размер отступа мал, в то время как время доступа к элементу массива важнее
    val indentsCount = Array(100, { _ -> 0 })  // arr[i]=count: отступ размером i встречается count раз
    var previousIndent = 0
    // val lineList = mutableListOf<String>()
    inputStream.bufferedReader().useLines { lines ->
        lines.forEach {
            //lineList.add(it)
            val currentIndent = getIndentation(it)
            val diff = Math.abs(previousIndent - currentIndent)
            println("spacing: $currentIndent")
            if (diff > 0) {
                indentsCount[diff] += 1
            }
            previousIndent = currentIndent
        }
    }

    var maxCount = 0
    var indent = 0
    for (i in 1 until indentsCount.size) {
        println("Size $i: count " + indentsCount[i])
        if (indentsCount[i] > maxCount) {
            maxCount = indentsCount[i]
            indent = i
        }
    }
    println(indent)

    //lineList.forEach { println(">  $it") }
}