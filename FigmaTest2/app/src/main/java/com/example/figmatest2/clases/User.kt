package com.example.figmatest1.clases

const val PHONE_LENGTH = 10
var match = false
val datos = Data()

class User (
    private val name: String,
    private var age: Int,
    private val password: String,
    private var email: String) {

    private var phoneNumber: Long = 0

        set(value){ //Setter para guardar un valor solo si es de 10 dígitos
            if(value.toString().length == PHONE_LENGTH){
                field = value
            } else {
                println("El número que ingresaste es incorrecto")
            }
        }

    companion object {
        @JvmStatic public fun main(vararg input: String) {
            val me = User("William", 2, "1234", "wlimon@cituspower.com")
            me.updateProfile(6861908823, "gmlimon")
            println(me.email)
        }
    }

    fun updateProfile(phoneNumber: Long, email: String){
        this.phoneNumber = phoneNumber
        this.email = email
    }

    fun login(user: String, password: String): Boolean {

        fun validate(input: String) = input.isNotEmpty()

        val userValidated = validate(user)
        val passwordValidated = validate(password)

        for ((key, value) in datos.loginData){
            //println("The password is $key and the user is $value")
            if (key == password && value == user) match = true else match = false
            if (match == true) break
        }

        return userValidated && passwordValidated && match
    }

    fun register(user: String, password: String): Boolean {
        fun validate(input: String) = input.isNotEmpty()

        val userValidated = validate(user)
        val passwordValidated = validate(password)

        return userValidated && passwordValidated
    }
}