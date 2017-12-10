package de.cit.backend.api.converter;

public interface Converter<T, U> {

	T convertToBackend(U instanceToConvert);
	
	U convertToFrontend(T instanceToConvert);
}
