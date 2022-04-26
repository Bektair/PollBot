package com.Commands;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import com.Entities.Alternative;
import com.Entities.Question;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/*
 * I think outdated unless I come up with something
 */
public class PostPoll {

	
	
	public static void command(MessageReceivedEvent event, TextChannel channel) {
		System.out.println("Starts something");
		
		Question q = getQuestion();
		EmbedBuilder eb = new EmbedBuilder();
		
		String questiontxt = q.getQuestionTxt();
		eb.setTitle(questiontxt);
		
		LinkedList<Alternative> alt = q.getAlternatives();
		String[] emo = null; // changed
		
		for(int i = 0; i < alt.size(); i++) {
			System.out.println(emo[i]);
			//List<Emote> reaction = event.getMessage().getGuild().getEmoteCache().getElementsByName(emo[i]);
			List<Emote> reaction = event.getMessage().getGuild().getEmoteCache().asList();
			
			
			if(reaction.size() > 0)
				eb.addField(alt.get(i).getAlt_txt(),reaction.get(0).toString(),false);
		}
		
		channel.sendMessageEmbeds(eb.build()).queue();	
		
		
		
		
	}
	
	//will in the future fetch a random question from the db :-)
	private static Question getQuestion() {
		
		String[] emo = {":green_circle:", ":blue_circle:", ":red_circle:"};
		String[] alt = {"green", "blue", "red"};
		
		//alt emo
		Question q = new Question("Whats the finest color?", BigInteger.ZERO);
		
		
		
		return q;
	}
	
	
}
