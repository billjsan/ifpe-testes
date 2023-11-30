package model.managers;

import model.User;
import model.repo.UserRepo;

import java.time.LocalDate;

public class UserManager {

    public static final int VALID_USER = 0;
    public static final int USER_NAME_EMPTY = 1;
    public static final int USER_CPF_EMPTY = 2;
    public static final int USER_TELEPHONE_EMPTY = 3;
    public static final int USER_PASSWORD_EMPTY = 4;
    public static final int USER_BIRTHDATE_EMPTY = 5;
    public static final int USER_EMAIL_EMPTY = 6;
    public static final int CPF_ALREADY_USED = 7;
    public static final int PASSWORD_LENGHT_INVALID = 8;
    public static final int USER_EMAIL_INVALID = 9;
    public static final int USER_NOT_RESPONSIBLE = 10;

    private User user;

    public int createNewUser(String nome,
                             String email,
                             String cpf,
                             String telefone,
                             String senha,
                             LocalDate dataNascimento,
                             boolean maiorDeIdade) {
        User newUser = new User(
                nome,
                email,
                senha,
                cpf,
                telefone,
                dataNascimento,
                maiorDeIdade);

        int validationCode = getUserValidationCode(newUser);
        if (validationCode == VALID_USER) {
            user = newUser;
            return VALID_USER;
        } else {
            return validationCode;
        }
    }

    private int getUserValidationCode(User user) {
        if (isNullOrEmpty(user.getName())) {
            return USER_NAME_EMPTY;
        }
        if (isNullOrEmpty(user.getCpf())) {
            return USER_CPF_EMPTY;
        }
        if (isNullOrEmpty(user.getCellNumber())) {
            return USER_TELEPHONE_EMPTY;
        }
        if (isNullOrEmpty(user.getPassword())) {
            return USER_PASSWORD_EMPTY;
        }
        if (user.getBirthDate() == null) {
            return USER_BIRTHDATE_EMPTY;
        }
        if (isNullOrEmpty(user.getEmail())) {
            return USER_EMAIL_EMPTY;
        }
        User userByCpf = UserRepo.INSTANCE.findUserByCPF(user.getCpf());
        if (userByCpf != null) {
            return CPF_ALREADY_USED;
        }
        if (user.getPassword().length() < 8) {
            return PASSWORD_LENGHT_INVALID;
        }
        if (!isValidEmail(user.getEmail())) {
            return USER_EMAIL_INVALID;
        }
        if (!user.isResponsible()) {
            return USER_NOT_RESPONSIBLE;
        }

        return VALID_USER;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        String domain = parts[1];
        return domain.contains(".") && domain.indexOf(".") > domain.indexOf("@");
    }

    public void saveUser() {
        UserRepo.INSTANCE.saveUser(user);
    }

    public User getUser() {
        return user;
    }

    public void deleteUser() {
        UserRepo.INSTANCE.deleteUser(user);
    }
}