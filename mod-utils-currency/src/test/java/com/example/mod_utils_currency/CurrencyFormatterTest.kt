package com.example.mod_utils_currency

import org.junit.Test
import java.util.Locale


class CurrencyFormatterTest {

    private val currencyFormatter by lazy { CurrencyFormatter() }

    @Test
    fun `Given a US Locale and 2 fraction digits, When the amount is formatted, Then the currency is correctly formatted`() {
        val amount = 1000
        val locale = Locale.US
        val expected = "$1,000.00"

        val result = currencyFormatter.format(amount, locale, 2)

        assert(expected == result)
    }

    @Test
    fun `Given a UK Locale and 2 fraction digits, When the amount is formatted, Then the currency is correctly formatted`() {
        val amount = 1000
        val locale = Locale.UK
        val expected = "£1,000.00"

        val result = currencyFormatter.format(amount, locale, 2)

        assert(expected == result)
    }

    @Test
    fun `Given a US Locale and 0 fraction digits, When the amount is formatted, Then the currency is correctly formatted`() {
        val amount = 1000
        val locale = Locale.US
        val expected = "$1,000"

        val result = currencyFormatter.format(amount, locale, 0)

        assert(expected == result)
    }
}