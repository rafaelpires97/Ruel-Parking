package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.repository.local.giraStation

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.GiraModel


@Dao
interface GiraStationDAO {

    @Insert
    fun save(parque: GiraModel): Long

    @Update
    fun update(parque: GiraModel): Int

    @Insert(onConflict = REPLACE)
    fun insertAll(parque: List<GiraModel>)

    @Delete
    fun delete(parque: GiraModel)


    @Query("SELECT * FROM GiraList")
    fun getList(): List<GiraModel>

}
