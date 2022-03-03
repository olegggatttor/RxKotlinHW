package server

import database.RxMongoDatabase
import io.reactivex.netty.protocol.http.server.HttpServer
import rx.Observable

class RxNettyHttpServer(private val database: RxMongoDatabase) {
    fun run() =
        HttpServer.newServer(8888).start { req, resp ->
            val request = req.decodedPath.substring(1)
            when (request) {
                "register" -> {
                    val username = req.queryParameters["username"]?.first()
                        ?: return@start resp.writeString(Observable.just("Username is not provided."))
                    val password = req.queryParameters["password"]?.first()
                        ?: return@start resp.writeString(Observable.just("Password is not provided."))
                    val currency = req.queryParameters["currency"]?.first()
                        ?: return@start resp.writeString(Observable.just("Currency is not provided."))



                    database.register(username, password, currency)

                    return@start resp.writeString(Observable.just("New user registered: $username"))
                }
                "users" -> return@start resp.writeString(database.getUsers().map { "$it\n" })
                "add_product" -> {
                    val name = req.queryParameters["name"]?.first()
                        ?: return@start resp.writeString(Observable.just("Product name is not provided."))
                    val price = req.queryParameters["price"]?.first()?.toDoubleOrNull()
                        ?: return@start resp.writeString(Observable.just("Product price is not provided."))

                    database.addProduct(name, price)

                    return@start resp.writeString(Observable.just("New product: $name -- $price"))
                }
                "products" -> {
                    val username = req.queryParameters["username"]?.first()
                        ?: return@start resp.writeString(Observable.just("Username is not provided."))
                    val products = database.getProducts()
                    val user = database.getUser(username)

                    return@start resp.writeString(user
                        .map { it.currency }
                        .flatMap { currency -> products.map { "${it.toString(currency)}\n" } })
                }
                else -> return@start resp.writeString(Observable.just("Unsupported query"))
            }
        }.awaitShutdown()
}