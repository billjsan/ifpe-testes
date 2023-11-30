package model

import model.repo.RepoHelper
import java.time.LocalDate

data class User(
    var name: String,
    var email: String,
    var password: String,
    var cpf: String,
    var cellNumber: String,
    var birthDate: LocalDate,
    var isResponsible: Boolean
) {
    val id: Long = RepoHelper.getNewID()
}