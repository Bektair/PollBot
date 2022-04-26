package com.pollBot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

import javax.swing.event.InternalFrameListener;

import org.apache.commons.collections4.Bag;

import com.Commands.*;

public class Commands extends ListenerAdapter {

	public void onGuildMessageReceived(MessageReceivedEvent event) {

		//I dont want bot messages or embeds to call this.
		if(event.getAuthor().isBot()) { return;}
	
		
		
		System.out.println(event.getMessage().getContentRaw() + "okay");

		String[] args = event.getMessage().getContentRaw().split("\\s+");
		String s = "haha";

		// Two ways of retriving emotes, secound one returns a list, can be multiple
		// with same names
		// Emote reaction =
		// event.getMessage().getGuild().getEmoteById("626389349273042945");
	
	
		//
		String searchword = "626389349273042945";
		Emote reaction;
		reaction = event.getMessage().getGuild().getEmoteCache().getElementById(searchword);
		
		if(reaction==null) {
			return;
		}
		
		String command = args[0];
		if (isCommand(command)) {
			//if(event.getAuthor().isBot()) { return;}
			
			TextChannel channel = event.getTextChannel();
			channel.sendTyping().queue();
			//System.out.println(command.substring(1));
			System.out.println(command.substring(1));
			switch (command.substring(1)) {
			case "guildemote":
				System.out.println("You must go on");
				updateGuildEmote.getEmotes(event);
				break;
			case "random":
				System.out.println("Random");
				RandomPoll.getPoll(event, channel);
				break;
			case  "website":
				channel.sendMessage(
						"https://discord.com/api/oauth2/authorize?client_id=868481819593551892&redirect_uri=http://localhost:8080/Daily_poll/login&response_type=code&scope=identify%20guilds")
						.queue(response -> {
							response.addReaction(reaction).queue();
						});
				break;
			case "poll":
				//Poll.command(event, channel); I think outdated
				break;
			case "yes":
				channel.sendMessage(
						"https://discord.com/api/oauth2/authorize?client_id=868481819593551892&redirect_uri=http://localhost:8080/Daily_poll/login&response_type=code&scope=identify%20guilds")
						.queue(response -> {
							response.addReaction("😄").queue();
						});
				break;
			}
		} else {
			System.out.println("No command");
			// not a command
		}

	}
	
	
	private boolean isCommand(String message) {
		if (message.startsWith(Main.getPrefix()))
			return true;
		return false;
	}

}
