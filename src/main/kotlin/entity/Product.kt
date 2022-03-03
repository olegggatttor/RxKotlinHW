package entity

import org.bson.Document

data class Product(val name: String, val price: Double) {
    constructor(doc: Document) : this(doc.getString("name"), doc.getDouble("price"))

    fun toString(currency: String): String {
        val realCurrency = when (currency) {
            "RUB", "USD", "EUR"-> currency
            else -> "RUB"
        }
        val alpha = when (currency) {
            "USD" -> 104.27 // 28.02.2022
            "EUR" -> 117.42 // 28.02.2022
            "RUB" -> 1.0
            else -> 1.0
        }

        return "Name: $name\nPrice: ${price / alpha} $realCurrency"
    }
}
