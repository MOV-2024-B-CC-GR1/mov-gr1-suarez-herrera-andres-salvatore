import java.util.Scanner
import java.time.LocalDate

val scanner = Scanner(System.`in`) // Inicialización del scanner

fun main() {
    val cantantes = leerCantantes().toMutableList()
    val canciones = leerCanciones().toMutableList()

    while (true) {
        println("\n-------------- MENÚ PRINCIPAL -------------")
        println("1. Crear cantante")
        println("2. Crear canción")
        println("3. Mostrar cantantes")
        println("4. Mostrar canciones")
        println("5. Editar/Actualizar cantante")
        println("6. Editar/Actualizar canción")
        println("7. Eliminar cantante")
        println("8. Eliminar canción")
        println("9. Salir")
        print("Selecciona una opción: ")

        when (scanner.nextInt()) {
            1 ->{
            scanner.nextLine()
            print("ID del cantante: ")
            val id = scanner.nextInt()
            scanner.nextLine()
            print("Nombre del cantante: ")
            val nombre = scanner.nextLine()
            print("Género musical: ")
            val genero = scanner.nextLine()
            print("Fecha de nacimiento (yyyy-mm-dd): ")
            val fechaNacimiento = LocalDate.parse(scanner.nextLine())
            print("Número de canciones en su carrera: ")
            val numeroCanciones = scanner.nextInt()
            print("Rating promedio (decimal): ")
            val ratingPromedio = scanner.nextDouble()

            val cantante = Cantante(id, nombre, genero, fechaNacimiento, numeroCanciones, ratingPromedio)
            guardarCantante(cantante)

            cantantes.clear()
            cantantes.addAll(leerCantantes())

            println("Cantante creado con éxito.")
            }

            2 -> {
                scanner.nextLine() // Consumir nueva línea pendiente
                print("ID de la canción: ")
                val id = scanner.nextInt()
                scanner.nextLine()
                print("ID del cantante asociado: ")
                val cantanteId = scanner.nextInt()
                scanner.nextLine()
                print("Título de la canción: ")
                val titulo = scanner.nextLine()
                print("Duración (en minutos): ")
                val duracion = scanner.nextDouble()
                print("¿Es un hit? (sí/no): ")
                val esHit = scanner.next().lowercase() == "sí"

                cantantes.clear()
                cantantes.addAll(leerCantantes())

                val cantante = cantantes.find { it.id == cantanteId }

                if (cantante != null) {
                    val cancion = Cancion(id, cantanteId, titulo, duracion, esHit)
                    guardarCancion(cancion)
                    println("Canción creada con éxito para el cantante: ${cantante.nombre}")
                } else {
                    println("No existe un cantante con ID $cantanteId.")
                }
            }

            3 -> {
                println("\n*** Lista de Cantantes ***")
                cantantes.clear()
                cantantes.addAll(leerCantantes())
                canciones.clear()
                canciones.addAll(leerCanciones())

                if (cantantes.isEmpty()) {
                    println("No hay cantantes registrados.")
                } else {
                    cantantes.forEach { cantante ->
                        // Filtrar las canciones asociadas al cantante actual
                        val cancionesDelCantante = canciones.filter { it.cantanteId == cantante.id }

                        // Imprimir la información del cantante
                        println("Cantante(id=${cantante.id}, nombre=${cantante.nombre}, generoMusical=${cantante.generoMusical}, fechaNacimiento=${cantante.fechaNacimiento}, numeroCancionesEnCarrera=${cantante.numeroCanciones}, ratingPromedio=${cantante.ratingPromedio})")

                        // Mostrar las canciones asociadas
                        if (cancionesDelCantante.isEmpty()) {
                            println("  No tiene canciones registradas en el sistema.")
                        } else {
                            println("  Canciones registradas:")
                            cancionesDelCantante.forEach { cancion ->
                                println("    - ${cancion.titulo} (Duración: ${cancion.duracion}, Hit: ${cancion.esHit})")
                            }
                        }
                    }
                }
            }

            4 -> {
                println("\n*** Lista de Canciones ***")

                // Asegurarnos de leer la lista más actualizada de canciones
                canciones.clear()
                canciones.addAll(leerCanciones())

                if (canciones.isEmpty()) {
                    println("No hay canciones registradas.")
                } else {
                    // Asegurarnos de leer también la lista más actualizada de cantantes
                    cantantes.clear()
                    cantantes.addAll(leerCantantes())

                    canciones.forEach { cancion ->
                        val cantante = cantantes.find { it.id == cancion.cantanteId }
                        val nombreCantante = cantante?.nombre ?: "Desconocido"
                        println("Cancion(id=${cancion.id}, cantanteId=${cancion.cantanteId}, cantanteNombre=$nombreCantante, titulo=${cancion.titulo}, duracion=${cancion.duracion}, esHit=${cancion.esHit})")
                    }
                }
            }

            5 -> {
                println("\n*** Actualizar Cantante ***")
                scanner.nextLine() // Consumir nueva línea pendiente
                print("ID del cantante a actualizar: ")
                val id = scanner.nextInt()
                scanner.nextLine()
                // Buscar al cantante por ID
                val cantante = cantantes.find { it.id == id }
                if (cantante != null) {
                    println("Información actual del cantante: $cantante")
                    print("Nuevo nombre (dejar en blanco para no cambiar): ")
                    val nuevoNombre = scanner.nextLine()
                    print("Nuevo género musical (dejar en blanco para no cambiar): ")
                    val nuevoGenero = scanner.nextLine()
                    print("Nueva fecha de nacimiento (yyyy-mm-dd, dejar en blanco para no cambiar): ")
                    val nuevaFecha = scanner.nextLine()
                    print("Nuevo número de canciones en su carrera (0 para no cambiar): ")
                    val nuevoNumeroCanciones = scanner.nextInt()
                    print("Nuevo rating promedio (0 para no cambiar): ")
                    val nuevoRating = scanner.nextDouble()

                    // Actualizar los campos si el usuario ingresó un valor
                    if (nuevoNombre.isNotBlank()) cantante.nombre = nuevoNombre
                    if (nuevoGenero.isNotBlank()) cantante.generoMusical = nuevoGenero
                    if (nuevaFecha.isNotBlank()) cantante.fechaNacimiento = LocalDate.parse(nuevaFecha)
                    if (nuevoNumeroCanciones > 0) cantante.numeroCanciones = nuevoNumeroCanciones
                    if (nuevoRating > 0) cantante.ratingPromedio = nuevoRating

                    // Guardar los cambios en el archivo
                    actualizarCantantes(cantantes)
                    println("Cantante actualizado con éxito.")
                } else {
                    println("No se encontró un cantante con ID $id.")
                }
            }
            6 -> {
                println("\n*** Actualizar Canción ***")
                scanner.nextLine() // Consumir nueva línea pendiente
                print("ID de la canción a actualizar: ")
                val id = scanner.nextInt()
                scanner.nextLine()

                // Buscar la canción por ID
                val cancion = canciones.find { it.id == id }
                if (cancion != null) {
                    println("Información actual de la canción: $cancion")
                    print("Nuevo título (dejar en blanco para no cambiar): ")
                    val nuevoTitulo = scanner.nextLine()
                    print("Nueva duración en minutos (0 para no cambiar): ")
                    val nuevaDuracion = scanner.nextDouble()
                    scanner.nextLine() // Consumir nueva línea pendiente
                    print("¿Es un hit? (sí/no, dejar vacío para no cambiar): ")
                    val nuevoHitInput = scanner.nextLine()

                    // Actualizar los campos si el usuario ingresó un valor
                    if (nuevoTitulo.isNotBlank()) cancion.titulo = nuevoTitulo
                    if (nuevaDuracion > 0) cancion.duracion = nuevaDuracion
                    if (nuevoHitInput.isNotBlank()) cancion.esHit = nuevoHitInput.lowercase() == "sí"

                    // Guardar los cambios en el archivo
                    actualizarCanciones(canciones)
                    println("Canción actualizada con éxito.")
                } else {
                    println("No se encontró una canción con ID $id.")
                }
            }

            7 -> {
                print("ID del cantante a eliminar: ")
                val id = scanner.nextInt()
                eliminarCantante(id)
            }
            8 -> {
                print("ID de la canción a eliminar: ")
                val id = scanner.nextInt()
                eliminarCancion(id)
            }
            9 -> {
                println("Saliendo del programa...")
                break
            }
            else -> println("Opción inválida.")
        }
    }
}