package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque

import android.content.Context
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Parque


class ParqueRepository(context: Context) {

    // Acesso ao banco de dados
    private val mDataBase = AppDataBase.getDatabase(context).parqueDAO()

    /**
     * Carrega parque
     */
    fun get(id: Int): Parque {
        return mDataBase.load(id.toString())
    }

    /**
     * Insere parque
     */
    fun save(parque: Parque): Boolean {
        return mDataBase.save(parque) > 0
    }

    fun insertAll(parques: List<Parque>) {
        return mDataBase.insertAll(parques)
    }

    /**
     * Faz a listagem de todos os parques
     */
    fun getAll(): List<Parque> {
        return mDataBase.getAll()
    }


    fun registerListener(){

    }
    /*
    /**
     * Faz a listagem de todos os convidados presentes
     */
    fun getPresent(): List<ParqueModel> {
        return mDataBase.getPresent()
    }

    /**
     * Faz a listagem de todos os convidados presentes
     */
    fun getAbsent(): List<GuestModel> {
        return mDataBase.getAbsent()
    }
*/


    /**
     * Atualiza parque
     */
    fun update(parque: Parque): Boolean {
        return mDataBase.update(parque) > 0
    }

    /**
     * Remove parque
     */
    fun delete(parque: Parque) {
        mDataBase.delete(parque)
    }

}