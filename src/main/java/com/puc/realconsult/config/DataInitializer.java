package com.puc.realconsult.config;

import com.puc.realconsult.utils.StatusUsuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.puc.realconsult.model.realConsult.UserModel;
import com.puc.realconsult.repository.realConsult.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(Ordered.LOWEST_PRECEDENCE)
public class DataInitializer implements CommandLineRunner {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // verifica se já existe algum usuário com cargo "Administrador" para criar um padrão
        boolean existeAdmin = repository.existsByCargo("Administrador");

        if (!existeAdmin) {
            UserModel usuario1 = new UserModel();
            usuario1.setNome("Teste Administrador");
            usuario1.setCargo("Administrador");
            usuario1.setEmail("adm@vtreal.com.br");
            usuario1.setStatus(StatusUsuario.ATIVO);
            usuario1.setAvatarColor("#4016cc");
            usuario1.setSenha(passwordEncoder.encode("123456"));
            repository.save(usuario1);
        }
    }
}
