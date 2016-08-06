package com.sample.application.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sample.application.dao.AdCampaignDaoImpl;
import com.sample.application.model.AdCampaign;
import com.sample.application.model.MessageResponse;

@RestController
public class AdController {

	@Autowired
	AdCampaignDaoImpl daoimpl;
	
	@RequestMapping(value="/ad", method=RequestMethod.POST)
	@ResponseBody
	public AdCampaign postAdCampaign(@RequestBody AdCampaign ad) throws Exception {
		return daoimpl.createAdCampaign(ad);
	}
	
	@RequestMapping(value="/ad/{partnerId}", method=RequestMethod.GET)
	@ResponseBody
	public AdCampaign getAdCampaign(@PathVariable String partnerId) throws Exception {
		AdCampaign adCampaign = daoimpl.getAdCampaign(partnerId);
		long currentTime = Calendar.getInstance().getTimeInMillis();
		long adExpireTime = adCampaign.getCreated_date() + adCampaign.getDuration();
		if(currentTime > adExpireTime) {
			throw new Exception("Requested Ad is Expired!!");
		}
		return adCampaign;
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public MessageResponse handleException(Exception e) {
		MessageResponse response = new MessageResponse();
		if(e instanceof EmptyResultDataAccessException) {
			response.setMessage("The Requested record not found");
		} else if(e instanceof DuplicateKeyException){
			response.setMessage("The record already present in Memory");
		} else {
			response.setMessage(e.getMessage());
		}
		response.setStatus(HttpStatus.BAD_REQUEST);
		return response;
	}
}
