import model.User
import model.UserRepo
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class UserTest {

    @Test
    fun registerUserTest() {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val date = LocalDate.parse("20-08-1993", formatter)

        val user = User("Nome",
            "email",
            "password",
            "cpf",
            "cell number",
            date,
            true)

        UserRepo.saveUser(user)
    }

}