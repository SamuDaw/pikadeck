package com.savants.Pokemon.Services;

import com.savants.Pokemon.Models.Role;
import com.savants.Pokemon.Models.User;
import com.savants.Pokemon.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    private RoleService(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }
    public List<Role> obtenerRoles(){
        return roleRepository.findAll();
    }
    public Role obtenerRolePorId(Long id){
        return roleRepository.findById(id).orElse(null);
    }
    public void crearRole(Role role){
        roleRepository.save(role);
    }
    public Role obtenerRolePorNombre(String role){ return roleRepository.findByRole(role); }
    public Role actualizarRole(Role role, Long id)
    {
        Optional<Role> existingRoleOptional = roleRepository.findById(id);
        if (existingRoleOptional.isPresent()) {
            Role existingRole = existingRoleOptional.get();
            existingRole.setRole(role.getRole());
            return roleRepository.save(existingRole);
        } else {
            return null;
        }
    }
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public boolean eliminarRole(Long id) {
        Optional<Role> existingRoleOptional = roleRepository.findById(id);
        if (existingRoleOptional.isPresent()) {
            roleRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
