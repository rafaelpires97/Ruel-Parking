package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.veiculo

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Veiculo

@Dao
interface VeiculoDAO {

    @Insert(onConflict = REPLACE)
    fun save(parque: Veiculo): Long

    @Update(onConflict = REPLACE)
    fun update(parque: Veiculo): Int

    @Insert
    fun insertAll(parque: List<Veiculo>)

    @Delete
    fun delete(parque: Veiculo)
/*
    @Query("SELECT * FROM VeiculoList WHERE id = :id")
    fun load(id: Int): Veiculo
*/

    @Query("SELECT * FROM VeiculoList")
    fun getList(): List<Veiculo>


    /*
    @Query("SELECT * FROM Parque WHERE blabla = 1")
    fun getPresent(): List<ParqueModel>

    @Query("SELECT * FROM Parque WHERE blabla = 0")
    fun getAbsent(): List<ParqueModel>
*/
}
