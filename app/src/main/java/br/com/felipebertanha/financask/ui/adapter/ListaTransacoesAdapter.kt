package br.com.felipebertanha.financask.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.felipebertanha.financask.R
import br.com.felipebertanha.financask.extension.formataParaBrasileiro
import br.com.felipebertanha.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(
    private val context: Context,
    private val transacoes: List<Transacao>
) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes[position]
        viewCriada.transacao_categoria.text = transacao.categoria
        viewCriada.transacao_valor.text = transacao.valor.toString()



        viewCriada.transacao_data.text = transacao.data.formataParaBrasileiro()
        return viewCriada

    }


    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }


}