package com.b2i.faf.domain.entity.common

import javax.persistence.MappedSuperclass

/**
 * @author alexwilfriedo
 */
@MappedSuperclass
abstract class ReferencedBaseEntity : BaseEntity() {

    var reference: String = ""
}
