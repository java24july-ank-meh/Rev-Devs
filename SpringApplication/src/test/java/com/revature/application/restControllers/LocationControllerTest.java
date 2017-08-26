package com.revature.application.restControllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.revature.application.RevatureSocialNetworkApplication;
import com.revature.application.dao.HotSpotDao;
import com.revature.application.dao.LocationDao;
import com.revature.application.dao.beans.HotSpot;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.HotSpotForm;
import com.revature.application.dao.beans.forms.LocationForm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
public class LocationControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	// list for readAll test
	private List<Location> locations;

	@Mock
	private LocationDao mockLocDao;
	@Mock
	private HotSpotDao mockHSDao;
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

//	@Test
	public void returnAllLocations() throws Exception {
		// for method call it returns our created list
		// System.out.println("locations");
		// System.out.println(locations);

		when(mockLocDao.readAll()).thenReturn(locations);

		// System.out.println("locations2");
		// System.out.println(listMock.readAll());

		mockMvc.perform(get("/locations")).andExpect(status().isOk()).andExpect(content().contentType(contentType))
		// .andDo(print())
		.andExpect(jsonPath("$[0].locationId", is((int) locations.get(0).getLocationId())))
		.andExpect(jsonPath("$[0].longitude", is(locations.get(0).getLongitude())))
		.andExpect(jsonPath("$[0].lattitude", is(locations.get(0).getLattitude())))
		.andExpect(jsonPath("$[0].city", is(locations.get(0).getCity())));

	}

//	@Test
	public void returnSingleLocation() throws Exception {

		when(mockLocDao.read(30)).thenReturn(locations.get(0));

		int lId = (int) locations.get(0).getLocationId();

		mockMvc.perform(get("/locations/" + lId)).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		// .andDo(print())
		.andExpect(jsonPath("$.locationId", is(lId)))
		.andExpect(jsonPath("$.longitude", is(locations.get(0).getLongitude())))
		.andExpect(jsonPath("$.lattitude", is(locations.get(0).getLattitude())))
		.andExpect(jsonPath("$.city", is(locations.get(0).getCity())));
	}

//	@Test
	public void createLocationPass() throws Exception {
		
	    when(mockLocDao.create(any(LocationForm.class))).thenReturn(true);

		RequestBuilder rb = post("/locations").param("city", locations.get(0).getCity())
				.param("lattitude", locations.get(0).getLattitude() + "")
				.param("longitude", locations.get(0).getLongitude() + "");

		mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
		// .andDo(print())
		.andExpect(jsonPath("$.message", is("Success"))).andExpect(jsonPath("$.success", is(true)));
	}

//	@Test
	public void createLocationFail() throws Exception {
//		when(mockLocDao.create(isA(Location.class))).thenReturn(true);

		RequestBuilder rb = post("/locations");

		mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
		// .andDo(print())
		.andExpect(jsonPath("$.message", is("Failed to create new location")))
		.andExpect(jsonPath("$.success", is(false)));
	}

//	@Test
	public void createHotSpotPass() throws Exception {
	    
		when(mockHSDao.createHotSpot(any(HotSpotForm.class))).thenReturn(true);

		int lId = (int)locations.get(0).getLocationId() ;
		RequestBuilder rb = post("/locations/"+lId+"/hotspots")
				.param("locationId", lId+"")
				.param("lattitude", locations.get(0).getLattitude()+"")
				.param("longitude", locations.get(0).getLongitude()+"");

		mockMvc.perform(rb).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		//		.andDo(print())
		.andExpect(jsonPath("$.message",is("Success")))
		.andExpect(jsonPath("$.success",is(true)));
	}

	@Test
	public void createHotSpotFail() throws Exception {
//		when(mockHSDao.createHotSpot(isA(HotSpot.class))).thenReturn(true);

		int lId = (int) locations.get(0).getLocationId();
		RequestBuilder rb = post("/locations/" + lId + "/hotspots");

		mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
		 .andDo(print())
		.andExpect(jsonPath("$.message", is("Failed to create new hot spot")))
		.andExpect(jsonPath("$.success", is(false)));
	}

//	@Test
	public void deleteLocation() throws Exception {
		mockMvc.perform(delete("/locations/1")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType));

		when(mockLocDao.deleteById(isA(Long.class))).thenReturn(true);

		RequestBuilder rb = delete("/locations/" + locations.get(0).getLocationId());// .param("city",
		// locations.get(0).getCity());

		mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
		// .andDo(print())
		.andExpect(jsonPath("$.message", is("Success"))).andExpect(jsonPath("$.success", is(true)));
		;
	}

}