package com.b2i.faf.application.controlForm


import com.b2i.faf.application.facade.AuthenticationFacade
import com.b2i.faf.domain.account.entity.User
import com.b2i.faf.domain.account.port.UserDomain
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service


@Service
class VerifyUser(@Qualifier("userWorker") private val userManager: UserDomain,
                 private val authenticationFacade: AuthenticationFacade) {

    fun userExistsByUsername(pseudo: String): Boolean {
        var exist: Boolean = false

        val user: User? = userManager.findByUsername(pseudo).orElse(null)

        if (user != null) {
            exist = true
        }

        return exist
    }


    fun userExistsByPhone(pseudo: String): Boolean {
        var exist: Boolean = false

        val user: User? = userManager.findByUsername(pseudo).orElse(null)

        if (user != null) {
            exist = true
        }

        return exist
    }

    fun userExistsByEmail(email: String): Boolean {
        var exist: Boolean = false

        val user: User? = userManager.findByEmail(email).orElse(null)

        if (user != null) {
            exist = true
        }

        return exist
    }


    fun getUser( username : String ) : User? {

        return userManager.findByUsername(username).orElse(null)
    }

    fun getUserAuthenticated() : User {
        return authenticationFacade.getAuthenticatedUser().get()
    }
}