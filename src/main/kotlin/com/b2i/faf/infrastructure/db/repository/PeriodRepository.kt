package com.b2i.faf.infrastructure.db.repository

import com.b2i.faf.domain.account.entity.Period
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PeriodRepository:JpaRepository<Period,Long> {

    fun findAllByState(state: String):MutableList<Period>

    fun findByLabel(label: String):Optional<Period>

}