package com.b2i.faf.domain.account.port

import com.b2i.faf.domain.account.entity.Civility
import com.b2i.faf.utils.OperationResult
import java.util.*

interface IManageCivility {

    fun saveCivility(civility: Civility): OperationResult<Civility>

    fun findAllCivility():MutableList<Civility>

    fun countCivility():Long

    fun findByIdCivility(id:Long): Optional<Civility>

    fun findAllByStateCivility(state: String):MutableList<Civility>

}