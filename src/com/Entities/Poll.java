package com.Entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/*
 * Should represent one table in the Database
 * Each instance would be one row  
 * 
 * 	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
 * 
 * Can hold other entities or collections of entities.
 * Using java.util.Collection/Set/List/Map
 *  
 * Can be annotated with persistent fields or properties
 */

public class Poll implements Serializable {


	private Date first_asked;
	private Date last_asked;
	private BigInteger poll_id;

	//ZoneId standardZone = ZoneId.of("Europe/Paris"); //Save standardZone somewere?
	//java does not like array of objects so lets do linkedlist

	Collection<Question> questions;

	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}

	public Poll() {
		
	}
	
	//make empty poll
	public Poll(Date first, Date last, BigInteger poll_id) {
		//test with regex

		setFirst_asked(first);
		setLast_asked(last);
		this.poll_id = poll_id;

		//questions= new Map<>();
	}





	//I will use LocalDate as we dont need to be perfectly precise

	public BigInteger getPoll_id() {
		return poll_id;
	}



	public void setPoll_id(BigInteger poll_id) {
		this.poll_id = poll_id;
	}



	public Date getFirst_asked() {
		return first_asked;
	}



	public void setFirst_asked(Date first_asked) {
		isPast(first_asked); //Date gotta be in past
		this.first_asked = first_asked;


	}



	public Date getLast_asked() {
		return last_asked;
	}



	public void setLast_asked(Date last_asked) {
		isPast(last_asked); //Date gotta be in past
		this.last_asked = last_asked;
	}



	private void isPast(Date date) {
//		if(!date.isAfter(Date.now(standardZone))) {
//			return;
//		}
//		else {
//			throw new IllegalArgumentException("You cannot set date as future day(compared to Paris Timezone)");
//		}
	}

	public Collection<Question> getQuestions() {
		return questions;
	}


	
	



	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LinkedList<String> hello = new LinkedList<>();


	}

}
