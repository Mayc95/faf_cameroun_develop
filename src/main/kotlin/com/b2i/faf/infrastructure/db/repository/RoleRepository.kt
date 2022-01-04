package com.b2i.faf.infrastructure.db.repository


import com.b2i.faf.domain.entity.common.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: String): Optional<Role>
}
