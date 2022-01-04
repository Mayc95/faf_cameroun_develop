package com.b2i.faf.infrastructure.db.repository

import com.b2i.faf.domain.account.entity.Sex
import org.springframework.data.jpa.repository.JpaRepository

interface SexRepository:JpaRepository<Sex,Long> {
}