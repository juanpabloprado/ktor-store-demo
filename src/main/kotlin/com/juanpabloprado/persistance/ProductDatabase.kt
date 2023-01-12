package com.juanpabloprado.persistance

import com.juanpabloprado.model.Product
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object ProductDatabase {
    init {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db"
        val database = Database.connect(jdbcURL, driverClassName)

        transaction {
            // create table if it doesn't exist
            SchemaUtils.create(ProductTable)
        }
    }

    val dao = ProductDAO().apply {
        runBlocking {
            if (products().isEmpty()) {
                addProduct(
                    Product(1, "Coffee Mug", "Your new favorite mug")
                )
            }
        }
    }
}