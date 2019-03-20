package br.com.felipebertanha.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import br.com.felipebertanha.financask.R
import br.com.felipebertanha.financask.delegate.TransacaoDelegate
import br.com.felipebertanha.financask.model.Transacao
import br.com.felipebertanha.financask.ui.adapter.ListaTransacoesAdapter
import br.com.felipebertanha.financask.ui.dialog.AdicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?): Unit {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()

        configuraLista()

        lista_transacoes_adiciona_receita
            .setOnClickListener {
                AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
                    .configuraDialog(object : TransacaoDelegate {
                        override fun delegate(transacao: Transacao) {
                            atualizaTransacoes(transacao)
                            lista_transacoes_adiciona_menu.close(true)
                        }

                    })

            }
    }



    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(this, transacoes)
    }


}