import java.time.LocalDate

data class Cantante(
    var id: Int,
    var nombre: String,
    var generoMusical: String,
    var fechaNacimiento: LocalDate,
    var numeroCanciones: Int,
    var ratingPromedio: Double
)

data class Cancion(
    var id: Int,
    var cantanteId: Int,
    var titulo: String,
    var duracion: Double,
    var esHit: Boolean
)
