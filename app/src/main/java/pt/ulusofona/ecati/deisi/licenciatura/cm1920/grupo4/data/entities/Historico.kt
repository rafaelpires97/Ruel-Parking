package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.constants.DataBaseConstants


@Entity(tableName = DataBaseConstants.Historico.TABLE_NAME)
class Historico(
    @PrimaryKey(autoGenerate = true)
    val idHistorico: Int,
    val parqueHistoricoName: String,
    //val parqueHistoricoTipo: String,
    //val veiculoHistoricoNome: String,
    val veiculoHistoricoMatricula: String,
    val precoHora: Double,
    val qtdHoras: Int,
    val dia: String,
    val image_parque: String
) {
}