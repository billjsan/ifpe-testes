import model.User;
import model.managers.UserManager;
import model.repo.UserRepo;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UsuarioTest {

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
    public void registrarUsuarioComInformacoesCorretas() {
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
        assert resultCode == UserManager.VALID_USER;
        userManager.saveUser();
        User user = userManager.getUser();
        assert user.getName().equals(NOME);
        assert user.getEmail().equals(EMAIL);
        assert user.getCpf().equals(CPF);
        assert user.getCellNumber().equals(TELEFONE);
        assert user.getBirthDate().equals(DATA_NASCIMENTO);
        assert user.getPassword().equals(SENHA);
        assert user.isResponsible() == MAIOR_DE_IDADE;
        userManager.deleteUser();
        assert UserRepo.INSTANCE.isDbEmpty();
    }

    @Test
    public void editarNome() {
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
        assert resultCode == UserManager.VALID_USER;
        userManager.saveUser();
        User user = userManager.getUser();
        assert user.getName().equals(NOME);
        assert user.getEmail().equals(EMAIL);
        assert user.getCpf().equals(CPF);
        assert user.getCellNumber().equals(TELEFONE);
        assert user.getBirthDate().equals(DATA_NASCIMENTO);
        assert user.getPassword().equals(SENHA);
        assert user.isResponsible() == MAIOR_DE_IDADE;
        String novoNome = "Novo nome";
        userManager.updateName(novoNome);
        userManager.updateUser();

        User userByCPF = UserRepo.INSTANCE.findUserByCPF(CPF);
        assert userByCPF.getName().equals(novoNome);

        userManager.deleteUser();
        assert UserRepo.INSTANCE.isDbEmpty();
    }

    @Test
    public void editarTelefone() {
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
        assert resultCode == UserManager.VALID_USER;
        userManager.saveUser();
        User user = userManager.getUser();
        assert user.getName().equals(NOME);
        assert user.getEmail().equals(EMAIL);
        assert user.getCpf().equals(CPF);
        assert user.getCellNumber().equals(TELEFONE);
        assert user.getBirthDate().equals(DATA_NASCIMENTO);
        assert user.getPassword().equals(SENHA);
        assert user.isResponsible() == MAIOR_DE_IDADE;
        String novoTelefone = "(81) 9999-8888";
        userManager.updateTelefone(novoTelefone);
        userManager.updateUser();

        User userByCPF = UserRepo.INSTANCE.findUserByCPF(CPF);
        assert userByCPF.getCellNumber().equals(novoTelefone);

        userManager.deleteUser();
        assert UserRepo.INSTANCE.isDbEmpty();
    }

    @Test
    public void registrarUsuarioComSenhaInvalida() {
        UserManager userManager = new UserManager();
        int resultCode = userManager.createNewUser(
                NOME,
                EMAIL,
                CPF,
                TELEFONE,
                "1234",
                DATA_NASCIMENTO,
                MAIOR_DE_IDADE
        );
        assert resultCode == UserManager.PASSWORD_LENGHT_INVALID;
        assert UserRepo.INSTANCE.isDbEmpty();
    }

    @Test
    public void registrarUsuarioComEmailInvalido() {
        UserManager userManager = new UserManager();
        int resultCode = userManager.createNewUser(
                NOME,
                "joao.silva.example.com",
                CPF,
                TELEFONE,
                SENHA,
                DATA_NASCIMENTO,
                MAIOR_DE_IDADE
        );
        assert resultCode == UserManager.USER_EMAIL_INVALID;
        assert UserRepo.INSTANCE.isDbEmpty();
    }

    @Test
    public void registrarUsuarioMenorDeIdade() {
        UserManager userManager = new UserManager();
        int resultCode = userManager.createNewUser(
                NOME,
                EMAIL,
                CPF,
                TELEFONE,
                SENHA,
                DATA_NASCIMENTO,
                false
        );
        assert resultCode == UserManager.USER_NOT_RESPONSIBLE;
        assert UserRepo.INSTANCE.isDbEmpty();
    }

    @Test
    public void registrarUsuarioComNomeVazio() {
        UserManager userManager = new UserManager();
        int resultCode = userManager.createNewUser(
                "",
                EMAIL,
                CPF,
                TELEFONE,
                SENHA,
                DATA_NASCIMENTO,
                false
        );
        assert resultCode == UserManager.USER_NAME_EMPTY;
        assert UserRepo.INSTANCE.isDbEmpty();
    }

    @Test
    public void registrarUsuarioComCPFJaUsadoEmOutroCadastro() {
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
        assert resultCode == UserManager.VALID_USER;
        userManager.saveUser();
        User user = userManager.getUser();
        assert user.getName().equals(NOME);
        assert user.getEmail().equals(EMAIL);
        assert user.getCpf().equals(CPF);
        assert user.getCellNumber().equals(TELEFONE);
        assert user.getBirthDate().equals(DATA_NASCIMENTO);
        assert user.getPassword().equals(SENHA);
        assert user.isResponsible() == MAIOR_DE_IDADE;

        UserManager userManager2 = new UserManager();
        int resultCode2 = userManager2.createNewUser(
                "novo nome",
                "novo@email.com",
                CPF,
                "819999999",
                "123456789",
                DATA_NASCIMENTO,
                true
        );
        assert resultCode2 == UserManager.CPF_ALREADY_USED;
        userManager.deleteUser();
        assert UserRepo.INSTANCE.isDbEmpty();
    }
}