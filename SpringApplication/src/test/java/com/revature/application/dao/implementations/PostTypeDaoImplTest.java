package com.revature.application.dao.implementations;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.RevatureSocialNetworkApplication;
import com.revature.application.dao.PostTypeDao;
import com.revature.application.dao.beans.PostType;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.forms.PostTypeForm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
@Transactional
public class PostTypeDaoImplTest {
    
    @Autowired
    private SessionFactory sf;
    
    @Autowired
    private PostTypeDao postTypeDAO;
    
    // Objects we want to persist in db
    PostType postType1 = null;
    PostType postType2 = null;
    PostType postType3 = null;
    
    @Before
    public void setup() {
        
        Session session = sf.getCurrentSession();
                
        postType1 = new PostType("A");
        postType2 = new PostType("B");
        postType3 = new PostType("C");
        
        session.saveOrUpdate(postType1);
        session.saveOrUpdate(postType2);
        // The last postType3 will be used to persist using our api
        
    }
    
    @Test
    public void createMethodMustSaveToDb() {
        
        Session session = sf.getCurrentSession();
        
        PostTypeForm postTypeForm = new PostTypeForm(postType3.getType());
        
        postTypeDAO.create(postTypeForm);
        
        String query = "from PostType postType where postType.type = :type";
        PostType newPostType = (PostType) session.createQuery(query)
                .setParameter("type", postType3.getType())
                .uniqueResult();
        
        assertTrue("PostType object must be persisted to DB", newPostType != null);
        assertTrue(newPostType.getType().equals(postType3.getType()));
    }
    
    @Test
    public void readGetsTheProperPostTypeObject() {
            
        Session session = sf.getCurrentSession();
        
        PostType newPostType = postTypeDAO.read(postType1.getTypeId());
        
        assertTrue("PostType object must not be null", newPostType != null);
        assertTrue(newPostType.getTypeId().equals(postType1.getTypeId()));
        assertTrue(newPostType.getType().equals(postType1.getType()));        
        
    }
    
    @Test
    public void readAllGetsAllPostTypeObjects() {
                
        Session session = sf.getCurrentSession();
        
        List<PostType> companies = postTypeDAO.readAll();
        
        assertTrue("Companies must not be null", companies != null);
        
        companies.sort((item1, item2) -> {
            if (item1.getTypeId() < item2.getTypeId()) {
                return -1;
            } else if (item1.getTypeId() > item2.getTypeId()) {
                return 1;
            } else {
                return 0;
            }
        });
   
        assertTrue(companies.size() == 2);
        
        assertTrue(companies.get(0).getTypeId().equals(postType1.getTypeId()));
        assertTrue(companies.get(1).getTypeId().equals(postType2.getTypeId()));
        
        assertTrue(companies.get(0).getType().equals(postType1.getType()));
        assertTrue(companies.get(1).getType().equals(postType2.getType()));
                
    }
    
    @Test
    public void updateMustChangePostType() {
        
        Session session = sf.getCurrentSession();
        
        PostTypeForm form = new PostTypeForm("Changed");
        
        postTypeDAO.update(postType1.getTypeId(), form);
        
        PostType newPostType = (PostType) session.get(PostType.class, postType1.getTypeId());
        
        assertTrue("New PostType must not be null", newPostType != null);
        assertTrue(newPostType.getTypeId().equals(postType1.getTypeId()));
        assertTrue(newPostType.getType().equals("Changed"));
        
    }
    
    
    @Test
    public void deleteMustDeletePostType() {
        
        Session session = sf.getCurrentSession();
        
        Long postTypeId = postType1.getTypeId();
        postTypeDAO.delete(postType1);
        
        PostType testPostType = session.get(PostType.class, postTypeId);
        
        assertTrue("Test postType must be null", testPostType == null);
    }
    
    
    @Test
    public void deleteByIdMustDeletePostType() {
        
        Session session = sf.getCurrentSession();
        
        Long postTypeId = postType1.getTypeId();
               
        postTypeDAO.deleteById(postTypeId);
                        
        PostType testPostType = (PostType) session.get(PostType.class, postTypeId);
        
        assertTrue("Test postType must be null", testPostType == null);
    }
}
