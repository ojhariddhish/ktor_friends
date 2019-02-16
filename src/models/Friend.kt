package com.dnsoftindia.models

import org.jetbrains.exposed.sql.Table

data class Friend(var id: Int = -1,
                  var firstName: String = "",
                  var lastName: String = "",
                  var age: Int = -1,
                  var married: Boolean = false,
                  var addresses: List<Address> = emptyList()) {
}

object Friends: Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val firstName = varchar("firstName", length = 100)
    val lastName = varchar("lastName", length = 100)
    val age = integer("age")
    val married = bool("married")
}