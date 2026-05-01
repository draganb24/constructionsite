package com.example.constructionsite.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

  @NotBlank(message = "Ime i prezime su obavezni")
  @Size(min = 6, max = 30, message = "Ime i prezime moraju da sadrze izmedju 6 i 30 karaktera")
  private String fullName;

  @NotBlank(message = "Email je obavezan")
  @Email(message = "Email mora biti ispravan")
  private String email;

  @NotBlank(message = "Lozinka je obavezna")
  @Size(min = 6, message = "Lozinka mora imati barem 6 karaktera")
  private String password;
}
