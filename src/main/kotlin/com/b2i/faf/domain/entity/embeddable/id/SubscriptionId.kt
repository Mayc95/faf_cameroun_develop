package com.b2i.faf.domain.entity.embeddable.id

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class SubscriptionId(private val userId:Long, private val serviceId:Long):Serializable {


}