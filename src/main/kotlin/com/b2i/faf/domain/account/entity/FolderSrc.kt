package com.b2i.faf.domain.account.entity

import java.nio.file.Path
import java.nio.file.Paths

object FolderSrc {

    val SRC_MEMBER: Path = Paths.get("upload/member")
    val SRC_STAFF: Path = Paths.get("upload/staff")
    val SRC_CUSTOMER: Path = Paths.get("upload/customer")
    val SRC_UPLOAD:Path=Paths.get("upload")
    val SRC_DOC_EVENT:Path=Paths.get("upload/docs")
}