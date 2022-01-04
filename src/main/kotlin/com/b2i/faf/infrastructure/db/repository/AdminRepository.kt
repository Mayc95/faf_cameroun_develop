package com.b2i.faf.infrastructure.db.repository

import com.b2i.faf.domain.account.entity.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository:JpaRepository<Admin,Long> {
}