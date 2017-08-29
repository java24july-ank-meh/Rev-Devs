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
    
    @Mock
    private LocationDao mockLocDao;
    @Mock
    private HotSpotDao mockHSDao;
    @InjectMocks
    private LocationController locationController;
    
    // list for readAll test
    private List<Location> locations;
    private Location location1;
    private Location location2;
    
    @Before
    public void setup() throws Exception {
        
        MockitoAnnotations.initMocks(this);
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
        
        location1 = new Location("test1", 123.12, 123.12);
        location1.setLocationId(30L);
        location2 = new Location("test2", 123.12, 123.12);
        location2.setLocationId(31L);
        
        locations = new ArrayList<Location>();
        
        locations.add(location1);
        locations.add(location2);
    }
    
    @Test
    public void returnAllLocations() throws Exception {
        
        when(mockLocDao.readAll()).thenReturn(locations);
        
        mockMvc.perform(get("/locations")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].locationId",
                        Matchers.is(locations.get(0).getLocationId().intValue())))
                .andExpect(jsonPath("$[0].longitude", Matchers.is(locations.get(0).getLongitude())))
                .andExpect(jsonPath("$[0].lattitude", Matchers.is(locations.get(0).getLattitude())))
                .andExpect(jsonPath("$[0].city", Matchers.is(locations.get(0).getCity())))
                .andExpect(jsonPath("$[0].employees", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].companies", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].posts", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].locationId",
                        Matchers.is(locations.get(1).getLocationId().intValue())))
                .andExpect(jsonPath("$[1].longitude", Matchers.is(locations.get(1).getLongitude())))
                .andExpect(jsonPath("$[1].lattitude", Matchers.is(locations.get(1).getLattitude())))
                .andExpect(jsonPath("$[1].city", Matchers.is(locations.get(1).getCity())))
                .andExpect(jsonPath("$[1].employees", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].companies", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].posts", Matchers.notNullValue()));
        
    }
    
    @Test
    public void returnSingleLocation() throws Exception {
        
        when(mockLocDao.read(location1.getLocationId())).thenReturn(location1);
        
        mockMvc.perform(get("/locations/" + location1.getLocationId())).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.locationId",
                        Matchers.is(locations.get(0).getLocationId().intValue())))
                .andExpect(jsonPath("$.longitude", Matchers.is(locations.get(0).getLongitude())))
                .andExpect(jsonPath("$.lattitude", Matchers.is(locations.get(0).getLattitude())))
                .andExpect(jsonPath("$.city", Matchers.is(locations.get(0).getCity())))
                .andExpect(jsonPath("$.employees", Matchers.notNullValue()))
                .andExpect(jsonPath("$.companies", Matchers.notNullValue()))
                .andExpect(jsonPath("$.posts", Matchers.notNullValue()));
    }
    
    @Test
    public void createLocationPass() throws Exception {
        
        when(mockLocDao.create(any(LocationForm.class))).thenReturn(true);
        
        RequestBuilder rb = post("/locations").param("city", location1.getCity())
                .param("lattitude", location1.getLattitude() + "")
                .param("longitude", location1.getLongitude() + "");
        
        mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Success")))
                .andExpect(jsonPath("$.success", is(true)));
    }
    
    @Test
    public void createLocationFail() throws Exception {
        
        when(mockLocDao.create(any(LocationForm.class))).thenReturn(true);
        
        RequestBuilder rb = post("/locations");
        
        mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", is(false)));
    }
    
    @Test
    public void createHotSpotPass() throws Exception {
        
        when(mockHSDao.createHotSpot(any(HotSpotForm.class))).thenReturn(true);
        
        RequestBuilder rb = post("/locations/" + location1.getLocationId() + "/hotspots")
                .param("locationId", location1.getLocationId() + "")
                .param("lattitude", location1.getLattitude() + "")
                .param("longitude", location1.getLongitude() + "");
        
        mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", is("Success")))
                .andExpect(jsonPath("$.success", is(true)));
    }
    
    @Test
    public void createHotSpotFail() throws Exception {
        
        when(mockHSDao.createHotSpot(any(HotSpotForm.class))).thenReturn(true);
        
        RequestBuilder rb = post("/locations/" + location1.getLocationId() + "/hotspots");
        
        mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(false)));
    }
    
    @Test
    public void deleteLocation() throws Exception {
        
        when(mockLocDao.deleteById(anyLong())).thenReturn(true);
        
        RequestBuilder rb = delete("/locations/" + location1.getLocationId());
        
        mockMvc.perform(rb).andExpect(status().isOk()).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.message", Matchers.is("Success")))
                .andExpect(jsonPath("$.success", Matchers.is(true)));
        ;
    }
    
}