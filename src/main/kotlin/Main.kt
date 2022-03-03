import database.RxMongoDatabase
import server.RxNettyHttpServer

fun main() {
    val db = RxMongoDatabase()
    RxNettyHttpServer(db).run()
}