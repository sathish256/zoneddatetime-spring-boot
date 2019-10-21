package com.sat.poc.datetime.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/v1/date")
public class DateController {

	@PostMapping
	public ResponseEntity<DatePojo> save(@RequestBody DatePojo datePojo) {
		System.out.println("Request received");
		try {
		printPojo(datePojo);
		}catch(Exception ex) {
			ex.printStackTrace();
		}

		return ResponseEntity.ok(datePojo);
	}

	private void printPojo(DatePojo datePojo) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();
		System.out.println(objMapper.writeValueAsString(datePojo));

	}

}
