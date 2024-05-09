package com.savants.Pokemon.Services;

import com.savants.Pokemon.DTOs.LoginRequest;
import com.savants.Pokemon.DTOs.RegisterRequest;
import com.savants.Pokemon.DTOs.TokenRequest;
import com.savants.Pokemon.JWT.JwtService;
import com.savants.Pokemon.Models.Carrito;
import com.savants.Pokemon.Models.Role;
import com.savants.Pokemon.Models.User;
import com.savants.Pokemon.Repositories.RoleRepository;
import com.savants.Pokemon.Repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CarritoService carritoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(this::convertToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre de usuario: " + username));
    }

    public List<Role> getAllRolesByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el nombre de usuario: " + username));
        return user.getRoles();
    }

    public TokenRequest login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails = loadUserByUsername(request.getUsername());
        String token = jwtService.getToken(userDetails, getAllRolesByUsername(request.getUsername()));
        return TokenRequest.builder()
                .token(token)
                .build();
    }


    public TokenRequest register(RegisterRequest request) {
        // Verificar si el nombre de usuario ya está en uso
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        // Verificar si el correo electrónico ya está en uso
        if (userRepository.existsByUserMail(request.getUseremail())) {
            throw new IllegalArgumentException("El correo electrónico ya está en uso");
        }

        // Construir un objeto User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserMail(request.getUseremail());
        user.setNombreEntrenador(request.getNombreEntrenador());

        Role userRole = roleRepository.findByRole("ROLE_BASIC");
        if (userRole == null) {
            throw new IllegalArgumentException("Rol 'ROLE_BASIC' no encontrado");
        }

        // Agregar el rol al usuario
        user.getRoles().add(userRole);

        // Crear un carrito para el usuario
        Carrito carrito = new Carrito();
        carritoService.guardarCarrito(carrito);

        // Asignar un carrito al usuario
         user.setCarrito(carrito);

        // Guardar el usuario en el repositorio
        userRepository.save(user);

        // Convertir el objeto User a UserDetails
        UserDetails userDetails = convertToUserDetails(user);

        // Generar el token JWT utilizando el objeto UserDetails
        String token = jwtService.getToken(userDetails, getAllRolesByUsername(request.getUsername()));

        // Construir y devolver la respuesta del token
        return TokenRequest.builder()
                .token(token)
                .build();
    }

    public List<Role> getRolesByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el nombre de usuario: " + username));
        return user.getRoles();
    }

    private UserDetails convertToUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())

                .build();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}