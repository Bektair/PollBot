package com.Entities;

public class Vote {

	private long discord_id;
	private int alt_id;

	public Vote(long discord_id, int alt_id) {
		this.discord_id = discord_id;
		this.alt_id = alt_id;
	}


	public long getDiscord_id() {
		return discord_id;
	}
	public void setDiscord_id(long discord_id) {
		this.discord_id = discord_id;
	}
	public int getAlt_id() {
		return alt_id;
	}
	public void setAlt_id(int alt_id) {
		this.alt_id = alt_id;
	}





}
