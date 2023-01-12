package com.juanpabloprado.plugins

import com.juanpabloprado.model.Product
import com.juanpabloprado.persistance.ProductDatabase
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    routing {
        authenticate("auth-basic") {
            post("/products") {
                val body = call.receive<Product>()

                when (val insertedProduct = ProductDatabase.dao.addProduct(body)) {
                    null -> call.respondText(
                        "Failed to add product: $body",
                        status = HttpStatusCode.InternalServerError
                    )

                    else -> call.respondText("Added product: $insertedProduct", status = HttpStatusCode.Created)
                }

            }
        }

        get("/") {
            call.respondText("Hello Ktor!!!!")
        }

        get("/products") {
            call.respond(ProductDatabase.dao.products())
        }

        get("/products/{id}") {
            val upc = call.parameters["id"]!!.toInt()

            when (val product = ProductDatabase.dao.product(upc)) {
                null -> call.respond(HttpStatusCode.NotFound)
                else -> call.respond(product)
            }
        }

    }
}
