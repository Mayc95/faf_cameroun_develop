package com.b2i.faf.domain.account.entity

import com.b2i.faf.domain.entity.common.BaseEntity
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Dates:BaseEntity() {

    lateinit var hourBegin: LocalDateTime

    lateinit var hourEnd : LocalDateTime

    var interval : Long = 0L

    @ManyToOne(targetEntity = Period::class)
    var period:Period?=null

    var dateCreation:String=""

    var state:String=""

}