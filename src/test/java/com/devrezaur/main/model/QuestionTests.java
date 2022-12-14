package com.devrezaur.main.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.devrezaur.main.repository.QuestionRepo;


@DataJpaTest
//@TestMethodOrder(OrderAnnotation.class) // to run by order we add @order(ordernumber) before the methods
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// to run this test  uncomment the autoconfigure and change ddl-auto to create-dop /none
public class QuestionTests {
	
	@Autowired
	private QuestionRepo qrepo;
	
	@Test
	//@Rollback(false) // to save the data in DB // for that change spring.jpa.hibernate.ddl-auto=none
	// @Order(1) // uncomment for order
	public void testCreateQuestion() {

		Question question = new Question("what is 4+4", "8","5","3",1,-1);
		Question saveQuestion= qrepo.save(question);
		assertNotNull(saveQuestion);
	
	}
	
	@Test
	// @Order(2) // uncomment for order
	public void testFindQuestionByTitleExist() {
		Question question = new Question("what is 4+4", "8","5","3",1,-1);
		Question saveQuestion= qrepo.save(question);
		
		String title ="what is 4+4";
		Question question1= qrepo.findByTitle(title);
		
		assertThat(question1.getTitle()).isEqualTo(title);
	}

	@Test
	public void testFindQuestionByTitleNotExist() {
		String title ="what is 4+40";
		Question question= qrepo.findByTitle(title);
		
		assertNull(question);
	}
	
	@Test
	//@Rollback(false)
	public void testUpdateQuestion() {
		String questionTitle = "what is 6*6";
		String questionTiTleUpdated = "updated question";
		Question question = new Question(questionTitle, "36","5","3",1,-1);
		Question question1 = qrepo.save(question);
		
		question1.setTitle(questionTiTleUpdated);
		
		Question updateQuestion = qrepo.save(question1);
		assertThat(updateQuestion.getTitle()).isEqualTo(questionTiTleUpdated);
			}
	
	@Test
	public void testListQuestions() {
		List<Question> questions = (List<Question>) qrepo.findAll();
		
		for(Question question : questions) {
			System.out.println(question);
		}
		
		assertThat(questions).size().isGreaterThan(0);
	}
	
	@Test
	//@Rollback(false)
	public void testDeleteQuestion() {
		
		Question question = new Question("what is 4+4", "8","5","3",1,-1);
		Question saveQuestion= qrepo.save(question);
		
	//	Question question= qrepo.findByTitle("what is 6*6");
		
		Integer id = saveQuestion.getQuesId();
		
		
		
		boolean isExistBeforeDelete = qrepo.findById(id).isPresent();
		if(isExistBeforeDelete)		qrepo.deleteById(id);
		
		boolean notExistAfterDelete = qrepo.findById(id).isPresent();
		
		assertTrue(isExistBeforeDelete);
		assertFalse(notExistAfterDelete);
		
	}
}
