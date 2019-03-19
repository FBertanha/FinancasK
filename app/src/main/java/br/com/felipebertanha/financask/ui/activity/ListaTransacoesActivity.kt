package br.com.felipebertanha.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import br.com.felipebertanha.financask.R
import br.com.felipebertanha.financask.model.Tipo
import br.com.felipebertanha.financask.model.Transacao
import br.com.felipebertanha.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?): Unit {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val lista = listOf(
            Transacao(
                BigDecimal(20.5),
                "Comida",
                Tipo.DESPESA,
                Calendar.getInstance()
            ),
            Transacao(
                BigDecimal(20.5),
                "Economia",
                Tipo.RECEITA,
                Calendar.getInstance()
            ),
            Transacao(
                BigDecimal(201.5),
                "Almoço de fina de semana rs..",
                Tipo.DESPESA,
                Calendar.getInstance()
            )
        )

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lista)

        lista_transacoes_listview.adapter = ListaTransacoesAdapter(this, lista)
    }

}