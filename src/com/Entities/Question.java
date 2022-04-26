package com.Entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Question implements Serializable {
	private String questionTxt;

	private BigInteger q_id;
	

	private LinkedList<Alternative> alternatives;

	public Question() {
		
	}
	
	public Question(String qTxt, BigInteger qId) {
		questionTxt = qTxt;
		this.q_id = qId;
	}
	
	

	public BigInteger getQ_id() {
		return q_id;
	}

	public void setQ_id(BigInteger q_id) {
		this.q_id = q_id;
	}

	public String getQuestionTxt() {
		return questionTxt;
	}

	public void setQuestionTxt(String questionTxt) {
		this.questionTxt = questionTxt;
	}

	public void setAlternatives(LinkedList<Alternative> alternatives) {
		this.alternatives = alternatives;
	}

	public LinkedList<Alternative> getAlternatives() {
		return alternatives;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
