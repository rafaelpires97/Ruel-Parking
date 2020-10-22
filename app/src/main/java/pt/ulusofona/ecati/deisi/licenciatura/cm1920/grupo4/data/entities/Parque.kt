package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.constants.DataBaseConstants


@Entity(tableName = DataBaseConstants.Parque.TABLE_NAME)
class Parque(
    @PrimaryKey var id_parque: String,
    var nome: String,
    var activo: Int,
    var id_entidade: Int,
    var capacidade_max: Int,
    var ocupacao: Int,
    var data_ocupacao: String,
    var latitude: Double,
    var longitude: Double,
    var tipo: String,
    var distancia: Double,
    var percentagem: Int
) {


}