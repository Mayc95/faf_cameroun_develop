package com.b2i.faf.application.bootstrap

import com.b2i.faf.domain.account.entity.UserType
import com.b2i.faf.domain.account.port.RoleDomain
import com.b2i.faf.domain.entity.common.Role


/**
 * @author alexwilfriedo
 */
object RoleBootstrap {

    fun seed(
            roleDomain: RoleDomain,
    ) {

        if (roleDomain.count() == 0L) {
            roleDomain.save(Role(UserType.ADMIN, UserType.ADMIN))
            roleDomain.save(Role(UserType.ACTUATOR, UserType.ACTUATOR))
            roleDomain.save(Role(UserType.OPERATOR, UserType.OPERATOR))
            roleDomain.save(Role(UserType.ORGANIZATION, UserType.ORGANIZATION))
            roleDomain.save(Role(UserType.CONTACT, UserType.CONTACT))
            roleDomain.save(Role(UserType.CHIEF, UserType.CHIEF))
            roleDomain.save(Role(UserType.MANAGER, UserType.MANAGER))
        }

    }
}