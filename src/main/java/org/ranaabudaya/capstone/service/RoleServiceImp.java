package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.entity.Role;
import org.ranaabudaya.capstone.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role findRoleByRoleName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public List<Role> getRolesByUser(int id) {
        return roleRepository.findRoleByUser(id);
    }
}
