package br.com.felipebertanha.financask.model

import java.math.BigDecimal
import java.util.*

data class Transacao(
    val valor: BigDecimal,
    val categoria: String,
    val data: Calendar
)