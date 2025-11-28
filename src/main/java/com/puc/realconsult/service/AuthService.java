package com.puc.realconsult.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.puc.realconsult.config.TokenService;
import com.puc.realconsult.dto.AutenticarDTO;
import com.puc.realconsult.dto.EsqueceuSenhaRequest;
import com.puc.realconsult.dto.RedefinirSenhaRequest;
import com.puc.realconsult.dto.loginUsuarioDTO;
import com.puc.realconsult.model.realConsult.ResetSenhaToken;
import com.puc.realconsult.model.realConsult.UserModel;
import com.puc.realconsult.repository.realConsult.ResetSenhaTokenRepository;
import com.puc.realconsult.repository.realConsult.UserRepository;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ResetSenhaTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public AuthLoginResult login(AutenticarDTO data, AuthenticationManager authManager) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());

        var auth = authManager.authenticate(usernamePassword);
        var usuario = (UserModel) auth.getPrincipal();

        String token = tokenService.gerarToken(usuario);

        var userDTO = new loginUsuarioDTO(usuario.getId(), token, usuario.getId(), usuario.getNome(), usuario.getAvatarColor(), usuario.getCargo());

        return new AuthLoginResult(token, userDTO);
    }

    public String resetPassword(Long userId) {
        UserModel usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setSenha(passwordEncoder.encode("123456"));
        userRepository.save(usuario);

        return "Senha redefinida com sucesso!";
    }

    public void forgotPassword(EsqueceuSenhaRequest request) throws Exception {

        UserModel usuario = userRepository.findByEmail(request.getEmail());

        if (usuario == null) {
            throw new Exception("E-mail não encontrado.");
        }


        String token = UUID.randomUUID().toString();


        ResetSenhaToken resetToken = new ResetSenhaToken(token, usuario);
        tokenRepository.save(resetToken);


        String link = System.getProperty("spring.web.cors.allowed-origins") + "/redefinicao-senha?token=" + token;


        emailService.send(
                usuario.getEmail(),
                "Redefinição de Senha - REAL Consult",
                "Olá " + usuario.getNome() + ",\n\n" +
                        "Clique no link abaixo para redefinir sua senha:\n" +
                        link + "\n\n" +
                        "Este link expira em 15 minutos."
        );
    }

    public record AuthLoginResult(String token, loginUsuarioDTO user) {}

}
