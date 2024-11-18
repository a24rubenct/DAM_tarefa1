import java.io.File

// Códigos de color de fondo
const val BG_BLACK = "\u001B[40m"
const val BG_RED = "\u001B[41m"
const val BG_GREEN = "\u001B[42m"
const val BG_YELLOW = "\u001B[43m"
const val BG_BLUE = "\u001B[44m"
const val BG_PURPLE = "\u001B[45m"
const val BG_CYAN = "\u001B[46m"
const val BG_WHITE = "\u001B[47m"

// Colores ANSI básicos
const val RESET = "\u001B[0m"
const val BLACK = "\u001B[30m"
const val RED = "\u001B[31m"
const val GREEN = "\u001B[32m"
const val YELLOW = "\u001B[33m"
const val BLUE = "\u001B[34m"
const val PURPLE = "\u001B[35m"
const val CYAN = "\u001B[36m"
const val WHITE = "\u001B[37m"
const val BOLD = "\u001B[1m"
const val UNDERLINE = "\u001B[4m"

//fun para menú inicial con algo de color
fun main() {
    while (true) {
        println("${BG_WHITE}${BLUE}${BOLD}Selecciona una de las siguientes opciones${RESET}")
        println("${BG_GREEN}${BLACK} 1 ${RESET} ${GREEN}Jugar${RESET}")
        println("${BG_YELLOW}${BLACK} 2 ${RESET} ${YELLOW}Consultar último juego${RESET}")
        println("${BG_RED}${BLACK} 3 ${RESET} ${RED}Salir${RESET}")
        print("Opción: ")
//suponemos que se introduce una opción válida, no se contempla error
        val teclado = readln().toInt()
        when (teclado) {
            1 -> juego()
            2 -> juegoAnterior()
            3 -> {
                println("Hasta pronto")
                break
            }
        }
    }
}

//fun para opción 1 - juego con número de 4 cifras del 1 al 8 sin repetir, con 8 intentos
fun juego() {
    val numSecreto = generaNumSecreto()
    var intentos = 8
    val jugadas = mutableListOf<String>()
    jugadas.add("Número secreto: $numSecreto")
    print("Teclea un número de cuatro cifras (del 1 al 8, sin repetir). Tienes $intentos intentos: ")
    //suponemos que se introduce un número válido, no se contempla error
    while (intentos > 0) {
        val intento = readln()
        if (intento == numSecreto) {
            println("Correcto, has adivinado el número secreto")
            jugadas.add("Intento ${9 - intentos}: $intento, Correcto")
            guardaJugada(jugadas)
            return
        }
        //se indica en fondo verde las cifras en posición y en amarillo los aciertos totales y se añade al archivo
        val (correctos, enPosicion) = verificarIntento(intento, numSecreto)
        println("$intento -- ${BG_GREEN}${BLACK} $enPosicion ${RESET}   ${BG_YELLOW}${BLACK} $correctos ${RESET}")
        jugadas.add("Intento ${9 - intentos}: $intento, Aciertos: $enPosicion, Coincidencias: $correctos")
        intentos--
        println("Te quedan $intentos intentos")
    }
    println("No has conseguido adivinar el número secreto $numSecreto en 8 intentos")
    guardaJugada(jugadas)
}

//fun para generar número secreto
fun generaNumSecreto(): String {
    val cifras = (1..8).shuffled().take(4)
    return cifras.joinToString("")
}

//fun para verificar las cifras en cada intento
fun verificarIntento(intento: String, numSecreto: String): Pair<Int, Int> {
    var correctos = 0
    var enPosicion = 0
    for (i in intento.indices) {
        if (intento[i] == numSecreto[i]) {
            enPosicion++
        }
        if (numSecreto.contains(intento[i])) {
            correctos++
        }
    }
    return Pair(correctos, enPosicion)
}

//fun para guardar última jugada en archivo
fun guardaJugada(jugadas: List<String>) {
    val archivo = File("jugadas.txt")
    archivo.writeText(jugadas.joinToString("\n") + "\n")
}

//fun para mostrar última jugada desde archivo, debe existir previamente
fun juegoAnterior() {
    val archivo = File("jugadas.txt")
    archivo.forEachLine { println(it) }
}
