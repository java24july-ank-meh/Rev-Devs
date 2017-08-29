package com.revature.application.dao.implementations;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.dao.PostDao;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.PostType;
import com.revature.application.dao.beans.forms.PostForm;

@Service
public class PostDaoImpl implements PostDao {
    
    @Autowired
    SessionFactory sf;
    
    @Override
    @Transactional
    public boolean create(PostForm postForm) {
        
        Session session = sf.getCurrentSession();
        
        Location location = new Location();
        location.setLocationId(postForm.getLocationId());
        
        Employee employee = new Employee();
        employee.setEmployeeId(postForm.getEmployeeId());
        
        PostType postType = new PostType();
        postType.setTypeId(postForm.getTypeId());
        
        Post post = new Post(location, employee, postType, new Date(), postForm.getContent());
        
        session.save(post);
        session.flush();
        
        return true;
    }
    
    @Override
    @Transactional
    public List<Post> readAll() {
        
        Session session = sf.getCurrentSession();
        
        List<Post> posts = session.createQuery("from Post post").list();
        session.flush();
        
        return posts;
    }
    
    @Override
    @Transactional
    public Post read(long post_id) {
        
        Session session = sf.getCurrentSession();
        Post post = session.get(Post.class, post_id);
        session.flush();
        
        return post;
    }
    
    @Override
    @Transactional
    public boolean update(long post_id, PostForm postForm) {
        
        Session session = sf.getCurrentSession();
        
        Location location = (Location) session.get(Location.class, postForm.getLocationId());
        
        Employee employee = (Employee) session.get(Employee.class, postForm.getEmployeeId());
        
        PostType postType = (PostType) session.get(PostType.class, postForm.getTypeId());
        
        Post post = (Post) session.get(Post.class, post_id);
        post.setContent(postForm.getContent());
        post.setEmployee(employee);
        post.setLocation(location);
        post.setType(postType);
        
        session.update(post);
        session.flush();
        
        return true;
    }
    
    @Override
    @Transactional
    public boolean delete(Post post) {
        
        Session session = sf.getCurrentSession();
        session.delete(post);
        session.flush();
        
        return true;
    }
    
    @Override
    @Transactional
    public boolean deleteById(long post_id) {
        
        Session session = sf.getCurrentSession();
        
        Post post = (Post) session.get(Post.class, post_id);
        session.delete(post);
        
        session.flush();
        
        return true;
    }
    
}
