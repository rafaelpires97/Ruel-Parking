package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.repository.local.historico

import androidx.room.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Historico

@Dao
interface HistoricoDAO {

    @Insert
    fun save(parque: Historico): Long

    @Update
    fun update(parque: Historico): Int

    @Insert
    fun insertAll(parque: List<Historico>)

    @Delete
    fun delete(parque: Historico)

    @Query("SELECT * FROM HistoricoList WHERE idHistorico = :id")
    fun load(id: Int): Historico


    @Query("SELECT * FROM HistoricoList")
    fun getList(): List<Historico>


    /*
    @Query("SELECT * FROM Parque WHERE blabla = 1")
    fun getPresent(): List<ParqueModel>

    @Query("SELECT * FROM Parque WHERE blabla = 0")
    fun getAbsent(): List<ParqueModel>
*/
}
