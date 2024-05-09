package com.savants.Pokemon.Seeder;

import com.savants.Pokemon.Models.Carrito;
import com.savants.Pokemon.Models.Role;
import com.savants.Pokemon.Models.User;
import com.savants.Pokemon.Services.CarritoService;
import com.savants.Pokemon.Services.RoleService;
import com.savants.Pokemon.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeederUserYRole {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CarritoService carritoService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SeederUserYRole(){}

    @Bean
    boolean seedInitialRoles(){
        if (roleService.findAll().isEmpty()) {
            Role rol1 = new Role("ROLE_BASIC");
            Role rol2 = new Role("ROLE_MANAGER");
            Role rol3 = new Role("ROLE_ADMIN");
            roleService.crearRole(rol1);
            roleService.crearRole(rol2);
            roleService.crearRole(rol3);
        }
        return true;
    }

    @Bean
    boolean seedInitialUsers() {
        if (userService.findAll().isEmpty()) {
            //Crear usuarios
            User user1 = new User("basic@gmail.com", "basic", "Azul", passwordEncoder.encode("1234"));
            User user2 = new User("manager@gmail.com", "manager", "verde", passwordEncoder.encode("1234"));
            User user3 = new User("admin@gmail.com", "admin", "rojo", passwordEncoder.encode("1234"));

            //Buscar el rol de cada usuario
            Role role1 = roleService.obtenerRolePorNombre("ROLE_BASIC");
            Role role2 = roleService.obtenerRolePorNombre("ROLE_MANAGER");
            Role role3 = roleService.obtenerRolePorNombre("ROLE_ADMIN");

            //Añadir rol a cada usuario
            user1.getRoles().add(role1);
            user2.getRoles().add(role2);
            user3.getRoles().add(role3);

            //Crear un carrito para cada usuario
            Carrito carrito1 = new Carrito();
            Carrito carrito2 = new Carrito();
            Carrito carrito3 = new Carrito();

            //Guardar cada carrito en la base de datos
            carritoService.guardarCarrito(carrito1);
            carritoService.guardarCarrito(carrito2);
            carritoService.guardarCarrito(carrito3);

            //Añadir el carrito a cada usuario
            user1.setCarrito(carrito1);
            user2.setCarrito(carrito2);
            user3.setCarrito(carrito3);

            //Guardar los usuarios
            userService.save(user1);
            userService.save(user2);
            userService.save(user3);
        }
        return true;
    }
}
