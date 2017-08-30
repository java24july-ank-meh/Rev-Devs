package com.revature.application.restControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.application.dao.PostTypeDao;
import com.revature.application.dao.beans.PostType;
import com.revature.application.services.LoginOperations;

@RestController
@RequestMapping("/postTypes")
public class PostTypesController {
    
    @Autowired
    private PostTypeDao postTypeDao;
    
    @Autowired
    private LoginOperations loginService;
    
    /*
     * All GET requests
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<PostType>> getAllPostTypes() {
        if (loginService.isLoggedIn()) {
            return new ResponseEntity<List<PostType>>(postTypeDao.readAll(), HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
}
