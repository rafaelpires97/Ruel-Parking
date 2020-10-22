package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.GiraModel
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Historico
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Parque
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Veiculo
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.repository.local.giraStation.GiraStationDAO
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.repository.local.historico.HistoricoDAO
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.veiculo.VeiculoDAO

@Database(entities =  [Parque::class, Veiculo::class, Historico::class, GiraModel::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun parqueDAO(): ParqueDAO
    abstract fun veiculoDAO(): VeiculoDAO
    abstract fun historicoDAO(): HistoricoDAO
    abstract fun giraDAO():GiraStationDAO

    companion object {

        private lateinit var INSTANCE: AppDataBase

        fun getDatabase(context: Context): AppDataBase {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context, AppDataBase::class.java, "AppDB")
                        .allowMainThreadQueries()
                        .addMigrations(MIGRATION_1_2)
                        .build()

                }
            }
            return INSTANCE
        }
        /**
         * Atualização de versão de banco de dados
         */
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DELETE FROM ParqueList")
            }
        }

    }
}