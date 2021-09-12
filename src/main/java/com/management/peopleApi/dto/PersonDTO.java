package com.management.peopleApi.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
	
	private Long id;
	
    @NotBlank(message = "The firstName field must be filled")
    @Size(min = 2, max = 100, message = "The firstName field must be between 2 and 100 characters")
    private String firstName;

    @NotBlank(message = "The lastName field must be filled")
    @Size(min = 2, max = 100, message = "The lastName field must be between 2 and 100 characters")
    private String lastName;
    
    @NotNull(message = "The cpf field must be filled and only with numbers")
    @CPF(message = "Invalid CPF")
    @Size(min = 11, max = 11, message = "Just 11 numbers")
    private String cpf;

    @NotNull(message = "The birthDate field must be entered in the following format: dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    
    @Valid
    private List<PhoneDTO> phones;
}
