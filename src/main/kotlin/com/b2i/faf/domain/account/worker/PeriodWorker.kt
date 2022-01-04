package com.b2i.faf.domain.account.worker

import com.b2i.faf.domain.account.entity.Period
import com.b2i.faf.domain.account.port.PeriodDomain
import com.b2i.faf.infrastructure.db.repository.PeriodRepository
import com.b2i.faf.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class PeriodWorker:PeriodDomain {

    @Autowired
    lateinit var periodRepository: PeriodRepository

    override fun findAllPeriod(): MutableList<Period> {
        return periodRepository.findAll()
    }

    override fun savePeriod(period: Period): OperationResult<Period> {
        return OperationResult(periodRepository.save(period), mutableMapOf())
    }

    override fun findByIdPeriod(id: Long): Optional<Period> {
        return periodRepository.findById(id)
    }

    override fun countPeriod(): Long {
        return periodRepository.count()
    }

    override fun findAllPeriodByState(state: String): MutableList<Period> {
        return periodRepository.findAllByState(state)
    }

    override fun findPeriodByLabel(label: String): Optional<Period> {
        return periodRepository.findByLabel(label)
    }


}