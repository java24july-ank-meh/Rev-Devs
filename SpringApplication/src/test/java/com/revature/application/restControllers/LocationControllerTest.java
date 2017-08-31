package com.revature.application.restControllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
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

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.revature.application.RevatureSocialNetworkApplication;
import com.revature.application.dao.HotSpotDao;
import com.revature.application.dao.LocationDao;
import com.revature.application.dao.beans.HotSpot;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.forms.HotSpotForm;
import com.revature.application.dao.beans.forms.LocationForm;
import com.revature.application.services.LoginOperations;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
public class LocationControllerTest {
    
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    private MockMvc mockMvc;
    
    @Mock
    private LoginOperations mockLOperation;
    @Mock
    private LocationDao mockLocDao;
    @Mock
    private HotSpotDao mockHSDao;
    @InjectMocks
    private LocationController locationController;
    
    private Post post;
    private List<HotSpot> hotSpots;
    private HotSpot hotSpot1;
    private HotSpot hotSpot2;
    private List<Location> locations;
    private Location location1;
    private Location location2;
    
    @Before
    public void setup() throws Exception {
        
        MockitoAnnotations.initMocks(this);
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
        
        post = new Post();
        post.setPostId(1L);
        
        /*
         * Add companies, post, and employees to the 2 locations below for more testing
         * the andExpected() functions are below but no values currently for the expected to test
         */
        location1 = new Location("test1", 123.12, 123.12);
        location1.setLocationId(30L);
        location2 = new Location("test2", 123.12, 123.12);
        location2.setLocationId(31L);
        
        locations = new ArrayList<Location>();
        
        locations.add(location1);
        locations.add(location2);
        
        hotSpot1 = new HotSpot(123.12, 123.12, location1);
        hotSpot1.setHotSpotId(100L);
        hotSpot1.setPost(post);
        hotSpot2 = new HotSpot(1.1, 1.1, location1);
        hotSpot2.setHotSpotId(111L);
        hotSpot1.setPost(post);
        
        hotSpots = new ArrayList<>();
        hotSpots.add(hotSpot1);
        hotSpots.add(hotSpot2);
        
    }
    
    @Test
    public void returnAllLocationsPass() throws Exception {
    	when(mockLOperation.isLoggedIn()).thenReturn(true);
        when(mockLocDao.readAll()).thenReturn(locations);
        
        mockMvc.perform(get("/locations")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].locationId",
                        Matchers.is(locations.get(0).getLocationId().intValue())))
                .andExpect(jsonPath("$[0].longitude", Matchers.is(locations.get(0).getLongitude())))
                .andExpect(jsonPath("$[0].lattitude", Matchers.is(locations.get(0).getLattitude())))
                .andExpect(jsonPath("$[0].city", Matchers.is(locations.get(0).getCity())))
//                .andExpect(jsonPath("$[0].employees", Matchers.notNullValue()))
//                .andExpect(jsonPath("$[0].companies", Matchers.notNullValue()))
//                .andExpect(jsonPath("$[0].posts", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].locationId",
                        Matchers.is(locations.get(1).getLocationId().intValue())))
                .andExpect(jsonPath("$[1].longitude", Matchers.is(locations.get(1).getLongitude())))
                .andExpect(jsonPath("$[1].lattitude", Matchers.is(locations.get(1).getLattitude())))
                .andExpect(jsonPath("$[1].city", Matchers.is(locations.get(1).getCity())))
//                .andExpect(jsonPath("$[1].employees", Matchers.notNullValue()))
//                .andExpect(jsonPath("$[1].companies", Matchers.notNullValue()))
//                .andExpect(jsonPath("$[1].posts", Matchers.notNullValue()))
                ;
        
    }
    
    @Test
    public void returnSingleLocationPass() throws Exception {
    	when(mockLOperation.isLoggedIn()).thenReturn(true);

        when(mockLocDao.read(location1.getLocationId())).thenReturn(location1);
        
        mockMvc.perform(get("/locations/" + location1.getLocationId()))
        		.andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.locationId",
                        Matchers.is(locations.get(0).getLocationId().intValue())))
                .andExpect(jsonPath("$.longitude", Matchers.is(locations.get(0).getLongitude())))
                .andExpect(jsonPath("$.lattitude", Matchers.is(locations.get(0).getLattitude())))
                .andExpect(jsonPath("$.city", Matchers.is(locations.get(0).getCity())))
//                .andExpect(jsonPath("$.employees", Matchers.notNullValue()))
//                .andExpect(jsonPath("$.companies", Matchers.notNullValue()))
//                .andExpect(jsonPath("$.posts", Matchers.notNullValue()))
                ;
    }
    
    @Test
    public void returnAllHotSpotsForSingleLocationPass() throws Exception {
    	when(mockLOperation.isLoggedIn()).thenReturn(true);

        when(mockHSDao.readAllHotSpotsByLocationId(anyLong())).thenReturn(hotSpots);
        
        mockMvc.perform(get("/locations/" + location1.getLocationId() + "/hotspots")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andDo(print())
                .andExpect(jsonPath("$[0].hotSpotId",
                        Matchers.is(hotSpots.get(0).getHotSpotId().intValue())))
                .andExpect(jsonPath("$[0].longitude", Matchers.is(hotSpots.get(0).getLongitude())))
                .andExpect(jsonPath("$[0].lattitude", Matchers.is(hotSpots.get(0).getLattitude())))
                .andExpect(jsonPath("$[0].location",  Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].hotSpotId",
                        Matchers.is(hotSpots.get(1).getHotSpotId().intValue())))
                .andExpect(jsonPath("$[1].longitude", Matchers.is(hotSpots.get(1).getLongitude())))
                .andExpect(jsonPath("$[1].lattitude", Matchers.is(hotSpots.get(1).getLattitude())))
                .andExpect(jsonPath("$[1].location",  Matchers.notNullValue()));

    }
    
    @Test
    public void createLocationPass() throws Exception {
    	when(mockLOperation.isLoggedIn()).thenReturn(true);
        when(mockLocDao.create(any(LocationForm.class))).thenReturn(true);
        
        RequestBuilder rb = post("/locations")
        		.param("city", location1.getCity())
                .param("lattitude", location1.getLattitude() + "")
                .param("longitude", location1.getLongitude() + "");
        
        mockMvc.perform(rb).andExpect(status().isOk())
        	.andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Success")))
                .andExpect(jsonPath("$.success", is(true)));
    }
    
    @Test
    public void createLocationFailValidation() throws Exception {
    	when(mockLOperation.isLoggedIn()).thenReturn(false);

        RequestBuilder rb = post("/locations")
        		.param("city", "")
                .param("lattitude", "")
                .param("longitude", "");
        
        mockMvc.perform(rb).andExpect(status().isUnauthorized())
        .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", is(false)));
    }
    
    /*
     * check the printed response    
     */
    @Test
    public void createHotSpotPass() throws Exception {
    	when(mockLOperation.isLoggedIn()).thenReturn(true);
        when(mockHSDao.createHotSpot(any(HotSpotForm.class))).thenReturn(true);
        
        RequestBuilder rb = post("/locations/" + location1.getLocationId() + "/hotspots")
        		.param("postId", post.getPostId()+ "")
                .param("locationId", location1.getLocationId() + "")
                .param("lattitude", location1.getLattitude() + "")
                .param("longitude", location1.getLongitude() + "")
//                .contentType(contentType)
                ;
        
        mockMvc.perform(rb).andExpect(status().isOk())
        		.andDo(print())
        		.andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Success")))
                .andExpect(jsonPath("$.success", is(true)));
    }
    
    /*
     * check the printed response    
     */
    @Test
    public void createHotSpotFailValidation() throws Exception {
        
    	when(mockLOperation.isLoggedIn()).thenReturn(false);
        
        RequestBuilder rb = post("/locations/" + location1.getLocationId() + "/hotspots")
        		.param("postId", "")
                .param("locationId", "")
                .param("lattitude", "")
                .param("longitude", "");
//                .contentType(contentType)
        		;
        
        mockMvc.perform(rb).andExpect(status().isUnauthorized())
        		.andDo(print())
        		.andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(false)));
    }
    
    @Test
    public void deleteLocationPass() throws Exception {
    	when(mockLOperation.isLoggedIn()).thenReturn(true);
        when(mockLocDao.deleteById(anyLong())).thenReturn(true);
        
        RequestBuilder rb = delete("/locations/" + location1.getLocationId());
        
        mockMvc.perform(rb).andExpect(status().isOk())
        		.andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", Matchers.is("Success")))
                .andExpect(jsonPath("$.success", Matchers.is(true)));
        ;
    }
    
}