package com.b2i.faf.domain.account.port

/**
 * @author Alexwilfriedo
 **/
interface UserDomain : IManageUserAccess, IRegisterUser, IAuthenticateUser,
        IRequestUser,IManagePasswordReset