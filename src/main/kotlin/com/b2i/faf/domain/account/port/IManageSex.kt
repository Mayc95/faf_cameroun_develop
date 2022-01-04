package com.b2i.faf.domain.account.port

import com.b2i.faf.domain.account.entity.Sex
import com.b2i.faf.utils.OperationResult
import java.util.*

interface IManageSex {

    fun saveSex(sex: Sex): OperationResult<Sex>

    fun findAllSex():MutableList<Sex>

    fun countSex():Long

    fun findByIdSex(id:Long): Optional<Sex>

}