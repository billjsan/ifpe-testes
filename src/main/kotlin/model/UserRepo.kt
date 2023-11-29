package model

object UserRepo {
    private var users: MutableList<User> = mutableListOf()

    fun saveUser(user: User) {
        users.add(user)
    }

    fun deleteUser(user: User) {
        users.remove(user)
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
}