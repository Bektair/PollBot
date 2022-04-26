package com.Entities;

public class Emote {

	private String emote_id;
	private String emoteName;

	public Emote(String emote_id, String emoteName) {
		this.emote_id = emote_id;
		this.emoteName = emoteName;
	}

	public String getEmote_id() {
		return emote_id;
	}
	public void setEmote_id(String emote_id) {
		this.emote_id = emote_id;
	}
	public String getEmoteName() {
		return emoteName;
	}
	public void setEmoteName(String emoteName) {
		this.emoteName = emoteName;
	}




}
