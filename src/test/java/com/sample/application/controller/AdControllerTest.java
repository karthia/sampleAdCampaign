package com.sample.application.controller;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.application.Application;
import com.sample.application.ApplnConfig;
import com.sample.application.dao.AdCampaignDaoImpl;
import com.sample.application.model.AdCampaign;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplnConfig.class, Application.class})
@WebAppConfiguration
public class AdControllerTest {

	private MockMvc mockMvc;
	
	@Mock 
	AdCampaignDaoImpl mockDaoImpl;
	
	@Mock 
	AdCampaign mockAd;
	
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testPostAdCampaign() throws Exception {
		AdCampaign ad = new AdCampaign();
		ad.setAd_content("test content");
		ad.setCreated_date(12344L);
		ad.setDuration(1000L);
		ad.setPartner_id("partner1");
		when(mockDaoImpl.createAdCampaign(ad)).thenReturn(ad);
		
		mockMvc.perform(post("/ad").content(convertObjectToJsonBytes(ad)).contentType(contentType))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.content().contentType(contentType));
	}

	@Test
	public void testGetAdCampaign() throws Exception {
		AdCampaign ad = new AdCampaign();
		ad.setAd_content("test content");
		ad.setCreated_date(12344L);
		ad.setDuration(1000L);
		ad.setPartner_id("partner1");
		when(mockDaoImpl.getAdCampaign(anyString())).thenReturn(ad);
		
		mockMvc.perform(get("/ad/{partnerId}", "partner1"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType(contentType))
					.andExpect(MockMvcResultMatchers.jsonPath("$.partner_id", is("partner1")))
					.andExpect(MockMvcResultMatchers.jsonPath("$.duration", is(1000)))
					.andExpect(MockMvcResultMatchers.jsonPath("$.ad_content", is("test content")));
	}
	
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}
