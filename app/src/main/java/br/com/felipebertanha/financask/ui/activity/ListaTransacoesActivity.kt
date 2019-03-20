package br.com.felipebertanha.financask.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.felipebertanha.financask.R
import br.com.felipebertanha.financask.extension.formataParaBrasileiro
import br.com.felipebertanha.financask.model.Tipo
import br.com.felipebertanha.financask.model.Transacao
import br.com.felipebertanha.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?): Unit {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()

        configuraLista()

        lista_transacoes_adiciona_receita
            .setOnClickListener {
                configuraDialog()
            }
    }

    private fun configuraDialog() {
        val view: ViewGroup = window.decorView as ViewGroup

        val viewCriada = LayoutInflater.from(this).inflate(
            R.layout.form_transacao,
            view,
            false
        )

        val hoje = Calendar.getInstance()
        viewCriada.form_transacao_data
            .setText(hoje.formataParaBrasileiro())
        viewCriada.form_transacao_data
            .setOnClickListener {
                DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(year, month, dayOfMonth)

                        viewCriada.form_transacao_data
                            .setText(dataSelecionada.formataParaBrasileiro())
                    }, hoje.get(Calendar.YEAR), hoje.get(Calendar.MONTH), hoje.get(Calendar.DAY_OF_MONTH)
                )
                    .show()
            }


        val adapter = ArrayAdapter
            .createFromResource(
                this,
                R.array.categorias_de_receita,
                android.R.layout.simple_spinner_dropdown_item
            )
        viewCriada
            .form_transacao_categoria.adapter = adapter

        AlertDialog.Builder(this)
            .setTitle(R.string.adiciona_receita)
            .setView(viewCriada)
            .setPositiveButton("Adicionar", { dialog, which ->
                val valorEmTexto = viewCriada.form_transacao_valor.text.toString()

                val dataEmTexto = viewCriada.form_transacao_data.text.toString()

                val categoriaEmTexto = viewCriada.form_transacao_categoria.selectedItem.toString()


                //try expression

                val valor = try {
                    BigDecimal(valorEmTexto)

                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Falha na conversão de valor!", Toast.LENGTH_LONG).show()
                    BigDecimal.ZERO
                }


                val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
                val dataConvertida: Date = formatoBrasileiro.parse(dataEmTexto)
                val data = Calendar.getInstance()
                data.time = dataConvertida

                val transacao = Transacao(valor, categoriaEmTexto, Tipo.RECEITA, data)

                atualizaTransacoes(transacao)
                lista_transacoes_adiciona_menu.close(true)

            })
            .setNegativeButton("Cancelar", null)
            .show()
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