package com.example.springboot.taskmanagementapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@Tag(name = "Autenticación", description = "Endpoints para autenticación de usuarios")
public class AuthController {

    @Operation(
        summary = "Autenticar usuario",
        description = "Autentica un usuario y retorna un token JWT"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Autenticación exitosa",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = LoginResponse.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Credenciales inválidas"
        )
    })
    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequest loginRequest) {
        // Este método no necesita implementación ya que el JwtAuthenticationFilter
        // se encarga del proceso de autenticación
    }

    public static class LoginRequest {
        @Schema(description = "Nombre de usuario", example = "Craig Fernandez")
        @NotBlank(message = "El nombre de usuario es requerido")
        private String nombreUsuario;

        @Schema(description = "Contraseña del usuario", example = "12345678")
        @NotBlank(message = "La contraseña es requerida")
        private String clave;

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }

        public String getClave() {
            return clave;
        }

        public void setClave(String clave) {
            this.clave = clave;
        }
    }

    public static class LoginResponse {
        @Schema(description = "Token JWT para autenticación", 
               example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        private String token;

        @Schema(description = "Nombre del usuario autenticado", 
               example = "Craig Fernandez")
        private String username;

        @Schema(description = "Mensaje de confirmación", 
               example = "Hola Craig Fernandez, has iniciado sesión con éxito")
        private String mensaje;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }
} 