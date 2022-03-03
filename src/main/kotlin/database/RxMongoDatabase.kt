package database

import com.mongodb.client.model.Filters.eq
import com.mongodb.rx.client.MongoClients
import entity.Product
import entity.User
import org.bson.Document
import rx.Observable
import rx.Subscription

class RxMongoDatabase {
    private val client = MongoClients.create()
    private val database = client.getDatabase("catalog")

    fun register(username: String, password: String, currency: String): Subscription =
        database
            .getCollection("users")
            .insertOne(
                Document(
                    mapOf(
                        "username" to username,
                        "password" to password,
                        "currency" to currency                    )
                )
            )
            .subscribe()

    fun getUser(username: String): Observable<User> =
        database
            .getCollection("users")
            .find(eq("username", username))
            .first()
            .map { User(it) }

    fun getUsers(): Observable<User> =
        database
            .getCollection("users")
            .find()
            .toObservable()
            .map { User(it) }

    fun addProduct(name: String, price: Double): Subscription =
        database
            .getCollection("products")
            .insertOne(
                Document(
                    mapOf(
                        "name" to name,
                        "price" to price
                    )
                )
            )
            .subscribe()

    fun getProducts(): Observable<Product> =
        database
            .getCollection("products")
            .find()
            .toObservable()
            .map { Product(it) }
}