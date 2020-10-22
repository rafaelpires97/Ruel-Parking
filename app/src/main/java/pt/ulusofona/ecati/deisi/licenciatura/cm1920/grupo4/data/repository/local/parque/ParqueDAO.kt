package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Parque

@Dao
interface ParqueDAO {

    @Insert
    fun save(parque: Parque): Long

    @Insert(onConflict = REPLACE)
    fun insertAll(parques: List<Parque>)


    @Query("SELECT * FROM ParqueList")
    fun getAll(): List<Parque>

    @Update
    fun update(parque: Parque): Int

    @Delete
    fun delete(parque: Parque)

    @Query("DELETE FROM ParqueList")
    fun deleteAll()

    @Query("SELECT * FROM ParqueList WHERE id_parque = :id")
    fun load(id: String): Parque


//QUERYS SEM ORDENACAO//

    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo")
    fun getTipo(tipo: String): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE percentagem>:menor AND percentagem<:maior")
    fun getOcupacao(menor: Int, maior: Int): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE distancia>:menor AND distancia<:maior")
    fun getDistance(menor: Double, maior: Double): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND percentagem>:menor AND percentagem<:maior AND distancia>:menos AND distancia<:mais")
    fun getOcupacao_Tipo_Distance(
        tipo: String,
        menor: Int,
        maior: Int,
        menos: Double,
        mais: Double
    ): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND percentagem>:menor AND percentagem<:maior")
    fun getOcupacao_Tipo(
        tipo: String,
        menor: Int,
        maior: Int
    ): List<Parque>

    @Query("SELECT * FROM ParqueList WHERE percentagem>:menor AND percentagem<:maior AND distancia>:menos AND distancia<:mais")
    fun getOcupacao_Distance(
        menor: Int,
        maior: Int,
        menos: Double,
        mais: Double
    ): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND distancia>:menor AND distancia<:maior")
    fun getDistance_Tipo(
        tipo: String,
        menor: Double,
        maior: Double
    ): List<Parque>


    @Query("SELECT * FROM ParqueList ORDER BY distancia ASC")
    fun orderByDistanciaASC(): List<Parque>

    @Query("SELECT * FROM ParqueList ORDER BY distancia DESC")
    fun orderByDistanciaDESC(): List<Parque>

    @Query("SELECT * FROM ParqueList ORDER BY percentagem ASC")
    fun orderByTaxaOcupacaoASC(): List<Parque>

    @Query("SELECT * FROM ParqueList ORDER BY percentagem DESC")
    fun orderByTaxaOcupacaoDESC(): List<Parque>


//QUERYS COM ORDENACAO ASC DISTANCIA//

    @Query("SELECT * FROM ParqueList  WHERE tipo = :tipo ORDER BY distancia ASC")
    fun getTipoASCDistancia(tipo: String): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE percentagem>:menor AND percentagem<:maior ORDER BY distancia ASC")
    fun getOcupacaoASCDistancia(menor: Int, maior: Int): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE distancia>:menor AND distancia<:maior ORDER BY distancia ASC")
    fun getDistanceASCDistancia(menor: Double, maior: Double): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND percentagem>:menor AND percentagem<:maior AND distancia>:menos AND distancia<:mais ORDER BY distancia ASC")
    fun getOcupacao_Tipo_DistanceASCDistancia(
        tipo: String,
        menor: Int,
        maior: Int,
        menos: Double,
        mais: Double
    ): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND percentagem>:menor AND percentagem<:maior ORDER BY distancia ASC")
    fun getOcupacao_TipoASCDistancia(
        tipo: String,
        menor: Int,
        maior: Int
    ): List<Parque>

    @Query("SELECT * FROM ParqueList WHERE percentagem>:menor AND percentagem<:maior AND distancia>:menos AND distancia<:mais ORDER BY distancia ASC")
    fun getOcupacao_DistanceASCDistancia(
        menor: Int,
        maior: Int,
        menos: Double,
        mais: Double
    ): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND distancia>:menor AND distancia<:maior ORDER BY distancia ASC")
    fun getDistance_TipoASCDistancia(
        tipo: String,
        menor: Double,
        maior: Double
    ): List<Parque>


//QUERYS COM ORDENACAO DESC DISTANCIA//

    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo ORDER BY distancia DESC")
    fun getTipoDESCDistancia(tipo: String): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE percentagem>:menor AND percentagem<:maior ORDER BY distancia DESC")
    fun getOcupacaoDESCDistancia(menor: Int, maior: Int): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE distancia>:menor AND distancia<:maior ORDER BY distancia DESC")
    fun getDistanceDESCDistancia(menor: Double, maior: Double): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND percentagem>:menor AND percentagem<:maior AND distancia>:menos AND distancia<:mais ORDER BY distancia DESC")
    fun getOcupacao_Tipo_DistanceDESCDistancia(
        tipo: String,
        menor: Int,
        maior: Int,
        menos: Double,
        mais: Double
    ): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND percentagem>:menor AND percentagem<:maior ORDER BY distancia DESC")
    fun getOcupacao_TipoDESCDistancia(
        tipo: String,
        menor: Int,
        maior: Int
    ): List<Parque>

    @Query("SELECT * FROM ParqueList WHERE percentagem>:menor AND percentagem<:maior AND distancia>:menos AND distancia<:mais ORDER BY distancia DESC")
    fun getOcupacao_DistanceDESCDistancia(
        menor: Int,
        maior: Int,
        menos: Double,
        mais: Double
    ): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND distancia>:menor AND distancia<:maior ORDER BY distancia DESC")
    fun getDistance_TipoDESCDistancia(
        tipo: String,
        menor: Double,
        maior: Double
    ): List<Parque>

//QUERYS COM ORDENACAO ASC OCUPACAO//

    @Query("SELECT * FROM ParqueList  WHERE tipo = :tipo ORDER BY percentagem ASC")
    fun getTipoASCOcupacao(tipo: String): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE percentagem>:menor AND percentagem<:maior ORDER BY percentagem ASC")
    fun getOcupacaoASCOcupacao(menor: Int, maior: Int): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE distancia>:menor AND distancia<:maior ORDER BY percentagem ASC")
    fun getDistanceASCOcupacao(menor: Double, maior: Double): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND percentagem>:menor AND percentagem<:maior AND distancia>:menos AND distancia<:mais ORDER BY percentagem ASC")
    fun getOcupacao_Tipo_DistanceASCOcupacao(
        tipo: String,
        menor: Int,
        maior: Int,
        menos: Double,
        mais: Double
    ): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND percentagem>:menor AND percentagem<:maior ORDER BY percentagem ASC")
    fun getOcupacao_TipoASCOcupacao(
        tipo: String,
        menor: Int,
        maior: Int
    ): List<Parque>

    @Query("SELECT * FROM ParqueList WHERE percentagem>:menor AND percentagem<:maior AND distancia>:menos AND distancia<:mais ORDER BY percentagem ASC")
    fun getOcupacao_DistanceASCOcupacao(
        menor: Int,
        maior: Int,
        menos: Double,
        mais: Double
    ): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND distancia>:menor AND distancia<:maior ORDER BY percentagem ASC")
    fun getDistance_TipoASCOcupacao(
        tipo: String,
        menor: Double,
        maior: Double
    ): List<Parque>


//QUERYS COM ORDENACAO DESC OCUPACAO//

    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo ORDER BY percentagem DESC")
    fun getTipoDESCOcupacao(tipo: String): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE percentagem>:menor AND percentagem<:maior ORDER BY percentagem DESC")
    fun getOcupacaoDESCOcupacao(menor: Int, maior: Int): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE distancia>:menor AND distancia<:maior ORDER BY percentagem DESC")
    fun getDistanceDESCOcupacao(menor: Double, maior: Double): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND percentagem>:menor AND percentagem<:maior AND distancia>:menos AND distancia<:mais ORDER BY percentagem DESC")
    fun getOcupacao_Tipo_DistanceDESCOcupacao(
        tipo: String,
        menor: Int,
        maior: Int,
        menos: Double,
        mais: Double
    ): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND percentagem>:menor AND percentagem<:maior ORDER BY percentagem DESC")
    fun getOcupacao_TipoDESCOcupacao(
        tipo: String,
        menor: Int,
        maior: Int
    ): List<Parque>

    @Query("SELECT * FROM ParqueList WHERE percentagem>:menor AND percentagem<:maior AND distancia>:menos AND distancia<:mais ORDER BY percentagem DESC")
    fun getOcupacao_DistanceDESCOcupacao(
        menor: Int,
        maior: Int,
        menos: Double,
        mais: Double
    ): List<Parque>


    @Query("SELECT * FROM ParqueList WHERE tipo = :tipo AND distancia>:menor AND distancia<:maior ORDER BY percentagem DESC")
    fun getDistance_TipoDESCOcupacao(
        tipo: String,
        menor: Double,
        maior: Double
    ): List<Parque>

}
