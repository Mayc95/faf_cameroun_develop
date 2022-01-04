package com.b2i.faf.domain.entity.common

import javax.persistence.MappedSuperclass

/**
 * @author alexwilfriedo
 */
@MappedSuperclass
abstract class GeolocatedBaseEntity(var longitude: Double = 0.toDouble(),
                                    var latitude: Double = 0.toDouble()) : BaseEntity()
