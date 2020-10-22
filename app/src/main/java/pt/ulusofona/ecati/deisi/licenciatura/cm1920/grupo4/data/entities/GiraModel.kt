package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "GiraList")
class GiraModel (
    @PrimaryKey()
    var  idx: String,
    var nome: String,
    var longitude: Double,
   var  latitude : Double,
    var numBike: Int,
    var numDocas: Int,
    var ultimoUpdate: String

)
{
}