package com.b2i.faf.domain.account.entity

import com.b2i.faf.domain.entity.common.BaseEntity
import javax.persistence.Entity

@Entity
class SmsData:BaseEntity() {

    var receiver:String=""

    var sender:String=""

    var message_id:String=""

    var message_count:String=""

    var network:String=""

}