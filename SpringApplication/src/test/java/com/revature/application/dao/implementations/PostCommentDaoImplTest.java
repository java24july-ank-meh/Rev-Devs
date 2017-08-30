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
import com.revature.application.dao.PostCommentDao;
import com.revature.application.dao.beans.PostComment;
import com.revature.application.dao.beans.PostType;
import com.revature.application.dao.beans.Company;
import com.revature.application.dao.beans.Employee;
import com.revature.application.dao.beans.HotSpot;
import com.revature.application.dao.beans.Location;
import com.revature.application.dao.beans.Post;
import com.revature.application.dao.beans.forms.PostCommentForm;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RevatureSocialNetworkApplication.class)
@Transactional
public class PostCommentDaoImplTest {

	@Autowired
	private SessionFactory sf;

	@Autowired
	private PostCommentDao postCommentDAO;

	// Objects we want to attach to post comments
	Company company = null;
	Location loc = null;
	Employee emp = null;
	Post post = null;
	Date date = null;
	PostType type = null;

	// Objects we want to persist in db
	PostComment postComment1 = null;
	PostComment postComment2 = null;
	PostComment postComment3 = null;

	@Before
	public void setup() {

		Session session = sf.getCurrentSession();

		date = new Date();
		loc = new Location("LA", 123.12, 123.12);
		company = new Company(loc, "Fintech");
		type = new PostType("new type");
		//only truly need these 2
		emp = new Employee(loc, company, "un12", "pw12", "un@mail.com", "Mike", "Stein");
		post = new Post(loc, emp, type, date, "post1234", new HotSpot()); 

		session.saveOrUpdate(company);
		session.saveOrUpdate(loc);
		session.saveOrUpdate(type);
		session.saveOrUpdate(emp);
		session.saveOrUpdate(post);

		postComment1 = new PostComment(emp, post, date, "comment1");
		postComment2 = new PostComment(emp, post, date, "comment2");
		postComment3 = new PostComment(emp, post, date, "comment3");

		session.saveOrUpdate(postComment1);
		session.saveOrUpdate(postComment2);
		// The last postComment3 will be used to persist using our api

	}

	@Test
	public void allMustBeInDB() {

		Session session = sf.getCurrentSession();

		Location newLoc =  (Location) session.get(Location.class, loc.getLocationId());
		Company newCompany =  (Company) session.get(Company.class, company.getCompanyId());
		PostType newType =  (PostType) session.get(PostType.class, type.getTypeId());
		//only truly need these 2
		Employee newEmp =  (Employee) session.get(Employee.class, emp.getEmployeeId());
		Post newPost =  (Post) session.get(Post.class, post.getPostId());


		assertTrue("Company object must be persisted to DB", newCompany != null);
		assertTrue("Location object must be persisted to DB", newLoc != null);
		assertTrue("Post type object must be persisted to DB", newType != null);
		//only truly need these 2
		assertTrue("Employee object must be persisted to DB", newEmp != null);
		assertTrue("Post object must be persisted to DB", newPost != null);

	}

	@Test
	public void createMethodMustSaveToDb() {

		Session session = sf.getCurrentSession();

		PostCommentForm postCommentForm = new PostCommentForm(emp.getEmployeeId(),
				post.getPostId(), postComment3.getContent());

		postCommentDAO.create(postCommentForm);

		String query = "from PostComment postComment where postComment.content = :postContent";
		PostComment newPostComment = (PostComment) session.createQuery(query)
				.setParameter("postContent", postComment3.getContent())
				.uniqueResult();

		assertTrue("PostComment object must be persisted to DB", newPostComment != null);
		assertTrue(newPostComment.getContent().equals(postComment3.getContent()));        
	}

	@Test
	public void readGetsTheProperPostCommentObject() {

		Session session = sf.getCurrentSession();

		PostComment newPostComment = postCommentDAO.read(postComment1.getCommentId());

		assertTrue("PostComment object must not be null", newPostComment != null);
		assertTrue(newPostComment.getCommentId() == postComment1.getCommentId());
		assertTrue(newPostComment.getContent().equals(postComment1.getContent()));        

	}

	@Test
	public void readAllGetsAllPostCommentObjects() {

		Session session = sf.getCurrentSession();

		List<PostComment> companies = postCommentDAO.readAll();

		assertTrue("Companies must not be null", companies != null);

		companies.sort((item1, item2) -> {
			if (item1.getCommentId() < item2.getCommentId()) {
				return -1;
			} else if (item1.getCommentId() > item2.getCommentId()) {
				return 1;
			} else {
				return 0;
			}
		});

		assertTrue(companies.size() == 2);

		assertTrue(companies.get(0).getCommentId() == postComment1.getCommentId());
		assertTrue(companies.get(1).getCommentId() == postComment2.getCommentId());

		assertTrue(companies.get(0).getContent().equals(postComment1.getContent()));
		assertTrue(companies.get(1).getContent().equals(postComment2.getContent()));

	}

	@Test
	public void updateMustChangePostComment() {

		Session session = sf.getCurrentSession();

		PostCommentForm form = new PostCommentForm(emp.getEmployeeId(),
				post.getPostId(), "Changed");
		
		postCommentDAO.update(postComment1.getCommentId(), form);

		PostComment newPostComment = (PostComment) session.get(PostComment.class, postComment1.getCommentId());
	
		assertTrue("New PostComment must not be null", newPostComment != null);
		assertTrue(newPostComment.getCommentId() == postComment1.getCommentId());
		assertTrue(newPostComment.getContent().equals("Changed"));

	}


	@Test
	public void deleteMustDeletePostComment() {

		Session session = sf.getCurrentSession();

		String query = "from PostComment postComment where postComment.content = :postContent";
		PostComment newPostComment = (PostComment) session.createQuery(query)
				.setParameter("postContent", postComment1.getContent())
				.uniqueResult();

		postCommentDAO.delete(newPostComment);

		PostComment testPostComment = session.get(PostComment.class, newPostComment.getCommentId());

		assertTrue("Test postComment must be null", testPostComment == null);
	}


	@Test
	public void deleteByIdMustDeletePostComment() {

		Session session = sf.getCurrentSession();

		String query = "from PostComment postComment where postComment.content = :postContent";
		PostComment newPostComment = (PostComment) session.createQuery(query)
				.setParameter("postContent", postComment1.getContent())
				.uniqueResult();

		long postCommentId = newPostComment.getCommentId();

		postCommentDAO.deleteById(postCommentId);

		PostComment testPostComment = (PostComment) session.get(PostComment.class, postCommentId);

		assertTrue("Test postComment must be null", testPostComment == null);
	}
}
