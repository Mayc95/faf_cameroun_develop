package com.b2i.faf.domain.account.worker

import com.b2i.faf.domain.account.entity.Civility
import com.b2i.faf.domain.account.entity.Sex
import com.b2i.faf.domain.account.port.ParamDomain
import com.b2i.faf.infrastructure.db.repository.CivilityRepository
import com.b2i.faf.infrastructure.db.repository.SexRepository
import com.b2i.faf.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class ParamWorker:ParamDomain {


    @Autowired
    lateinit var sexRepository:SexRepository

    @Autowired
    lateinit var civilityRepository: CivilityRepository

    override fun saveSex(sex: Sex): OperationResult<Sex> {
        return OperationResult(sexRepository.save(sex))
    }

    override fun findAllSex(): MutableList<Sex> {
        return sexRepository.findAll()
    }

    override fun countSex(): Long {
        return sexRepository.count()
    }

    override fun findByIdSex(id: Long): Optional<Sex> {
        return sexRepository.findById(id)
    }

    override fun saveCivility(civility: Civility): OperationResult<Civility> {
        return OperationResult(civilityRepository.save(civility))
    }

    override fun findAllCivility(): MutableList<Civility> {
        return civilityRepository.findAll()
    }

    override fun countCivility(): Long {
        return civilityRepository.count()
    }

    override fun findByIdCivility(id: Long): Optional<Civility> {
        return civilityRepository.findById(id)
    }

    override fun findAllByStateCivility(state: String): MutableList<Civility> {
        return civilityRepository.findAllByState(state)
    }


}