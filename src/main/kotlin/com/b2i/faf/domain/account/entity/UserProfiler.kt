package com.b2i.faf.domain.account.entity

/**
 * @author Alexwilfriedo
 */

object UserProfiler {

    fun profile(user: User) = UserProfile(user)

    class UserProfile(user: User) {

        val id = user.id

        val username = user.username

        val firstname = user.firstname

        val lastname = user.lastname

        val img = user.img

        val inService = user.inService

        val isChief = user.chief

        val fullname = "$firstname $lastname"

        val isEnabled = user.isEnabled

        var actuator: Boolean = false

        var operator:Boolean = false

        var contact:Boolean = false

        var admin: Boolean = false

        var organization:Boolean = false

        var chief:Boolean =false

        var manager:Boolean = false


        init {
            val roles = user.roles
            actuator = roles!!.any { it.name == UserType.ACTUATOR }
            operator = roles.any { it.name == UserType.OPERATOR }
            admin = roles.any { it.name == UserType.ADMIN }
            contact = roles.any { it.name == UserType.CONTACT }
            organization = roles.any { it.name == UserType.ORGANIZATION }
            chief = roles.any {it.name == UserType.CHIEF}
            manager = roles.any { it.name == UserType.MANAGER }
        }
    }
}