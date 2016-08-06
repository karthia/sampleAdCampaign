package com.sample.application.model;

import java.util.Date;

public class AdCampaign {
	
	private String partner_id;
	
	private long duration;
	
	private String ad_content;
	
	private long created_date;

	public String getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getAd_content() {
		return ad_content;
	}

	public void setAd_content(String ad_content) {
		this.ad_content = ad_content;
	}

	public long getCreated_date() {
		return created_date;
	}

	public void setCreated_date(long created_date) {
		this.created_date = created_date;
	}
}
