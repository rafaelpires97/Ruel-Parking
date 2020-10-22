package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.veiculo

import android.content.Context
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Veiculo
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.AppDataBase


class VeiculoRepository(context: Context) {

    // Acesso ao banco de dados
    private val mDataBase = AppDataBase.getDatabase(context).veiculoDAO()

    /**
     * Carrega parque
     */
/*
    fun get(id: Int): Veiculo {
        return mDataBase.load(id)
    }*/

    /**
     * Insere parque
     */
    fun save(veiculo: Veiculo): Boolean {
        return mDataBase.save(veiculo) > 0
    }

    /**
     * Faz a listagem de todos os parques
     */
    fun getAll(): List<Veiculo> {
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
    fun update(veiculo: Veiculo): Boolean {
        return mDataBase.update(veiculo) > 0
    }

    /**
     * Remove parque
     */
    fun delete(veiculo: Veiculo) {
        mDataBase.delete(veiculo)
    }

}