
package com.revature.application.dao.implementations;

import static org.junit.Assert.assertTrue;

import java.util.Date;
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
import com.revature.application.dao.PostDao;
import com.revature.application.dao.PostDao;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.PostType;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.forms.PostForm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
@Transactional
public class PostDaoImplTest {

	@Autowired
	private SessionFactory sf;

	@Autowired
	private PostDao postDAO;

	// Objects we want to attach to post comments
	Company company = null;
	Location loc = null;
	Employee emp = null;
	Date date = null;
	PostType type = null;

	// Objects we want to persist in db
	Post post1 = null;
	Post post2 = null;
	Post post3 = null;

	@Before
	public void setup() {

		Session session = sf.getCurrentSession();

		date = new Date();
		loc = new Location("LA", 123.12, 123.12);
		company = new Company(loc, "Fintech");
		type = new PostType("new type");
		emp = new Employee(loc, company, "un12", "pw12", "un@mail.com", "Mike", "Stein");

		session.saveOrUpdate(company);
		session.saveOrUpdate(loc);
		session.saveOrUpdate(type);
		session.saveOrUpdate(emp);

		post1 = new Post(loc, emp, type, date, "post1");
		post2 = new Post(loc, emp, type, date, "post2");
		post3 = new Post(loc, emp, type, date, "post3");

		session.saveOrUpdate(post1);
		session.saveOrUpdate(post2);
		// The last post3 will be used to persist using our api

	}

	@Test
	public void allMustBeInDB() {

		Session session = sf.getCurrentSession();

		Location newLoc =  (Location) session.get(Location.class, loc.getLocationId());
		Company newCompany =  (Company) session.get(Company.class, company.getCompanyId());
		PostType newType =  (PostType) session.get(PostType.class, type.getTypeId());
		Employee newEmp =  (Employee) session.get(Employee.class, emp.getEmployeeId());


		assertTrue("Company object must be persisted to DB", newCompany != null);
		assertTrue("Location object must be persisted to DB", newLoc != null);
		assertTrue("Post type object must be persisted to DB", newType != null);
		assertTrue("Employee object must be persisted to DB", newEmp != null);

	}

	@Test
	public void createMethodMustSaveToDb() {

		Session session = sf.getCurrentSession();

		PostForm postForm = new PostForm(loc.getLocationId(),emp.getEmployeeId(),
				post1.getType().getTypeId(), post3.getContent());

		postDAO.create(postForm);

		String query = "from Post post where post.content = :postContent";
		Post newPost = (Post) session.createQuery(query)
				.setParameter("postContent", post3.getContent())
				.uniqueResult();

		assertTrue("Post object must be persisted to DB", newPost != null);
		assertTrue(newPost.getContent().equals(post3.getContent()));        
	}

	@Test
	public void readGetsTheProperPostObject() {

		Session session = sf.getCurrentSession();

		Post newPost = postDAO.read(post1.getPostId());

		assertTrue("Post object must not be null", newPost != null);
		assertTrue(newPost.getPostId() == post1.getPostId());
		assertTrue(newPost.getContent().equals(post1.getContent()));        

	}

	@Test
	public void readAllGetsAllPostObjects() {

		Session session = sf.getCurrentSession();

		List<Post> companies = postDAO.readAll();

		assertTrue("Companies must not be null", companies != null);

		companies.sort((item1, item2) -> {
			if (item1.getPostId() < item2.getPostId()) {
				return -1;
			} else if (item1.getPostId() > item2.getPostId()) {
				return 1;
			} else {
				return 0;
			}
		});

		assertTrue(companies.size() == 2);

		assertTrue(companies.get(0).getPostId() == post1.getPostId());
		assertTrue(companies.get(1).getPostId() == post2.getPostId());

		assertTrue(companies.get(0).getContent().equals(post1.getContent()));
		assertTrue(companies.get(1).getContent().equals(post2.getContent()));

	}

	@Test
	public void updateMustChangePost() {

		Session session = sf.getCurrentSession();
		PostForm form = new PostForm(loc.getLocationId(),emp.getEmployeeId(),
				post1.getType().getTypeId(), "Changed");

		postDAO.update(post1.getPostId(), form);

		Post newPost = (Post) session.get(Post.class, post1.getPostId());

		assertTrue("New Post must not be null", newPost != null);
		assertTrue(newPost.getPostId() == post1.getPostId());
		assertTrue(newPost.getContent().equals("Changed"));

	}


	@Test
	public void deleteMustDeletePost() {

		Session session = sf.getCurrentSession();

		String query = "from Post post where post.content = :postContent";
		Post newPost = (Post) session.createQuery(query)
				.setParameter("postContent", post1.getContent())
				.uniqueResult();

		postDAO.delete(newPost);

		Post testPost = session.get(Post.class, newPost.getPostId());

		assertTrue("Test post must be null", testPost == null);
	}


	@Test
	public void deleteByIdMustDeletePost() {

		Session session = sf.getCurrentSession();

		String query = "from Post post where post.content = :postContent";
		Post newPost = (Post) session.createQuery(query)
				.setParameter("postContent", post1.getContent())
				.uniqueResult();

		long postId = newPost.getPostId();

		postDAO.deleteById(postId);

		Post testPost = (Post) session.get(Post.class, postId);

		assertTrue("Test post must be null", testPost == null);
	}
}
