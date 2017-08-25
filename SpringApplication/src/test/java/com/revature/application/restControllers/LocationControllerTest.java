package com.revature.application.restControllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.revature.application.RevatureSocialNetworkApplication;
import com.revature.application.dao.LocationDao;
import com.revature.application.dao.beans.Location;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
public class LocationControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	//list for readAll test
	private List<Location> locations;

	@Mock
	private LocationDao mockLocDao;
	@InjectMocks 
	private LocationController locationController;

	@Before
	public void setup() throws Exception {

		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();

		locations = new ArrayList<Location>();
		Location location = new Location("test1", 22.2, 111.1);
		location.setLocationId(30L);
		locations.add(location);
	}

	/*
	 * Only testing for success and correct response type, responses must also be
	 * tested for correct data response
	 */

	@Test
	public void returnAllLocations() throws Exception {
		//for method call it returns our created list
		//		System.out.println("locations");
		//		System.out.println(locations);

		when(mockLocDao.readAll()).thenReturn(locations);

		//		System.out.println("locations2");
		//		System.out.println(listMock.readAll());

		mockMvc.perform(get("/locations"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
//		.andDo(print())
		.andExpect(jsonPath("$[0].locationId", is((int)locations.get(0).getLocationId())))
		.andExpect(jsonPath("$[0].longitude", is(locations.get(0).getLongitude())))
		.andExpect(jsonPath("$[0].lattitude", is(locations.get(0).getLattitude())))
		.andExpect(jsonPath("$[0].city", is(locations.get(0).getCity())));

	}

	@Test
	public void returnSingleLocation() throws Exception {

		when(mockLocDao.read(30)).thenReturn(locations.get(0));

		int lId = (int)locations.get(0).getLocationId() ;
		
		mockMvc.perform(get("/locations/"+lId))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
//		.andDo(print())
		.andExpect(jsonPath("$.locationId", is(lId)))
		.andExpect(jsonPath("$.longitude", is(locations.get(0).getLongitude())))
		.andExpect(jsonPath("$.lattitude", is(locations.get(0).getLattitude())))
		.andExpect(jsonPath("$.city", is(locations.get(0).getCity())));
	}

	//	@Test
	public void createLocation() throws Exception {
		mockMvc.perform(post("/locations")).andExpect(status().isOk()).andExpect(content().contentType(contentType));
	}

	//	@Test
	public void deleteLocation() throws Exception {
		mockMvc.perform(delete("/locations/1")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType));
	}

}