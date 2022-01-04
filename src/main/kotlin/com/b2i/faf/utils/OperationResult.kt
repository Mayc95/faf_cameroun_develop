package com.b2i.faf.utils

/**
 * @author Alexwilfriedo
 **/
class OperationResult<T>(val data: T?,
                         val errors: MutableMap<String, String>? = null) {

    val isSuccess: Boolean = errors == null || errors.isEmpty()
}