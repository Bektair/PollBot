package com.Entities;

public class Emoji {

	private String eName;
	private String emoji_id;

	public Emoji(String eName, String emoji_id) {
		this.eName = eName;
		this.emoji_id = emoji_id;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getEmoji_id() {
		return emoji_id;
	}

	public void setEmoji_id(String emoji_id) {
		this.emoji_id = emoji_id;
	}

	@Override
	public String toString() {
		return emoji_id+";"+eName;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
