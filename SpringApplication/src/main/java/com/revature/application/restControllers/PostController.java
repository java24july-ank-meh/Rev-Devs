package com.revature.application.restControllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.beans.RequestStatus;
import com.revature.application.dao.PostCommentDao;
import com.revature.application.dao.PostDao;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.PostComment;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	PostDao postDAO;
	
	@Autowired
	PostCommentDao commentDAO;
	
	/*
	 * All GET requests
	 */
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<Post> readAllPosts() {
		// Read all posts from the db
		return postDAO.readAll();
	}

	@RequestMapping(path = "/{postId}", method = RequestMethod.GET)
	public Post readSinglePost(@PathVariable long postId) {
		// Read a single post from db
		return postDAO.read(postId);
	}

	/*
	 * All POST requests
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public RequestStatus createPost(@Valid Post post, BindingResult bindingResult) {
		// Create a post in the db
		if (!bindingResult.hasErrors()) {
			postDAO.create(post);
			return new RequestStatus(); 
		}
		
		return new RequestStatus(false, "Failed to create new post");
	}
	
	@RequestMapping(path = "/comment", method = RequestMethod.POST) 
	public RequestStatus createComment(@Valid PostComment comment, BindingResult bindingResult) {
		// Save a comment for this comment
		
		if (!bindingResult.hasErrors()) {
			commentDAO.create(comment);
			return new RequestStatus();
		}
		
		return new RequestStatus(false, "Failed to create new comment");
	}
	
	
	/*
	 * All DELETE requests
	 */
	@RequestMapping(path = "/{postId}", method = RequestMethod.DELETE)
	public RequestStatus deletePost(@PathVariable long postId) {
		// Delete a post from the db
		postDAO.deleteById(postId);
		return new RequestStatus();
	}
	
	@RequestMapping(path = "/comment/{commentId}", method = RequestMethod.DELETE)
	public RequestStatus deleteComment(@PathVariable long commentId) {
		// Delete a comment from the db
		commentDAO.deleteById(commentId);
		return new RequestStatus();
	}

}
