package com.Commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import javax.naming.NamingException;

import com.pollBot.Main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import databaseUpdate.dbConnect;

/* Updates the DB with the current servers local specific emotes
 * 
 * 
 */
public class updateGuildEmote {

	
	public static void getEmotes(MessageReceivedEvent event) {	
		List<Emote> reaction = event.getGuild().getEmoteCache().asList();
		List<String> list = new ArrayList<>();
		for(Emote e : reaction) {
			String s = e.toString(); //E:FinishHim4(639997224515796993)
			list.add(s.substring(s.indexOf(':')+1, s.indexOf('(')));
			list.add(s.substring(s.indexOf('(')+1, s.lastIndexOf(')')));
		}

		updateDB(list);
		//Save to cache
		String path = System.getenv("POLL_ASSETS")+"emotes.txt";
		
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(path)));
			int count = 0;
			
			for(String str : list) {
				out.write(str);
				
				count++;
				if(count%2==0) {
					out.println();
				}else {
					out.write(',');
				}
			}
			out.close();
		
		BufferedReader buff = new BufferedReader(new FileReader(path));
		String line;
		while((line = buff.readLine()) != null) {
			System.out.println(buff.readLine());
		}
		buff.close();
			
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		

	}

	private static void updateDB(List<String> list) {
		Connection con = null;
		Statement st = null;
		try {
			con = dbConnect.createConn();
			st = con.createStatement(); //used to be inside if it got ruined
			for(int i = 0; i < list.size(); i=i+2) {
				String query = "insert ignore into emote(emote_name, emote_id)"
						+ "values('"+
						list.get(i)+"', "+list.get(i+1)+");";
				st.executeUpdate(query); //Do I have to remake statements each loop?
			}
		} catch (SQLException | IOException |ClassNotFoundException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(con!=null)
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(st!=null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	

	public static void clearFolder() {

		Path pathDir = Paths.get(System.getenv("POLL_ASSETS")+
				"\\guildEmotes");
		Stream<Path> allFiles = null;
		try {
			allFiles = Files.list(pathDir);
			allFiles.forEach((k)->{
				System.out.println(k.toString());
				try {
					Files.deleteIfExists(k);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			allFiles.close();
			
		}
	}
	
	public static void readEmoji() {
		clearFolder();
		
		String path = System.getenv("POLL_ASSETS")+"emotes.txt";
		BufferedReader buff;
		
		try {
			buff = new BufferedReader(new FileReader(path));
			String line;
			while((line = buff.readLine()) != null) {
				//name,keyvalue
				//https://cdn.discordapp.com/emojis/639997224515796993.png?v=1
				String code = line.split(",")[1];
				String name = line.split(",")[0];
				String url = "https://cdn.discordapp.com/emojis/"
							+ code +".png?v=1";
				//This part is from a guide https://www.baeldung.com/java-download-file
				InputStream in = 
						new URL(url).openStream();
						Path pathType = Paths.get(System.getenv("POLL_ASSETS")+
								"\\guildEmotes\\"+code+".png");
						Files.copy(in, pathType, 
								StandardCopyOption.REPLACE_EXISTING); 
				System.out.println(line);
			}
			buff.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		readEmoji();
	}


}