package com.revature.application.restControllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.RequestStatus;
import com.revature.application.dao.PostCommentDao;
import com.revature.application.dao.PostDao;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.forms.PostCommentForm;
import com.revature.application.dao.beans.forms.PostForm;
import com.revature.application.services.LoginOperations;

@RestController
@RequestMapping("/posts")
public class PostController {
    
    @Autowired
    PostDao postDAO;
    
    @Autowired
    PostCommentDao commentDAO;
    
    @Autowired
    LoginOperations loginService;
    
    /*
     * All GET requests
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Post>> readAllPosts() {
        // Read all posts from the db
        if (loginService.isLoggedIn()) {
            return new ResponseEntity<>(postDAO.readAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(path = "/{postId}", method = RequestMethod.GET)
    public ResponseEntity<Post> readSinglePost(@PathVariable long postId) {
        // Read a single post from db
        if (loginService.isLoggedIn()) {
            return new ResponseEntity<>(postDAO.read(postId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    /*
     * All POST requests
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<RequestStatus> createPost(@Valid PostForm postForm,
            BindingResult bindingResult) {
        // Create a post in the db
        if (loginService.isLoggedIn()) {
            if (!bindingResult.hasErrors()) {
                postForm.setEmployeeId(loginService.getEmployeeId());
                postDAO.create(postForm);
                return new ResponseEntity<>(new RequestStatus(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new RequestStatus(false, "Failed to create new post"),
                    HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }
    
    @RequestMapping(path = "/comment", method = RequestMethod.POST)
    public ResponseEntity<RequestStatus> createComment(@Valid PostCommentForm commentForm,
            BindingResult bindingResult) {
        // Save a comment for this comment
        if (loginService.isLoggedIn()) {
            if (!bindingResult.hasErrors()) {
                commentForm.setEmployeeId(loginService.getEmployeeId());
                commentDAO.create(commentForm);
                return new ResponseEntity<>(new RequestStatus(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new RequestStatus(false, "Failed to create new comment"),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    /*
     * All DELETE requests
     */
    @RequestMapping(path = "/{postId}", method = RequestMethod.DELETE)
    public ResponseEntity<RequestStatus> deletePost(@PathVariable long postId) {
        // Delete a post from the db
        if (loginService.isLoggedIn()) {
            postDAO.deleteById(postId);
            return new ResponseEntity<>(new RequestStatus(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
    }
    
    @RequestMapping(path = "/comment/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity<RequestStatus> deleteComment(@PathVariable long commentId) {
        // Delete a comment from the db
        if (loginService.isLoggedIn()) {
            commentDAO.deleteById(commentId);
            return new ResponseEntity<>(new RequestStatus(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
}
