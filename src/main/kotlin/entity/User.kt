package entity

import org.bson.Document

data class User(val username: String, val currency: String) {
    constructor(doc: Document) : this(doc.getString("username"), doc.getString("currency"))

    override fun toString() = "User: $username\nCurrency: $currency"
}
