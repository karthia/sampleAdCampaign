package com.sample.application.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sample.application.model.AdCampaign;

@Repository
public class AdCampaignDaoImpl {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public AdCampaign createAdCampaign(AdCampaign ad) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("partner_id", ad.getPartner_id());
		params.put("duration", ad.getDuration());
		params.put("ad_content", ad.getAd_content());
		params.put("created_date", Calendar.getInstance().getTimeInMillis());
		
		int rowCnt = getTemplate().update("INSERT INTO AD_CAMPAIGN (partner_id, duration, ad_content, created_datetime) VALUES(:partner_id, :duration, :ad_content, :created_date)", params); 
		if(rowCnt == 0) {
			throw new Exception("Data Insertion error");
		}
		return ad;
	}
	
	public AdCampaign getAdCampaign(String partnerId) {
		Map<String, Object> params = new HashMap<>();
		params.put("partner_id", partnerId);
		return getTemplate().queryForObject("SELECT * FROM AD_CAMPAIGN WHERE partner_id=:partner_id", params, new RowMapper<AdCampaign>(){
			@Override
			public AdCampaign mapRow(ResultSet rs, int rowNum) throws SQLException {
				AdCampaign adCampaign = new AdCampaign();
				adCampaign.setPartner_id(rs.getString(1));
				adCampaign.setDuration(rs.getLong(2));
				adCampaign.setAd_content(rs.getString(3));
				adCampaign.setCreated_date(rs.getLong(4));
				return adCampaign;
			}
		});
	}
	
	public NamedParameterJdbcTemplate getTemplate() {
		return this.template;
	}
	
	public void setTemplate(NamedParameterJdbcTemplate template) {
		this.template = template;
	}
}
