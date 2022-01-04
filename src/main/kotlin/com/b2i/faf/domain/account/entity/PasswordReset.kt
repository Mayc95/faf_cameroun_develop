package com.b2i.faf.domain.account.entity


import com.b2i.faf.domain.entity.common.BaseEntity
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class PasswordReset: BaseEntity() {

    var token:String=""

    var expiredDate: LocalDateTime?=null

    var expiredTime:LocalTime?=null

    var beginTime:LocalTime?=null

    @ManyToOne(targetEntity = User::class,optional = false)
    var user:User?=null

    @Column(columnDefinition = "text")
    var linkSend=""

    var state:String=""

    var reason:String=""

}