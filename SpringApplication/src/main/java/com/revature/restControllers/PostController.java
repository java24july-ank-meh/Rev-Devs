package com.revature.restControllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Greeting;

@RestController
@RequestMapping("/posts")
public class PostController {

	
	/*
	 * All GET requests
	 */
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public Greeting readAllPosts() {
		// Read all posts from the db
		return new Greeting(1, "Read all the posts from the db");
	}
	
	@RequestMapping(path = "/{postId}", method = RequestMethod.GET)
	public Greeting readSinglePost(@PathVariable long postId) {
		// Read a single post from db
		return new Greeting(1, "Read a single post from db");
	}
	
	/*
	 * All POST requests
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public Greeting createPost(/* Get Post object */) {
		// Create a post in the db
		return new Greeting(1, "Created a new post in the db");
	}
	
	/*
	 * All DELETE requests
	 */
	@RequestMapping(path = "/{postId}", method = RequestMethod.DELETE)
	public Greeting deletePost(@PathVariable long postId) {
		// Delete a post from the db
		return new Greeting(1, "Deleted a post variable");
	}
	
}
