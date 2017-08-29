package com.revature.application.restControllers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
import com.revature.application.dao.PostCommentDao;
import com.revature.application.dao.PostDao;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.PostType;
import com.revature.application.dao.beans.forms.PostCommentForm;
import com.revature.application.dao.beans.forms.PostForm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
public class PostControllerTest {
    
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    private MockMvc mockMvc;
    
    @Mock
    private PostDao mockPostDao;
    @Mock
    private PostCommentDao postCommentDao;
    @InjectMocks
    private PostController postController;
    
    // Global objects for injection
    private List<Post> posts;
    private Post post1;
    private Post post2;
    private Date date = new Date(1L);
    private Location location = new Location();
    private Employee employee = new Employee();
    private PostType postType = new PostType();
    
    @Before
    public void setup() throws Exception {
        
        MockitoAnnotations.initMocks(this);
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
        
        // Set up all the Post info
        date = new Date(1L);
        post1 = new Post(location, employee, postType, date, "content1");
        post1.setPostId(1L);
        post2 = new Post(location, employee, postType, date, "content2");
        post2.setPostId(2L);
        
        posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);
    }
    
    @Test
    public void returnAllPosts() throws Exception {
        
        when(mockPostDao.readAll()).thenReturn(posts);
        
        mockMvc.perform(get("/posts")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].postId", Matchers.is(posts.get(0).getPostId().intValue())))
                .andExpect(jsonPath("$[0].content", Matchers.is(posts.get(0).getContent())))
                .andExpect(jsonPath("$[0].location", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].employee", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].type", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].postId", Matchers.is(posts.get(1).getPostId().intValue())))
                .andExpect(jsonPath("$[1].content", Matchers.is(posts.get(1).getContent())))
                .andExpect(jsonPath("$[1].location", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].employee", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].type", Matchers.notNullValue()));
    }
    
    @Test
    public void returnSinglePost() throws Exception {
        
        when(mockPostDao.read(post1.getPostId())).thenReturn(post1);
        
        mockMvc.perform(get("/posts/1")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.postId", Matchers.is(post1.getPostId().intValue())))
                .andExpect(jsonPath("$.content", Matchers.is(post1.getContent())))
                .andExpect(jsonPath("$.location", Matchers.notNullValue()))
                .andExpect(jsonPath("$.employee", Matchers.notNullValue()))
                .andExpect(jsonPath("$.type", Matchers.notNullValue()));
    }
    
    @Test
    public void createPost() throws Exception {
        
        when(mockPostDao.create(any(PostForm.class))).thenReturn(true);
        
        RequestBuilder builder = post("/posts")
                .param("locationId", "1")
                .param("employeeId", "1")
                .param("typeId", "1")
                .param("content", "message");      
        
        mockMvc.perform(builder).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("Success")));
    }
    
    @Test
    public void missingParamForPost() throws Exception {
        
        when(mockPostDao.create(any(PostForm.class))).thenReturn(true);
        
        RequestBuilder builder = post("/posts")
                .param("locationId", "1")
                .param("typeId", "1")
                .param("content", "message");      
        
        mockMvc.perform(builder).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(false)));
    }
    
    @Test
    public void createComment() throws Exception {
        
        when(postCommentDao.create(any(PostCommentForm.class))).thenReturn(true);
        
        RequestBuilder builder = post("/posts/comment")
                .param("employeeId", "1")
                .param("postId", "1")
                .param("content", "content");
        
        mockMvc.perform(builder).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("Success")));
    }
    
    @Test
    public void createCommentMissingParamsMustFail() throws Exception {
        
        when(postCommentDao.create(any(PostCommentForm.class))).thenReturn(true);
        
        RequestBuilder builder = post("/posts");
        
        mockMvc.perform(builder).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(false))); 
    }
    
    
    @Test
    public void deletePost() throws Exception {
        
        when(mockPostDao.deleteById(anyLong())).thenReturn(true);
        
        mockMvc.perform(delete("/posts/1")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("Success")));
    }
    
    @Test
    public void deleteComment() throws Exception {
        
        when(postCommentDao.deleteById(anyLong())).thenReturn(true);
        
        mockMvc.perform(delete("/posts/comment/1")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.success", Matchers.is(true)))
                .andExpect(jsonPath("$.message", Matchers.is("Success")));
    }
}
