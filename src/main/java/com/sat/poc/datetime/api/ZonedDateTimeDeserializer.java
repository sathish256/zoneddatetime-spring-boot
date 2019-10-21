package com.sat.poc.datetime.api;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

	@Override
	public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ZonedDateTime zdTime = null;
		if (p.getValueAsString().length() < 11)
			zdTime = ZonedDateTime.parse(p.getValueAsString() + "T00:00:00.000Z");
		else
			zdTime = ZonedDateTime.parse(p.getValueAsString());

		return zdTime;
	}

}
