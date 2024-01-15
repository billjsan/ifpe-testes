package model.managers;

import model.User;
import model.repo.UserRepo;

public class LoginManager {

    public static final int VALID_INFO = 0;
    public static final int USER_LOGGED_IN = 10;
    public static final int USER_NOT_LOGGED_IN = 20;
    public static final int PASSWORD_INCORRECT = 100;
    public static final int MISSING_PASSWORD_INFO = 1000;
    public static final int MISSING_LOGIN_INFO = 10000;
    public static final int MISSING_EMAIL_INFO = 100000;
    public static final int USER_NOT_FOUND = 200000;

    private int mSessionStatus = USER_NOT_LOGGED_IN;

    public int getLoginValidationCode(String email, String senha) {
        if (email.isEmpty() && senha.isEmpty()) {
            return MISSING_LOGIN_INFO;
        } else if (email.isEmpty()) {
            return MISSING_EMAIL_INFO;
        } else if (senha.isEmpty()) {
            return MISSING_PASSWORD_INFO;
        }
        User user = UserRepo.INSTANCE.findUserByEmail(email);
        if (user == null) return USER_NOT_FOUND;
        if (user.getPassword().equals(senha)) {
            mSessionStatus = USER_LOGGED_IN;
            return VALID_INFO;
        } else {
            return PASSWORD_INCORRECT;
        }
    }

    public void logOff() {
        mSessionStatus = USER_NOT_LOGGED_IN;
    }

    public int getSessionStatus() {
        return mSessionStatus;
    }
}