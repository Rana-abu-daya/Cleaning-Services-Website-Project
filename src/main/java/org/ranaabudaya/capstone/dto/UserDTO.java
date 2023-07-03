package org.ranaabudaya.capstone.dto;

import lombok.AllArgsConstructor;
import org.ranaabudaya.capstone.validation.FieldMatch;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch.List( { @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")})
public class UserDTO {
    private int id ;
    @NotEmpty
    private String userName;
    @NotEmpty(message = "Required")
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String firstName;

    @NotEmpty(message = "Required")
    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    private String lastName;
    @Email(message = "Invalid email format. Please provide a valid email address.",regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Required")
    private String email;
    @NotEmpty(message = "Required")
    @Pattern(regexp = "^\\+?[0-9\\s]+$", message = "Invalid phone number format. Please provide a valid phone number.")
    private String phone;
    @NotEmpty(message = "Required")
    private String address;
    @NotEmpty(message = "Required")
    private String city;
    @NotEmpty(message = "Required")
    private String state;
    @NotEmpty(message = "Required")
    @Pattern(regexp = "[0-9]{5}$", message = "Zip code wrong format")
    private String zipCode;

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", password='" + password + '\'' +
                ", matchingPassword='" + matchingPassword + '\'' +
                '}';
    }

    @NotEmpty(message = "Required")
    private String password;


    private String matchingPassword;

    public UserDTO(@NotEmpty String userName, @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed") String firstName, @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed") String lastName, @Email String email, String phone, @Pattern(regexp = "[0-9]{5}$", message = "Zip code wrong format") String zip, @NotEmpty(message = "Required") String password, @NotEmpty(message = "Required") String matchingPassword, String address
    , String city, String state) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.zipCode = zip;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.address = address;
        this.city = city;
        this.state=state;
    }
    private String photo;
    private MultipartFile file;
    private String roleName;
}
