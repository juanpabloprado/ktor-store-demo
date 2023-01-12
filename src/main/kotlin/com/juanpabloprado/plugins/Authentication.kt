package com.juanpabloprado.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureAuthentication() {
    install(Authentication) {
        basic("auth-basic") {
            validate { credentials ->
                if (credentials.name == "juan" && credentials.password == "prado") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }
}