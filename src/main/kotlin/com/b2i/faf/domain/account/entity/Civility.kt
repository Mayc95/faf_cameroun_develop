package com.b2i.faf.domain.account.entity


import com.b2i.faf.domain.entity.common.BaseEntity
import javax.persistence.Entity

@Entity
class Civility: BaseEntity() {

    var label:String=""

    var description:String=""

    var state:String=""


}