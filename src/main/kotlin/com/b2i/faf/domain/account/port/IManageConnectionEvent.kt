package com.b2i.faf.domain.account.port

import com.b2i.faf.domain.entity.common.ConnectionEvent
import com.b2i.faf.utils.OperationResult

/**
 * @author Alexwilfriedo
 **/
interface IManageConnectionEvent {

    fun save(model: ConnectionEvent): OperationResult<ConnectionEvent>

    fun isFirstConnection(id: Long): Boolean
}