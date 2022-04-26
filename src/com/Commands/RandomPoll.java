package com.Commands;

import utils.UTFConverter;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

import javax.naming.NamingException;

import com.pollBot.Main;

import databaseUpdate.dbConnect;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
//import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import config.discordVariables;

/* TODO Need to fix the usecase multiple questions in one poll or exclude these
 * You get random command
 * And this class tries to find random semi old poll
 * And send it as embed in discord channel
 * With the corresponding reactions
 */
public class RandomPoll {

	public static void getPoll(MessageReceivedEvent event, TextChannel channel) {

		//
		long monthsSinceAsked = 6;
		//new polls are over 2000 years since were asked
		long yearsSinceAsked = 2000;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //this is how dates are stored sql
		LocalDateTime now = LocalDateTime.now();
		String date = dtf.format(now.minusMonths(monthsSinceAsked));
		String newPollDate = dtf.format(now.minusYears(yearsSinceAsked));
		
		
		//discord_id
		// I want to get a random poll that is more than 6months old.
		String query = "Select poll_id, discord_id, if(last_asked<'"+newPollDate+"', '1', '0')as isNew FROM Poll WHERE last_asked<"+monthsSinceAsked+" ORDER BY isNew DESC, RAND() LIMIT 1;";
		System.out.println(query);
		// Select poll_id, discord_id, if(last_asked<'0021-03-08', "1", "0")as isNew FROM Poll WHERE last_asked<"2020-01-01" ORDER BY isNew DESC, RAND() LIMIT 1;
		
		
		//Select poll_id, discord_id, if(first_asked<'0021-03-08', "1", "0")as isNew FROM Poll ORDER BY isNew DESC, RAND() LIMIT 1;
		// select * from poll where last_asked > '2010-01-01' ORDER BY RAND() LIMIT 1;

		/*
		 * SELECT q_txt, alternative_txt, emoji_id, emote_id from poll_question pq INNER
		 * JOIN(alternative alt inner join question q on alt.q_id = q.q_id) ON pq.q_id =
		 * q.q_id where poll id = poll_id;
		 */
		
		Connection con = null;
		try {
			con = dbConnect.createConn();
			Statement st = con.createStatement();
			
			ResultSet r = st.executeQuery(query);
			r.next();
			int poll_id = r.getInt(1);
			Long discord_id = r.getLong(2);
			Statement st2 = con.createStatement();
			String queryStatement = "SELECT q_txt, alternative_txt, emoji_id, emote_id from poll_question pq"
					+ "	INNER JOIN(alternative alt inner join question q on alt.q_id = q.q_id)"
					+ "	ON pq.q_id = q.q_id where poll_id = " + poll_id;
			System.out.println(queryStatement);

			ResultSet r2 = st2.executeQuery(queryStatement);
			Stack<String> qStack = new Stack<>();
			EmbedBuilder eb = new EmbedBuilder();
			boolean first = true;
			StringBuilder alternative_txt = new StringBuilder();
			String[] utfReactions = new String[config.discordVariables.maxReactions];
			Emote[] customReactions = new Emote[config.discordVariables.maxReactions];
			int currentUtfReaction = 0;
			int currentEmoteReaction = 0;
			
			
			while (r2.next()) {
				String qstr = r2.getString(1);
				System.out.println("Do you even have rows " + qstr);
				if (qStack.isEmpty()) {
					qStack.push(qstr);
				}
				if (!qStack.peek().equals(qstr)) {
					System.out.println("multiquestion");
					// if new question then que previous question as embed
					// and make a new one
					eb.addField(qStack.peek(), alternative_txt.toString(), true);
					// System.out.println(alternative_txt.toString());
					alternative_txt.setLength(0);
					eb.setTitle(now.toLocalDate().toString());
					channel.sendMessageEmbeds(eb.build()).queue();
					eb.clear();
					qStack.push(qstr);
				}
			
				int emoji_id = r2.getInt(3);
				if (emoji_id > 0) {
					//emojii_id is the codepoint
					System.out.println(emoji_id);
					System.out.println(Character.toChars(emoji_id));
					
					// time to convert into hex
					// UTF 32 hex can be made doing this
					String emojistr = Long.toHexString(emoji_id);
					//I get the last digits but how do I add the first
					int i = 8-emojistr.length();
					StringBuilder emojii = new StringBuilder();
					emojii.append("U+");
					for(int j = 0; j<i; j++) {
						emojii.append("0");
					}
					emojii.append(emojistr);
					emojistr = emojii.toString();
					
					//then from hexstring to character
					
					//eb.addField(emojistr, "Yes", false);		
					
					//UTF16 getvalue
					char[] heyhey = Character.toChars(emoji_id);
					alternative_txt.append(String.valueOf(heyhey));
					
					//I am supposed to ahve this work in the U+2B05" format
					utfReactions[currentUtfReaction++]=emojistr;
					
					System.out.println(emojistr);
					
				}

				long emote_id = r2.getLong(4);
				if(emote_id > 0) {
					Emote e= event.getMessage().getGuild().getEmoteCache().getElementById(emote_id);
					customReactions[currentEmoteReaction++] = e;
					System.out.println("id " + e.getId());
					System.out.println("name " + e.getName());
					//the discord format <:name:id>
					alternative_txt.append("<:"+e.getName()+":"+e.getId()+">");
				}
				
				alternative_txt.append(r2.getString(2));
				alternative_txt.append("\n");

			
			}
			
			eb.addField(qStack.peek(), alternative_txt.toString(), true);
			eb.setTitle(now.toLocalDate().toString());
			
			//discord_id.toString();
			
			JDA jda = Main.getJDA();
			User user = jda.getUserById(discord_id);
		
			//TODO consider using the 2nd param to link somewer
			eb.setAuthor(user.getAsTag(), "https://discord.com/channels/341016948265713675/885214334853185578/885214337436909568", user.getAvatarUrl());
			//eb.setAuthor("I am great author");
			System.out.println(alternative_txt.toString());
			
			int totalUtfReactions = currentUtfReaction;
			int totalCustomReactions = currentEmoteReaction;
			//need to add reactions in here
			channel.sendMessageEmbeds(eb.build())
			.queue(response -> {
						for(int l = 0; l < totalCustomReactions; l++) {
							response.addReaction(customReactions[l]).queue();
						}
						for(int o = 0; o < totalUtfReactions; o++) {
							response.addReaction(utfReactions[o]).queue();
						}
					}
			);
		} catch (SQLException|IOException|ClassNotFoundException | NamingException e1) {
			e1.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
