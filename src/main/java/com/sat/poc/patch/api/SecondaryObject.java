package com.sat.poc.patch.api;

import java.util.List;

public class SecondaryObject {
	
	private String secondaryName;
	
	private String secondaryId;
	
	private ThirdLevelObject thirdLevelObject;
	
	private List<ThirdLevelObject> thirdLevelObjectArray;

	public String getSecondaryName() {
		return secondaryName;
	}

	public void setSecondaryName(String secondaryName) {
		this.secondaryName = secondaryName;
	}

	public String getSecondaryId() {
		return secondaryId;
	}

	public void setSecondaryId(String secondaryId) {
		this.secondaryId = secondaryId;
	}

	public ThirdLevelObject getThirdLevelObject() {
		return thirdLevelObject;
	}

	public void setThirdLevelObject(ThirdLevelObject thirdLevelObject) {
		this.thirdLevelObject = thirdLevelObject;
	}

	public List<ThirdLevelObject> getThirdLevelObjectArray() {
		return thirdLevelObjectArray;
	}

	public void setThirdLevelObjectArray(List<ThirdLevelObject> thirdLevelObjectArray) {
		this.thirdLevelObjectArray = thirdLevelObjectArray;
	}
	
	

}
