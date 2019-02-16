package com.dnsoftindia.models

import org.jetbrains.exposed.sql.Table

data class Address(var id: Int = -1,
                   var street: String = "",
                   var city: String = "",
                   var friendId: Int = -1) {
}

object Addresses: Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val street = varchar("varchar", length = 100)
    val city = varchar("city", length = 100)
    val friendId = integer("friendId") references (Friends.id)
}