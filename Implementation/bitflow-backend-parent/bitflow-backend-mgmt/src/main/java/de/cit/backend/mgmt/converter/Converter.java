package de.cit.backend.mgmt.converter;

import java.util.ArrayList;
import java.util.List;

public abstract class Converter<T, U> {

	abstract T convertToBackend(U instanceToConvert);
	
	abstract U convertToFrontend(T instanceToConvert);
	
	public List<U> convertListToFrontend(List<T> list){
		List<U> outList = new ArrayList<>();
		for(T t : list){
			outList.add(convertToFrontend(t));
		}
		
		return outList;
	}
	
	public List<T> convertListToBackend(List<U> list){
		List<T> outList = new ArrayList<>();
		for(U u : list){
			outList.add(convertToBackend(u));
		}
		
		return outList;
	}
}
