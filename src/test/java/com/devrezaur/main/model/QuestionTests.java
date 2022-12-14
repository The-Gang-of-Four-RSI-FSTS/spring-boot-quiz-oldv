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
	@Rollback(false) // to save the data in DB // for that change spring.jpa.hibernate.ddl-auto=none
	// @Order(1) // uncomment for order
	public void testCreateQuestion() {
		Question question = new Question(11,"what is 4+4", "8","5","3",1,-1);
		Question saveQuestion= qrepo.save(question);
		assertNotNull(saveQuestion);
	}
	
	@Test
	// @Order(2) // uncomment for order
	public void testFindQuestionByTitleExist() {
		String title ="What is a correct syntax to output \"Hello World\" in Java?";
		Question question= qrepo.findByTitle(title);
		
		assertThat(question.getTitle()).isEqualTo(title);
	}

	@Test
	public void testFindQuestionByTitleNotExist() {
		String title ="what is 4+40";
		Question question= qrepo.findByTitle(title);
		
		assertNull(question);
	}
	
	@Test
	@Rollback(false)
	public void testUpdateQuestion() {
		String questionTitle = "what is 6*6";
		Question question = new Question(11,questionTitle, "36","5","3",1,-1);
		
		qrepo.save(question);
		Question updateQuestion = qrepo.findByTitle(questionTitle);
		assertThat(updateQuestion.getTitle()).isEqualTo(questionTitle);
			}
	
	@Test
	public void testListQuestions() {
		List<Question> questions = (List<Question>) qrepo.findAll();
		
		for(Question question : questions) {
			System.out.println(question);
		}
		
		assertThat(questions).size().isGreaterThan(10);
	}
	
	@Test
	@Rollback(false)
	public void testDeleteQuestion() {
		Question question= qrepo.findByTitle("what is 4+4");
		Integer id = question.getQuesId();
		
		boolean isExistBeforeDelete = qrepo.findById(id).isPresent();
		qrepo.deleteById(id);
		
		boolean notExistAfterDelete = qrepo.findById(id).isPresent();
		
		assertTrue(isExistBeforeDelete);
		assertFalse(notExistAfterDelete);
		
	}
}
