package br.com.felipebertanha.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.felipebertanha.financask.delegate.TransacaoDelegate
import br.com.felipebertanha.financask.extension.formataParaBrasileiro
import br.com.felipebertanha.financask.model.Transacao

class AlteraTransacaoDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : FormularioTransacaoDialog(context, viewGroup) {


    fun chama(transacao: Transacao, transacaoDelegate: TransacaoDelegate) {
        val tipo = transacao.tipo


        super.chama(tipo, transacaoDelegate)

        campoValor.setText(transacao.valor.toString())
        campoData.setText(transacao.data.formataParaBrasileiro())
        val categoriasRetornadas = context.resources.getStringArray(categoriaPor(tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }
}