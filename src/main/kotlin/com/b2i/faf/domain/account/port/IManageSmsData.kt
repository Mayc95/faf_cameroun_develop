package com.b2i.faf.domain.account.port

import com.b2i.faf.domain.account.entity.SmsData
import com.b2i.faf.utils.OperationResult
import java.util.*

interface IManageSmsData {

    fun countSmsData():Long

    fun findSmsDataById(id:Long):Optional<SmsData>

    fun findAllSmsData():MutableList<SmsData>

    fun saveSmsData(smsData: SmsData):OperationResult<SmsData>

}