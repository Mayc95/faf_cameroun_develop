package com.b2i.faf.infrastructure.db.repository

import com.b2i.faf.domain.account.entity.Civility
import org.springframework.data.jpa.repository.JpaRepository

interface CivilityRepository:JpaRepository<Civility,Long> {

    fun findAllByState(state: String):MutableList<Civility>



}