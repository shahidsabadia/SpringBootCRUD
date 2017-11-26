package com.ss.project.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean b) {
		if (b == null) {
			return null;
		}
		/*if (b.booleanValue()) {
			return "TRUE";
		}*/
		
		return String.valueOf(b.booleanValue());
	}

	@Override
	public Boolean convertToEntityAttribute(String s) {
		if (s == null) {
			return null;
		}
		/*if ("TRUE".equalsIgnoreCase(s)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;*/
		return Boolean.valueOf(s);
	}

}
