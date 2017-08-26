package com.revature.application.dao.implementations;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.application.dao.PostCommentDao;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.PostComment;
import com.revature.application.dao.beans.forms.PostCommentForm;

@Service
public class PostCommentDaoImpl implements PostCommentDao {
    
    @Autowired
    SessionFactory sf;
    
    @Override
    @Transactional
    public boolean create(PostCommentForm commentForm) {
        
        Session session = sf.getCurrentSession();
        
        Employee employee = new Employee();
        employee.setEmployeeId(commentForm.getEmployeeId());
        
        Post post = new Post();
        post.setPostId(commentForm.getPostId());
        
        PostComment postComment = new PostComment(employee, post, new Date(),
                commentForm.getContent());
        
        session.save(postComment);
        session.flush();
        
        return true;
    }
    
    @Override
    @Transactional
    public List<PostComment> readAll() {
        
        Session session = sf.getCurrentSession();
        
        List<PostComment> comments = session.createQuery("from PostComment postComment").list();
        session.flush();
        
        return comments;
    }
    
    @Override
    @Transactional
    public PostComment read(long comment_id) {
        
        Session session = sf.getCurrentSession();
        
        PostComment comment = session.get(PostComment.class, comment_id);
        session.flush();
        
        return comment;
    }
    
    @Override
    @Transactional
    public boolean update(long comment_id, PostCommentForm commentForm) {
        
        Session session = sf.getCurrentSession();
        
        Employee employee = new Employee();
        employee.setEmployeeId(commentForm.getEmployeeId());
        
        Post post = new Post();
        post.setPostId(commentForm.getPostId());
        
        PostComment postComment = new PostComment(employee, post, new Date(),
                commentForm.getContent());
        postComment.setCommentId(comment_id);
        
        session.update(postComment);
        session.flush();
        
        return true;
    }
    
    @Override
    @Transactional
    public boolean delete(PostComment comment) {
        
        Session session = sf.getCurrentSession();
        
        session.delete(comment);
        session.flush();
        
        return true;
    }
    
    @Override
    @Transactional
    public boolean deleteById(long comment_id) {
        
        Session session = sf.getCurrentSession();
        
        PostComment comment = new PostComment();
        comment.setCommentId(comment_id);
        
        session.delete(comment);
        session.flush();
        
        return true;
    }
    
}
