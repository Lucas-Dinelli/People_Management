package com.management.peopleApi.utils;

import com.management.peopleApi.dto.PhoneDTO;
import com.management.peopleApi.model.Phone;
import com.management.peopleApi.model.PhoneType;

public class PhoneUtils {
	
	private static final long PHONE_ID = 1L;
	private static final String PHONE_NUMBER = "(21) 98379-3635";
	private static final PhoneType PHONE_TYPE = PhoneType.MOBILE;
	
	public static PhoneDTO createFakeDTO() {
        return PhoneDTO.builder()
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }

    public static Phone createFakeModel() {
        return Phone.builder()
                .id(PHONE_ID)
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }
}
