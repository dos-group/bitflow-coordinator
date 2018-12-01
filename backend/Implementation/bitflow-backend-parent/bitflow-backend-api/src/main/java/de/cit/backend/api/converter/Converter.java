package de.cit.backend.api.converter;

public interface Converter<T, U> {

	public static final String ID_KEY = "ID";
	
	T convertToBackend(U instanceToConvert);
	
	U convertToFrontend(T instanceToConvert);
}
