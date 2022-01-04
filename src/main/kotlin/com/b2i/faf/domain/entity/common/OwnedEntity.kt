package com.b2i.faf.domain.entity.common

import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class OwnedEntity : BaseEntity() {

    var owner: Long = -1L
}