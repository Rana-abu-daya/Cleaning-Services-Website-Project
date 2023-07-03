package org.ranaabudaya.capstone.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {
    @NotNull
    private String fullname;
    @NotNull
    private String email;
    @NotNull
    private String message;
}
