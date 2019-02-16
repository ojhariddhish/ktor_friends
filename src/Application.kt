package com.dnsoftindia

import com.dnsoftindia.database.DBSettings
import com.dnsoftindia.routes.FriendRouter
import com.dnsoftindia.routes.root
import io.ktor.application.*
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.event.Level

val myRouter: FriendRouter = FriendRouter()

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, port = 8080, module = Application::mainModule)

//    DBSettings.createTablesAndData()

    server.start(wait = true)
}

@Suppress("unused") // Referenced in application.conf
fun Application.mainModule() {
    install(ContentNegotiation) {
        gson { setPrettyPrinting() }
    }
    install(CallLogging) {
        level = Level.TRACE
    }
    routing {
        root()
    }
}


