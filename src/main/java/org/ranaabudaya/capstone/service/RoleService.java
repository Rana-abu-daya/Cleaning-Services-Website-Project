package org.ranaabudaya.capstone.service;

import org.ranaabudaya.capstone.entity.Role;

import java.util.List;

/**
 * @author Igor Adulyan
 */
public interface RoleService {

    public void saveRole(Role role);
    public Role findRoleByRoleName(String name);
    public List<Role> getAllRoles();
    public List<Role> getRolesByUser(int id);
}