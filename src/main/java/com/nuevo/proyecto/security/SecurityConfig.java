package com.nuevo.proyecto.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nuevo.proyecto.model.Usuario;
import com.nuevo.proyecto.service.UsuarioService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;

import java.util.Arrays;
import java.util.Optional;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint; 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para facilitar pruebas  
            .cors(Customizer.withDefaults()) // Habilitar CORS con configuración personalizada
            .authorizeHttpRequests(auth -> auth
                // Seguridad endpoint Microservicio Usuarios y login
                .requestMatchers(HttpMethod.GET, "/api/usuarios").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/usuarios/{id}").authenticated()  
                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll() 
                .requestMatchers(HttpMethod.PUT, "/api/usuarios/{id}").authenticated()  
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/{id}").hasRole("admin") 
                .requestMatchers(HttpMethod.POST, "/api/login/**").permitAll()
                .requestMatchers("/api/usuarios/nombre/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/Productos/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/Productos/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/Productos/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/Productos/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/compras").authenticated() 
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(customAuthenticationEntryPoint) // Usar manejador personalizado para 401 Unauthorized
            )        
            .httpBasic(Customizer.withDefaults());  
    
        return http.build();
    }

    // Autenticación de usuarios
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userDetailsService());

        return authenticationManagerBuilder.build();
    }

    // Recuperar información de usuario basado en el nombre
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<Usuario> usuarioOptional = usuarioService.obtenerUsuarioPorNombre(username);
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                return new org.springframework.security.core.userdetails.User(
                    usuario.getNombre(),
                    "{noop}" + usuario.getPassword(), // Indica que la contraseña no está codificada
                    AuthorityUtils.createAuthorityList("ROLE_" + usuario.getRol())
                );
            } else {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
        };
    }

    // Configuración de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Permitir el origen de la app Angular
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization")); // Encabezados permitidos
        configuration.setAllowCredentials(true); // Permitir credenciales
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplicar la configuración a todos los endpoints
        return source;
    }
}