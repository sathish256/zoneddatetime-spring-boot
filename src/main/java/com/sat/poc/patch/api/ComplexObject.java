package com.sat.poc.patch.api;

import java.time.ZonedDateTime;
import java.util.List;

public class ComplexObject {
	
	private String name;
	
	private Integer id;
	
	private Long someValue;
	
	private ZonedDateTime createAt;
	 
	private SecondaryObject secondObject;
	
	private List<SecondaryObject> secondaryObjectArray;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getSomeValue() {
		return someValue;
	}

	public void setSomeValue(Long someValue) {
		this.someValue = someValue;
	}

	public ZonedDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(ZonedDateTime createAt) {
		this.createAt = createAt;
	}

	public SecondaryObject getSecondObject() {
		return secondObject;
	}

	public void setSecondObject(SecondaryObject secondObject) {
		this.secondObject = secondObject;
	}

	public List<SecondaryObject> getSecondaryObjectArray() {
		return secondaryObjectArray;
	}

	public void setSecondaryObjectArray(List<SecondaryObject> secondaryObjectArray) {
		this.secondaryObjectArray = secondaryObjectArray;
	}

}
