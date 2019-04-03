package br.com.felipebertanha.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.felipebertanha.financask.R
import br.com.felipebertanha.financask.delegate.TransacaoDelegate
import br.com.felipebertanha.financask.extension.formataParaBrasileiro
import br.com.felipebertanha.financask.model.Tipo
import br.com.felipebertanha.financask.model.Transacao

class AlteraTransacaoDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Alterar"

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.altera_receita
        }
        return R.string.altera_despesa
    }


    fun chama(transacao: Transacao, transacaoDelegate: TransacaoDelegate) {
        super.chama(transacao.tipo, transacaoDelegate)

        inicializaCampos(transacao)
    }

    private fun inicializaCampos(
        transacao: Transacao
    ) {
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCategoria(transacao)
    }

    private fun inicializaCategoria(
        transacao: Transacao
    ) {
        val categoriasRetornadas = context.resources.getStringArray(categoriaPor(transacao.tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }
}