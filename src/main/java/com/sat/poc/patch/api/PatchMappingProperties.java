package com.sat.poc.patch.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:patch-mapping.json")
@ConfigurationProperties
public class PatchMappingProperties {

	private String description;
	private List<PatchMap> mapping = new ArrayList<>();

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<PatchMap> getMapping() {
		return mapping;
	}

	public void setMapping(List<PatchMap> mapping) {
		this.mapping = mapping;
	}

	@Override
	public String toString() {
		return "PatchMappingProperties [description=" + description + ", mapping=" + mapping + ", getDescription()="
				+ getDescription() + ", getMapping()=" + getMapping() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
