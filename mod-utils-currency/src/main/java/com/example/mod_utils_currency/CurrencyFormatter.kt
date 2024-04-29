package com.example.mod_utils_currency

import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

class CurrencyFormatter @Inject constructor() {

    fun format(
        amount: Int,
        locale: Locale = Locale.getDefault(),
        maximumFractionDigits: Int = 2
    ): String {
        val formatter = NumberFormat.getCurrencyInstance(locale)
        formatter.maximumFractionDigits = maximumFractionDigits
        return formatter.format(amount)
    }
}