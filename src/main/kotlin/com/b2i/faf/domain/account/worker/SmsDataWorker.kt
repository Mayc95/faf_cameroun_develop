package com.b2i.faf.domain.account.worker

import com.b2i.faf.domain.account.entity.SmsData
import com.b2i.faf.domain.account.port.SmsDataDomain
import com.b2i.faf.infrastructure.db.repository.smsDataRepository
import com.b2i.faf.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class SmsDataWorker:SmsDataDomain {

    @Autowired
    lateinit var smsDataRepository: smsDataRepository

    override fun countSmsData(): Long {
        return smsDataRepository.count()
    }

    override fun findSmsDataById(id: Long): Optional<SmsData> {
        return smsDataRepository.findById(id)
    }

    override fun findAllSmsData(): MutableList<SmsData> {
        return smsDataRepository.findAll()
    }

    override fun saveSmsData(smsData: SmsData): OperationResult<SmsData> {
        return OperationResult(smsDataRepository.save(smsData))
    }
}