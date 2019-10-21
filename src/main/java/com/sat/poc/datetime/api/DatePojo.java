package com.sat.poc.datetime.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class DatePojo {

	private Date utilDate;
	private LocalDate localDate;
	
	private ZonedDateTime zoneDateTime;
	private LocalDateTime localDateTime;

	public Date getUtilDate() {
		return utilDate;
	}

	public void setUtilDate(Date utilDate) {
		this.utilDate = utilDate;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	public ZonedDateTime getZoneDateTime() {
		return zoneDateTime;
	}

	public void setZoneDateTime(ZonedDateTime zoneDateTime) {
		this.zoneDateTime = zoneDateTime;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

}
