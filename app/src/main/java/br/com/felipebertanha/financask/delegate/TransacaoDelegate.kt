package br.com.felipebertanha.financask.delegate

import br.com.felipebertanha.financask.model.Transacao

interface TransacaoDelegate {
    fun delegate(transacao: Transacao)
}