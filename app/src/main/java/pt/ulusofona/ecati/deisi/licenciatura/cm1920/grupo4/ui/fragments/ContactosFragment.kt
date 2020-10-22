package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_contactos.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.adapter.ContactosAdapter
import java.util.ArrayList

class ContactosFragment : Fragment() {

    val header : MutableList<String> = ArrayList()
    val body : MutableList<MutableList<String>> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_contactos,container,false)

        return view
    }

    override fun onStart() {
        super.onStart()

        val pontosDeAtendimento : MutableList<String> = ArrayList()
        pontosDeAtendimento.add("           "+getString(R.string.lojac_saldanha))
        pontosDeAtendimento.add("           "+getString(R.string.lojac_Laranjeiras))

        val geralEMEL : MutableList<String> = ArrayList()
        geralEMEL.add(getString(R.string.telefone)+"\n" +
                "         "+getString(R.string.horario)+"\n" +
                "                     "+getString(R.string.horario_2)+"\n" +
                "         "+getString(R.string.contactar)+"\n" +
                "         "+getString(R.string.contactar_2))

        val veiculosRemovidos: MutableList<String> = ArrayList()
        veiculosRemovidos.add("         "+getString(R.string.morada)+"\n" +
                "         "+getString(R.string.rebocar)+"\n" +
                "         "+getString(R.string.informacoes)+"\n" +
                "         "+getString(R.string.horario)+"\n" +
                "         "+getString(R.string.horario_limitado)+"\n" +
                "         "+getString(R.string.excepcional))

        val veiculosBloqueados: MutableList<String> = ArrayList()
        veiculosBloqueados.add("         "+getString(R.string.telefone2)+"\n" +
                "         "+getString(R.string.horario)+"\n" +
                "         "+getString(R.string.horario_2))

        val confirmacaoVeiculoRemovido: MutableList<String> = ArrayList()
        confirmacaoVeiculoRemovido.add("        "+getString(R.string.sms_gratuito)+"\n" +
                "        "+getString(R.string.reboque_seguida)+"\n" +
                "        "+getString(R.string.com_ou_sem)+"\n" +
                "        "+getString(R.string.informacoes_sobre_veiculos)+"\n" +
                "        "+getString(R.string.como_resposta)+"\n" +
                "        "+getString(R.string.as_forças_de_seguranca)+"\n" +
                "        "+getString(R.string.se_o_carro_foi_removido)+"\n" +
                "        "+getString(R.string.de_lisboa)+"\n")

        val clienteEmel: MutableList<String> = ArrayList()
        clienteEmel.add("         E-mail: provedoria@emel.pt\n" +
                "         Alameda das Linhas de Torres, nº. 198/200\n" +
                "         1769-032 Lisboa")

        val encarregadoProtecaoDados : MutableList<String> = ArrayList()
        encarregadoProtecaoDados.add("          E-mail: dpo@emel.pt\n" +
                "          Alameda das Linhas de Torres, 198 / 200\n" +
                "          1769-032 Lisboa")



        header.add("        "+getString(R.string.ponto_atendimento))
        body.add(pontosDeAtendimento)

        header.add("        "+getString(R.string.Emel_Geral))
        body.add(geralEMEL)

        header.add("        "+getString(R.string.parque_veiculo_removido))
        body.add(veiculosRemovidos)

        header.add("        "+getString(R.string.veiculo_bloqueado))
        body.add(veiculosBloqueados)

        header.add("        "+getString(R.string.veiculoRemovido))
        body.add(confirmacaoVeiculoRemovido)

        header.add("        "+getString(R.string.provedoria))
        body.add(clienteEmel)

        header.add("        "+getString(R.string.encarregado_protecao))
        body.add(encarregadoProtecaoDados)


        expandableListView.setAdapter(
            ContactosAdapter(
                activity as Context,
                header,
                body
            )
        )


    }

}
