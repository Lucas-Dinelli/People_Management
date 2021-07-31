package com.management.peopleApi.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.management.peopleApi.model.PhoneType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
	
	@NotNull(message = "The type field must be entered with the following values: [HOME, MOBILE or COMMERCIAL]")
	@Enumerated(EnumType.STRING)
	private PhoneType type;
	
	@NotBlank(message = "The number field must be filled")
	@Size(min = 8, max = 15, message = "The number must be between 8 and 15 characters.")
	private String number;
}
