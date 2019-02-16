package com.dnsoftindia.routes

import com.dnsoftindia.database.DBSettings
import com.dnsoftindia.models.Address
import com.dnsoftindia.models.Addresses
import com.dnsoftindia.models.Friend
import com.dnsoftindia.models.Friends
import com.dnsoftindia.routes.FriendRouter.Companion.handleGetFriendRequest
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.util.pipeline.PipelineContext
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class FriendRouter {
    companion object {

        suspend fun PipelineContext<Unit, ApplicationCall>.handleGetFriendRequest() {
            val friends: MutableSet<Friend> = mutableSetOf()
            val addressesForFriends: MutableList<Address> = mutableListOf()
            transaction(DBSettings.db) {
                Addresses.join(Friends, JoinType.INNER, additionalConstraint = {
                    Friends.id eq Addresses.friendId
                }).selectAll().iterator().forEach {
                    val friend: Friend = Friend()
                    friend.id = it[Friends.id]
                    friend.firstName = it[Friends.firstName]
                    friend.lastName = it[Friends.lastName]
                    friend.age = it[Friends.age]
                    friend.married = it[Friends.married]
                    val address: Address = Address()
                    address.id = it[Addresses.id]
                    address.city = it[Addresses.city]
                    address.street = it[Addresses.street]
                    address.friendId = it[Addresses.friendId]
                    addressesForFriends.add(address)
                    friends.add(friend)
                    System.out.println(it.toString())
                }
            }.apply {

                for (f in friends) {
                    val addresses: MutableList<Address> = mutableListOf()
                    for(a in addressesForFriends) {
                        if (a.friendId == f.id) {
                            addresses.add(a)
                            continue
                        }
                    }
                    f.addresses = addresses
                }

                call.respond(friends)
            }
        }

    }




}

fun Routing.root() {
    get("/friend") {
        handleGetFriendRequest()
    }
}

