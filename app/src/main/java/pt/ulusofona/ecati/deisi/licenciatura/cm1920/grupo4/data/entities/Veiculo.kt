package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.constants.DataBaseConstants

@Entity(tableName = DataBaseConstants.Veiculo.TABLE_NAME)
class Veiculo(


    @ColumnInfo(name = DataBaseConstants.Veiculo.COLUMNS.MARCA)
    var marca: String,

    @ColumnInfo(name = DataBaseConstants.Veiculo.COLUMNS.MODELO)
    var modelo: String,

    @PrimaryKey()
    @ColumnInfo(name = DataBaseConstants.Veiculo.COLUMNS.MATRICULA)
    var matricula: String,

    @ColumnInfo(name = DataBaseConstants.Veiculo.COLUMNS.IMAGE_CARRO)
    var image_carro: String,

    @ColumnInfo(name = DataBaseConstants.Veiculo.COLUMNS.ANO_MATRICULA)
    var ano_matricula: String
) {




}