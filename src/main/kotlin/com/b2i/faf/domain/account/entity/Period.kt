package com.b2i.faf.domain.account.entity

import com.b2i.faf.domain.entity.common.BaseEntity
import javax.persistence.Entity

@Entity
class Period:BaseEntity() {

    var label:String=""

    var description=""

    var state:String=""
}