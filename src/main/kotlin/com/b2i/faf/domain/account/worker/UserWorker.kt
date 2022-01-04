package com.b2i.faf.domain.account.worker

import com.b2i.faf.domain.account.entity.*
import com.b2i.faf.domain.account.port.UserDomain
import com.b2i.faf.infrastructure.db.repository.*
import com.b2i.faf.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserWorker : UserDomain {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordResetRepository: PasswordResetRepository



    @Autowired
    lateinit var roleRepository: RoleRepository

    override fun updateDataUser(mode: String, user: User): OperationResult<User> {
        val errors:MutableMap<String,String> = mutableMapOf()
        var data:User? = null

        val optionalUser = userRepository.findById(user.id)
        val usernameExists = userRepository.findByUsernameAndIdNot(user.username,user.id)
        val emailExists = userRepository.findByContactEmailAndIdNot(user.contact.email,user.id)
        val phoneExists = userRepository.findByContactPhoneAndIdNot(user.contact.phone,user.id)

        if(!optionalUser.isPresent){
            errors["user_not_found"]="utilisateur introuvable"
        }else{
            when(mode){
                "email"->{
                    if(user.contact.email.isEmpty()){
                        errors["email"]="Remplissez le champ email"
                    }
                    if(emailExists.isPresent){
                        errors["usernameExists"]="Email est deja pris"
                    }
                }
                "phone"->{
                    if(user.contact.phone.isEmpty()){
                        errors["email"]="Remplissez le champ phone"
                    }
                    if(phoneExists.isPresent){
                        errors["usernameExists"]="le numero de telephone est deja pris"
                    }
                }
                "username"->{
                    if(usernameExists.isPresent && user.id==-1L){
                        errors["usernameExists"]="le username est deja pris"
                    }
                }

            }

            if(errors.isEmpty()){
                data = userRepository.save(user)
            }
        }

        return OperationResult(data,errors)
    }

    override fun authenticateUser(username: String, password: String): OperationResult<User> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: User? = null

        if (username.isEmpty() || password.isEmpty()) {
            errors["credentials"] = "Le nom d'utilisateur et le mot de passe sont obligatoires"
        }

        if (errors.isEmpty()) {
            val optionalUser = userRepository.findByUsername(username)
            val badCredentialsError = "Le nom d'utilisateur ou le mot de passe est incorrect"

            if (!optionalUser.isPresent) {
                errors["credentials"] = badCredentialsError
            } else {
                val user = optionalUser.get()
                if (!BCryptPasswordEncoder().matches(password, user.password)) {
                    errors["credentials"] = badCredentialsError
                } else {
                    data = user
                }
            }
        }

        return OperationResult(data, errors)
    }

    override fun findUserByIdAndUserType(user_type: String, id_user: Long): Optional<User> {
        return userRepository.findUserByIdAndUserType(user_type,id_user)
    }

    override fun findUserByIdUserTypeAndEmail(user_type: String, email: String): Optional<User> {
        return userRepository.findUserByEmailAndUserType(user_type,email)
    }

    override fun findUserByUserTypeAndUsername(user_type: String, email: String): Optional<User> {
        return userRepository.findUserByUsernameAndUserType(user_type,email)
    }



    override fun lock(username: String): OperationResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun unlock(username: String): OperationResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun forgotPassword(user: User): OperationResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun updatePassword(user: User, newPassword: String): OperationResult<Boolean> {
        TODO("Not yet implemented")
    }

    override fun saveUser(model: User): OperationResult<User> {
        val errors: MutableMap<String, String> = mutableMapOf()
        var data: User? = null

        if (model.contact.email.isEmpty()) {
            errors["email"] = "Le numéro est requis pour la création d'un utilisateur"
        }

        if (model.firstname.isNullOrEmpty()) {
            errors["firstname"] = "Le prénom est requis pour la création d'un utilisateur"
        }

        if (model.lastname.isNullOrEmpty()) {
            errors["firstname"] = "Le prénom est requis pour la création d'un utilisateur"
        }

        if (errors.isEmpty()) {
            model.enabled = true
            data = userRepository.save(model)
        }

        return OperationResult(data, errors)
    }

    override fun createOrUpdateUser(user: User): OperationResult<User> {
        return OperationResult(userRepository.save(user))
    }

    override fun countAllUserByUserType(user_type: String): Long {
        return userRepository.countAllUserByUserType(user_type)
    }

    override fun findUserTypeByUserId(id: Long): String {
        return userRepository.findTypeBy(id)
    }

    override fun findByContactEmailAndId(contact_email: String, id: Long): Optional<User> {
        return userRepository.findByContactEmailAndId(contact_email,id)
    }

    override fun findByContactPhoneAndId(contact_phone: String, id: Long): Optional<User> {
        return userRepository.findByContactPhoneAndId(contact_phone,id)
    }

    override fun findAllUserByUserType(user_type: String): MutableList<User> {
        return userRepository.findAllUserByUserType(user_type)
    }

    override fun findUserByContactEmailAndIdNot(contact_email: String, id: Long): Optional<User> {
        return userRepository.findByContactEmailAndIdNot(contact_email,id)
    }

    override fun findUserByContactPhoneAndIdNot(contact_phone: String, id: Long): Optional<User> {
        return userRepository.findByContactPhoneAndIdNot(contact_phone,id)
    }

    override fun findUserByUsernameAndIdNot(username: String, id: Long): Optional<User> {
        return userRepository.findByUsernameAndIdNot(username,id)
    }

    override fun findUserByUsernameAndId(username: String, id: Long): Optional<User> {
        return userRepository.findByUsernameAndId(username,id)
    }


    override fun saveUserMix(role: String, user: User): OperationResult<User> {

        val errors: MutableMap<String, String> = mutableMapOf()
        var data: User? = null

        if (user.contact.email.isEmpty()) {
            errors["email"] = "Le numéro est requis pour la création d'un utilisateur"
        }

        if (user.firstname.isNullOrEmpty()) {
            errors["firstname"] = "Le prénom est requis pour la création d'un utilisateur"
        }

        if (user.lastname.isNullOrEmpty()) {
            errors["firstname"] = "Le prénom est requis pour la création d'un utilisateur"
        }

        var operator = User()
        var manager = User()
        var contacts = User()


        if (errors.isEmpty()){
            when(role){

                "operator"->{
                    operator.firstname=user.firstname
                    operator.lastname=user.lastname
                    operator.address.primaryAddress=user.address.primaryAddress
                    operator.sex=user.sex
                    operator.birthdate=user.birthdate
                    operator.img=user.img
                    if(operator.id==-1L){
                        operator.username=user.username
                        operator.password=user.password
                        operator.contact.email=user.contact.email
                        operator.contact.phone=user.contact.phone
                        roleRepository.findByName(UserType.OPERATOR).ifPresent {
                            operator.roles = Collections.singleton(it)
                        }
                    }
                    data = userRepository.save(operator)
                }

            }
        }

        return  OperationResult(data, errors)
    }



    override fun findByUsername(username: String): Optional<User> = userRepository.findByUsername(username)


    override fun findByEmail(email: String): Optional<User> {
        return userRepository.findByContactEmail(email)
    }

    override fun findByPhone(phone: String): Optional<User> {
        return userRepository.findByContactPhone(phone)
    }

    override fun findUserById(id: Long): Optional<User> = userRepository.findById(id)

    override fun count(): Long = userRepository.count()


    override fun savePasswordReset(passwordReset: PasswordReset): OperationResult<PasswordReset> {
        return OperationResult(passwordResetRepository.save(passwordReset))
    }

    override fun findPasswordReset(id: Long): Optional<PasswordReset> {
        return passwordResetRepository.findById(id)
    }

    override fun findAllPasswordReset(): MutableList<PasswordReset> {
        return passwordResetRepository.findAll()
    }

    override fun findByEmailPasswordReset(email: String): Optional<PasswordReset> {
        return passwordResetRepository.findByUserContactEmail(email)
    }

    override fun findByToken(token: String): Optional<PasswordReset> {
        return passwordResetRepository.findByToken(token)
    }

    override fun findPRByIdAndToken(id: Long, token: String): Optional<PasswordReset> {
        return passwordResetRepository.findByIdAndToken(id,token)
    }




}