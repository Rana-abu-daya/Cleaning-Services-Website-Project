package org.ranaabudaya.capstone.helper;

import jakarta.validation.Valid;
import org.ranaabudaya.capstone.dto.CustomerDTO;
import org.ranaabudaya.capstone.dto.UserDTO;

public class CustomerformWrapper {
    @Valid
    private UserDTO userDTO;
    @Valid
    private CustomerDTO customerDTO;

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }


}
