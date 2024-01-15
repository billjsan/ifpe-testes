import model.managers.LoginManager;
import model.managers.UserManager;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoginTest {

    private final String NOME = "João da Silva";
    private final String NASCIMENTO = "25-02-1995";
    private final String EMAIL = "joao.silva@example.com";
    private final String CPF = "123.456.789-09";
    private final String TELEFONE = " (11)98765-4321";
    private final String SENHA = "Senha123";
    private final boolean MAIOR_DE_IDADE = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final LocalDate DATA_NASCIMENTO = LocalDate.parse(NASCIMENTO, formatter);

    @Test
    public void realizarLoginComInformacoesCorretas() {
        UserManager userManager = new UserManager();
        int resultCode = userManager.createNewUser(
                NOME,
                EMAIL,
                CPF,
                TELEFONE,
                SENHA,
                DATA_NASCIMENTO,
                MAIOR_DE_IDADE
        );
        if (resultCode == UserManager.VALID_USER) {
            userManager.saveUser();
            LoginManager lm = new LoginManager();
            int loginResultCode = lm.getLoginValidationCode(EMAIL, SENHA);
            assert loginResultCode == LoginManager.VALID_INFO;
            assert lm.getSessionStatus() == LoginManager.USER_LOGGED_IN;
            userManager.deleteUser();
        }
    }

    @Test
    public void realizarLoginComSenhaIncorreta() {
        UserManager userManager = new UserManager();
        int resultCode = userManager.createNewUser(
                NOME,
                EMAIL,
                CPF,
                TELEFONE,
                SENHA,
                DATA_NASCIMENTO,
                MAIOR_DE_IDADE
        );
        if (resultCode == UserManager.VALID_USER) {
            userManager.saveUser();
            LoginManager lm = new LoginManager();
            int loginResultCode = lm.getLoginValidationCode(EMAIL, "senha errada");
            assert loginResultCode == LoginManager.PASSWORD_INCORRECT;
            userManager.deleteUser();
        }
    }

    @Test
    public void realizarLoginComCampoDeEmailVazio() {
        LoginManager lm = new LoginManager();
        int loginResultCode = lm.getLoginValidationCode("", SENHA);
        assert loginResultCode == LoginManager.MISSING_EMAIL_INFO;
    }

    @Test
    public void realizarLoginComCampoDeSenhaVazio() {
        LoginManager lm = new LoginManager();
        int loginResultCode = lm.getLoginValidationCode(EMAIL, "");
        assert loginResultCode == LoginManager.MISSING_PASSWORD_INFO;
    }

    @Test
    public void realizarLoginComCamposVazios() {
        LoginManager lm = new LoginManager();
        int loginResultCode = lm.getLoginValidationCode("", "");
        assert loginResultCode == LoginManager.MISSING_LOGIN_INFO;
    }
}