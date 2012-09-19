// Copyright 2008 Google Inc. All Rights Reserved.
package com.google.appengine.demos.helloorm;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author Max Ross <maxr@google.com>
 */
@PersistenceCapable(detachable = "true")
public class Flashcard {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String question;

	@Persistent
	private String answer;

	public Flashcard(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Long getId() {
		return id;
	}

}
