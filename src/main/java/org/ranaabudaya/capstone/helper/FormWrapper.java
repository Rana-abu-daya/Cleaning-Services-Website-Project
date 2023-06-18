package org.ranaabudaya.capstone.helper;

import jakarta.validation.Valid;
import org.ranaabudaya.capstone.dto.CleanerDTO;
import org.ranaabudaya.capstone.dto.UserDTO;

public class FormWrapper {
    @Valid
    private UserDTO userDTO;
    @Valid
    private CleanerDTO cleanerDTO;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void setCleanerDTO(CleanerDTO cleanerDTO) {
        this.cleanerDTO = cleanerDTO;
    }

    public CleanerDTO getCleanerDTO() {
        return cleanerDTO;
    }
}