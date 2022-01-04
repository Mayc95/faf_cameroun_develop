package com.b2i.faf.application.bootstrap

import com.b2i.faf.domain.account.entity.*
import com.b2i.faf.domain.account.port.RoleDomain
import com.b2i.faf.domain.account.port.UserDomain
import java.util.*

object UserBootstrap {

    fun seed(
            userDomain: UserDomain,
            roleDomain: RoleDomain,
    ){

        userDomain.let {domain ->

            if(userDomain.countAllUserByUserType(UserType.ADMIN)==0L){
                var admin = Admin()
                admin.firstname="jean"
                admin.lastname="jesus"
                admin.contact.email="admin@gmail.com"
                admin.contact.phone="2250140155628"
                admin.password="open"
                admin.username="admin"
                admin.address.primaryAddress="rue des jardins"
                roleDomain.findByName(UserType.ADMIN).ifPresent {
                    admin.roles= Collections.singleton(it)
                }
                domain.createOrUpdateUser(admin)
            }

            if(userDomain.countAllUserByUserType(UserType.ACTUATOR)==0L){
                var actuator = Actuator()
                actuator.firstname="cris"
                actuator.lastname="jesus"
                actuator.contact.email="actuator@gmail.com"
                actuator.contact.phone="2250140155667"
                actuator.password="open"
                actuator.username="actuator"
                actuator.address.primaryAddress="rue des jardins"
               // actuator.civility=paramDomain.findAllCivility().first()
                //actuator.sex=paramDomain.findAllSex().first()
                roleDomain.findByName(UserType.ACTUATOR).ifPresent {
                    actuator.roles= Collections.singleton(it)
                }
                domain.createOrUpdateUser(actuator)
            }


        }

    }

}