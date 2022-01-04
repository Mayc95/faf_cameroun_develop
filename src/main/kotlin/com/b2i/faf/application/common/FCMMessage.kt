package com.b2i.faf.application.common

/**
 * @author alexwilfriedo
 */
open class FCMMessage(val name: String, val to: String, val data: Map<String, String>)