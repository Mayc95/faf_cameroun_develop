package com.b2i.faf.domain.account.port

import com.b2i.faf.domain.account.entity.Period
import com.b2i.faf.utils.OperationResult
import java.util.*

interface IManagePeriod {

    fun findAllPeriod():MutableList<Period>

    fun savePeriod(period: Period):OperationResult<Period>

    fun findByIdPeriod(id:Long):Optional<Period>

    fun countPeriod():Long

    fun findAllPeriodByState(state:String):MutableList<Period>

    fun findPeriodByLabel(label: String):Optional<Period>
}