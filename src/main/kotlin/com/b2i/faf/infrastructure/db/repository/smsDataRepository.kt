package com.b2i.faf.infrastructure.db.repository

import com.b2i.faf.domain.account.entity.SmsData
import org.springframework.data.jpa.repository.JpaRepository

interface smsDataRepository:JpaRepository<SmsData,Long> {
}