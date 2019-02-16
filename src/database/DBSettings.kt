package com.dnsoftindia.database

import com.dnsoftindia.models.Addresses
import com.dnsoftindia.models.Friends
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object DBSettings {

    val db by lazy {
        Database.connect("jdbc:mysql://localhost:3306/test_friends", driver = "com.mysql.jdbc.Driver",
            user = "root", password = "")
    }

    fun createTablesAndData() {
        transaction(DBSettings.db) {
            SchemaUtils.create(Friends, Addresses)

            val myfriendId: Int? = Friends.insert {
                it[firstName] = "Riddhish"
                it[lastName] = "Ojha"
                it[age] = 33
                it[married] = false
            } get Friends.id

            Addresses.insert {
                it[street] = "Roop mahal"
                it[city] = "Vapi"
                it[friendId] = myfriendId!!
            }

            Addresses.insert {
                it[street] = "Homli Pol"
                it[city] = "Padiv"
                it[friendId] = myfriendId!!
            }

            val myfriend2Id: Int? = Friends.insert {
                it[firstName] = "Shahrukh"
                it[lastName] = "Khan"
                it[age] = 53
                it[married] = true
            } get Friends.id

            Addresses.insert {
                it[street] = "Mannat"
                it[city] = "Bandra"
                it[friendId] = myfriend2Id!!
            }

        }
    }

}

