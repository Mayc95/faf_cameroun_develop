package com.b2i.faf.application.config.security

import com.b2i.faf.domain.account.port.IAuthenticateUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PatientDetailsImplementation @Autowired
constructor(private val authenticationManager:IAuthenticateUser) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {

        return authenticationManager.findByUsername(username).orElseThrow { UsernameNotFoundException("email or password bad") }
    }


}


