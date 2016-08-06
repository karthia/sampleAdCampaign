package com.sample.application.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sample.application.ApplnConfig;
import com.sample.application.model.AdCampaign;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplnConfig.class})
@WebAppConfiguration
public class AdCampaignDaoImplTest {

	@InjectMocks 
	AdCampaignDaoImpl mockDaoImpl;
	
	@Autowired 
	NamedParameterJdbcTemplate template;
	
	@Mock AdCampaign mockAd;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(mockAd.getAd_content()).thenReturn("test content");
		when(mockAd.getDuration()).thenReturn(50L);
		when(mockAd.getPartner_id()).thenReturn("test1");
		when(mockAd.getCreated_date()).thenReturn(122201025L);
		mockDaoImpl.setTemplate(template);
	}
	
	@Test
	public void testCreateAdCampaign() throws Exception {
		
		AdCampaign adCam = mockDaoImpl.createAdCampaign(mockAd);
		assertEquals(adCam.getAd_content(), "test content");
		assertEquals(adCam.getDuration(), 50L);
		assertEquals(adCam.getPartner_id(), "test1");
		assertEquals(adCam.getCreated_date(), 122201025L);
	}

	@Test
	public void testGetAdCampaign() throws Exception {
		AdCampaign adCam = mockDaoImpl.getAdCampaign("test1");
		assertEquals(adCam.getAd_content(), "test content");
		assertEquals(adCam.getDuration(), 50L);
		assertEquals(adCam.getPartner_id(), "test1");
	}

}
