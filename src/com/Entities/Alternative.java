package com.Entities;

import java.math.BigInteger;



public class Alternative {
	String alt_txt;
	String emoji_id;
	Boolean isEmoji;

	BigInteger alt_id;
	Boolean hasAnswered;
	
	private Question question;

	
	public Alternative() {
		
	}
	
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setAlt_txt(String alt_txt) {
		this.alt_txt = alt_txt;
	}

	public void setEmoji_id(String emoji_id) {
		this.emoji_id = emoji_id;
	}

	public void setIsEmoji(Boolean isEmoji) {
		this.isEmoji = isEmoji;
	}

	public Alternative(String alt_txt, String emoji_id, boolean isEmoji, BigInteger alt_id, boolean hasAnswered) {
		this.alt_txt = alt_txt;
		this.emoji_id = emoji_id;
		this.isEmoji = isEmoji;
		this.alt_id = alt_id;
		this.hasAnswered = hasAnswered;
	}

	public String getAlt_txt() {
		return alt_txt;
	}


	public String getEmoji_id() {
		return emoji_id;
	}

	public Boolean getHasAnswered() {
		return hasAnswered;
	}


	public void setHasAnswered(Boolean hasAnswered) {
		this.hasAnswered = hasAnswered;
	}
	public Boolean getIsEmoji() {
		return isEmoji;
	}




	public BigInteger getAlt_id() {
		return alt_id;
	}


	public void setAlt_id(BigInteger alt_id) {
		this.alt_id = alt_id;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
