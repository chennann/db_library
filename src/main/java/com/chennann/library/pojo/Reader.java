package com.chennann.library.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reader {

    Integer readerId;
    @NotEmpty
    String name;
    @NotEmpty
    @Pattern(regexp = "^\\d{11}$", message = "手机号码必须是11位数字")
    String phone;
    @NotEmpty
    @Email
    String email;
//    @JsonIgnore
    String password;
}
