package com.pollBot;




import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class Main {

	private static JDA jda;
	private static String prefix = "~";


	
	public static JDA getJDA() {
		return jda;
	}
	
	public static String getPrefix() {
		return prefix;
	}
	
	public static void main(String[] args) throws LoginException, IOException, InterruptedException {
		
		Properties prop = new Properties();
		InputStream in = Main.class.getClassLoader()
        .getResourceAsStream("token.properties");
		prop.load(in);
		
		String token = prop.getProperty("token");
		System.out.println(token);
		
		jda = JDABuilder.createDefault(token)
		.setAutoReconnect(true)
		.setActivity(Activity.playing("Eats fish"))
		.addEventListeners(new Commands())
		.setStatus(OnlineStatus.ONLINE)
		.build();
		
		jda.awaitReady();

		System.out.println("bot is on");
		System.out.println(jda.getAccountType());
		
		
		
		
		
	}

	
	
	
	private void UpdateDb() {
		
		
	}

}
