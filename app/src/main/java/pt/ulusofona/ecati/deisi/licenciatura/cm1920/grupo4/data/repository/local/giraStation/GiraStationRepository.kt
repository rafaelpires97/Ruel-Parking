package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.repository.local.giraStation

import android.content.Context
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Historico
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.AppDataBase


class GiraStationRepository(context: Context) {

    // Acesso ao banco de dados
    private val mDataBase = AppDataBase.getDatabase(context).historicoDAO()

    /**
     * Carrega parque
     */
    fun get(id: Int): Historico {
        return mDataBase.load(id)
    }

    /**
     * Insere parque
     */
    fun save(historicoItem: Historico): Boolean {
        return mDataBase.save(historicoItem) > 0
    }

    /**
     * Faz a listagem de todos os parques
     */
    fun getAll(): List<Historico> {
        return mDataBase.getList()
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
    fun update(historicoItem: Historico): Boolean {
        return mDataBase.update(historicoItem) > 0
    }

    /**
     * Remove parque
     */
    fun delete(historicoItem: Historico) {
        mDataBase.delete(historicoItem)
    }

}