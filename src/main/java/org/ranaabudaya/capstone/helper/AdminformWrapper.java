package org.ranaabudaya.capstone.helper;

import jakarta.validation.Valid;
import org.ranaabudaya.capstone.dto.AdminDTO;
import org.ranaabudaya.capstone.dto.UserDTO;

public class AdminformWrapper {
    @Valid
    private UserDTO userDTO;
    @Valid
    private AdminDTO adminDTO;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public AdminDTO getAdminDTO() {
        return adminDTO;
    }

    public void setAdminDTO(AdminDTO adminDTO) {
        this.adminDTO = adminDTO;
    }
}
