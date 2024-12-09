import java.io.File
import java.time.LocalDate

val cantanteFile = File("Cantante.txt")
val cancionesFile = File("Canciones.txt")

// Funciones CRUD para Cantantes
fun guardarCantante(cantante: Cantante) {
    val cantantes = leerCantantes().toMutableList()
    cantantes.add(cantante)
    cantanteFile.writeText(cantantes.joinToString("\n") {
        "${it.id},${it.nombre},${it.generoMusical},${it.fechaNacimiento},${it.numeroCanciones},${it.ratingPromedio}"
    })
}

fun leerCantantes(): List<Cantante> {
    if (!cantanteFile.exists()) return emptyList()
    return cantanteFile.readLines().map { linea ->
        val datos = linea.split(",")
        Cantante(
            id = datos[0].toInt(),
            nombre = datos[1],
            generoMusical = datos[2],
            fechaNacimiento = LocalDate.parse(datos[3]),
            numeroCanciones = datos[4].toInt(),
            ratingPromedio = datos[5].toDouble()
        )
    }
}

fun actualizarCantantes(cantantes: List<Cantante>) {
    cantanteFile.writeText(cantantes.joinToString("\n") {
        "${it.id},${it.nombre},${it.generoMusical},${it.fechaNacimiento},${it.numeroCanciones},${it.ratingPromedio}"
    })
}

fun eliminarCantante(id: Int) {
    val cantantes = leerCantantes().toMutableList()
    val eliminado = cantantes.removeIf { it.id == id }
    if (eliminado) {
        actualizarCantantes(cantantes)
        val canciones = leerCanciones().toMutableList()
        canciones.removeIf { it.cantanteId == id }
        actualizarCanciones(canciones)
        println("Cantante eliminado con éxito.")
    } else {
        println("Cantante no encontrado.")
    }
}

// Funciones CRUD para Canciones
fun guardarCancion(cancion: Cancion) {
    val canciones = leerCanciones().toMutableList()
    canciones.add(cancion)
    cancionesFile.writeText(canciones.joinToString("\n") {
        "${it.id},${it.cantanteId},${it.titulo},${it.duracion},${it.esHit}"
    })
}

fun leerCanciones(): List<Cancion> {
    if (!cancionesFile.exists()) return emptyList()
    return cancionesFile.readLines().map { linea ->
        val datos = linea.split(",")
        Cancion(
            id = datos[0].toInt(),
            cantanteId = datos[1].toInt(),
            titulo = datos[2],
            duracion = datos[3].toDouble(),
            esHit = datos[4].toBoolean()
        )
    }
}

fun actualizarCanciones(canciones: List<Cancion>) {
    cancionesFile.writeText(canciones.joinToString("\n") {
        "${it.id},${it.cantanteId},${it.titulo},${it.duracion},${it.esHit}"
    })
}

fun eliminarCancion(id: Int) {
    val canciones = leerCanciones().toMutableList()
    val eliminado = canciones.removeIf { it.id == id }
    if (eliminado) {
        actualizarCanciones(canciones)
        println("Canción eliminada con éxito.")
    } else {
        println("Canción no encontrada.")
    }
}
