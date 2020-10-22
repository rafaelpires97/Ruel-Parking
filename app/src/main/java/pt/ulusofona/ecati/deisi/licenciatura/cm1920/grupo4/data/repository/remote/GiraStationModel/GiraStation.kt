package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo3.data.remote.responses.GiraStation


data class GiraStation (

    val type : String,
    val totalFeatures : Int,
    val features : List<Features>,
    val crs : Crs,
    val bbox : List<Double>
)