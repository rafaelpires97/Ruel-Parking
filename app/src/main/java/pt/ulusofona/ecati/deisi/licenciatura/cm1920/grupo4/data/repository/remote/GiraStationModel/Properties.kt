package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo3.data.remote.responses.GiraStation


data class Properties (

	val id_expl : String,
	val id_planeamento : String,
	val desig_comercial : String,
	val tipo_servico_niveis : String,
	val num_bicicletas : Int,
	val num_docas : Int,
	val racio : Double,
	val estado : String,
	val update_date : String,
	val bbox : List<Double>
)