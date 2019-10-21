package com.sat.poc.patch.api;

public class PatchMap {
	private String fieldName;
	private String className;
	private String keys;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	@Override
	public String toString() {
		return "PatchMap [fieldName=" + fieldName + ", className=" + className + ", keys=" + keys + ", getFieldName()="
				+ getFieldName() + ", getClassName()=" + getClassName() + ", getKeys()=" + getKeys() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
