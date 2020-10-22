package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.constants

class DataBaseConstants private constructor() {

    object Settings{
        const val DARKMODE = false
        const val WANTDARKMODE = false
    }




    /**
     * Tabelas disponíveis na db com as suas colunas
     */
    object Parque {
        const val TABLE_NAME = "ParqueList"

        object COLUMNS {
            const val ID = "id_parque"
            const val NOME = "nome"
            const val ACTIVO = "activo"
            const val ENTIDADE = "id_entidade"
            const val  CAPACIDADE_MAX = "capacidade_max"
            const val OCUPACAO = "ocupacao"
            const val LATITUDE = "latitude"
            const val LONGITUDE = "longitude"
            const val TIPO = "tipo"
        }
    }

    object Veiculo {
        const val TABLE_NAME = "VeiculoList"

        object COLUMNS {
            const val ID = "id"
            const val MARCA = "marca"
            const val MODELO = "modelo"
            const val MATRICULA = "matricula"
            const val IMAGE_CARRO = "image_carro"
            const val ANO_MATRICULA = "ano_matricula"

        }
    }

    object Historico {
        const val TABLE_NAME = "HistoricoList"


    }

}