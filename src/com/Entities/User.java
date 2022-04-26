package com.Entities;

public class User {
	boolean isAdmin;
	String discord_nickname;
	String discord_id;
	String avatar;



	public User(boolean isAdmin, String discord_nickname, String discord_id, String avatar) {
		super();
		this.isAdmin = isAdmin;
		this.discord_nickname = discord_nickname;
		this.discord_id = discord_id;
		this.avatar = avatar;
	}

	public User() {

	}

	@Override
	public String toString() {
		return "User [discord_id=" + discord_id + ", discord_nickname=" + discord_nickname + ", avatar=" + avatar
				+ ", isAdmin=" + isAdmin + "]";
	}
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getDiscord_nickname() {
		return discord_nickname;
	}
	public void setDiscord_nickname(String discord_nickname) {
		this.discord_nickname = discord_nickname;
	}
	public String getDiscord_id() {
		return discord_id;
	}
	public void setDiscord_id(String discord_id) {
		this.discord_id = discord_id;
	}





}
