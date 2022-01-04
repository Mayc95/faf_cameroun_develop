package com.b2i.faf.domain.account.entity

import javax.persistence.*

@Entity
@DiscriminatorValue(UserType.ADMIN)
class Admin : User(){




}