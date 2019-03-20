package br.com.felipebertanha.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.felipebertanha.financask.R
import br.com.felipebertanha.financask.delegate.TransacaoDelegate
import br.com.felipebertanha.financask.extension.converteParaCalendar
import br.com.felipebertanha.financask.extension.formataParaBrasileiro
import br.com.felipebertanha.financask.model.Tipo
import br.com.felipebertanha.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val viewCriada = criaLayout()

    fun configuraDialog(transacaoDelegate: TransacaoDelegate) {
        configuraCampoData()
        configuraCampoCategoria()
        configuraFormulario(transacaoDelegate)
    }

    private fun configuraFormulario(transacaoDelegate: TransacaoDelegate) {
        AlertDialog.Builder(context)
            .setTitle(R.string.adiciona_receita)
            .setView(viewCriada)
            .setPositiveButton(
                "Adicionar"
            ) { _, _ ->
                val valorEmTexto = viewCriada.form_transacao_valor.text.toString()

                val dataEmTexto = viewCriada.form_transacao_data.text.toString()

                val categoriaEmTexto = viewCriada.form_transacao_categoria.selectedItem.toString()


                //try expression

                val valor = converteCampoValor(valorEmTexto)

                val data = dataEmTexto.converteParaCalendar()

                val transacao = Transacao(valor, categoriaEmTexto, Tipo.RECEITA, data)


                transacaoDelegate.delegate(transacao)


            }
            .setNegativeButton("Cancelar", null)
            .show()
    }


    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)

        } catch (e: NumberFormatException) {
            Toast.makeText(context, "Falha na conversão de valor!", Toast.LENGTH_LONG).show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria() {
        val adapter = ArrayAdapter
            .createFromResource(
                context,
                R.array.categorias_de_receita,
                android.R.layout.simple_spinner_dropdown_item
            )
        viewCriada.form_transacao_categoria.adapter = adapter
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)


        viewCriada.form_transacao_data
            .setText(hoje.formataParaBrasileiro())

        viewCriada.form_transacao_data
            .setOnClickListener {
                DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val dataSelecionada = Calendar.getInstance()

                        dataSelecionada.set(year, month, dayOfMonth)

                        viewCriada.form_transacao_data
                            .setText(dataSelecionada.formataParaBrasileiro())
                    }, ano, mes, dia
                )
                    .show()
            }
    }

    private fun criaLayout(): View {
        val viewCriada = LayoutInflater.from(context).inflate(
            R.layout.form_transacao,
            viewGroup,
            false
        )
        return viewCriada
    }
}