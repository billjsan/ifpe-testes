package model.repo

import model.User

object UserRepo {
    private var users: MutableList<User> = mutableListOf()

    fun saveUser(user: User) {
        users.add(user)
    }

    fun deleteUser(user: User) {
        users.remove(user)
    }

    fun isDbEmpty(): Boolean {
        return users.isEmpty();
    }

    fun updateUser(user: User) {
        for (u in users) {
            if (u.id == user.id) {
                u.birthDate = user.birthDate
                u.cpf = user.cpf
                u.cellNumber = user.cellNumber
                u.email = user.email
                u.name = user.name
                u.isResponsible = user.isResponsible
                u.password = user.password
            }
        }
    }

    fun findUserByCPF(cpf: String): User? {
        for (u: User in users) {
            if (u.cpf.equals(cpf))
                return u
        }
        return null
    }
}