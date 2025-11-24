package com.puc.realconsult.controller;

import com.puc.realconsult.dto.ChangePasswordDTO;
import com.puc.realconsult.dto.UserDTO;
import com.puc.realconsult.model.realConsult.UserModel;
import com.puc.realconsult.repository.realConsult.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails principal) {
        UserModel u = userRepository.findByEmail(principal.getUsername());
        if (u == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(toDTO(u));
    }

    @PatchMapping("/me")
    public ResponseEntity<?> patchMe(
            @AuthenticationPrincipal UserDetails principal,
            @RequestBody Map<String, Object> payload) {

        UserModel u = userRepository.findByEmail(principal.getUsername());
        if (u == null) return ResponseEntity.status(401).build();

        if (payload.containsKey("avatarColor")) {
            u.setAvatarColor(String.valueOf(payload.get("avatarColor")));
        }
        userRepository.save(u);
        return ResponseEntity.ok(toDTO(u));
    }

    @PostMapping("/me/password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal UserDetails principal,
            @RequestBody ChangePasswordDTO body
    ) {
        UserModel user = userRepository.findByEmail(principal.getUsername());
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuário não encontrado."));
        }

        if (!passwordEncoder.matches(body.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Senha atual incorreta."));
        }

        if (!body.getNewPassword().equals(body.getConfirmNewPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "A confirmação da nova senha não confere."));
        }

        if (body.getNewPassword().length() < 8) {
            return ResponseEntity.badRequest().body(Map.of("error", "A nova senha deve ter pelo menos 8 caracteres."));
        }

        user.setSenha(passwordEncoder.encode(body.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Senha alterada com sucesso!"));
    }

    private UserDTO toDTO(UserModel u) {
        return new UserDTO(
                u.getId(),
                u.getNome(),
                u.getCargo(),
                u.getEmail(),
                u.getStatus(),
                u.getAvatarColor()
        );
    }
}
