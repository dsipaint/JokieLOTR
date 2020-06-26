package com.github.dsipaint.listeners;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.github.dsipaint.main.Main;
import com.github.dsipaint.main.Utils;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter
{
	private ArrayList<String> ids;
	private final String FILE_LOCATION = "users.dat";
	
	public CommandListener()
	{		
		ids = new ArrayList<String>();
		
		try
		{
			//read in discord ids
			BufferedReader br = new BufferedReader(new FileReader(new File(FILE_LOCATION)));
			String id = "";
			
			while((id = br.readLine()) != null)
				ids.add(id);
			
			br.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		String msg = e.getMessage().getContentRaw();
		String args[] = msg.split(" ");
		
		//  ^lotr
		if(args[0].equalsIgnoreCase(Main.PREFIX + "lotr"))
		{
			//if discord staff
			if(args.length == 1 && Utils.isStaff(e.getMember()))
			{
				String lotr_announce = "";
				
				for(String id : ids)
					lotr_announce += "<@!" + id + "> ";
				
				lotr_announce += "\nJokie is doing a reading of LOTR!! Come on and join him in the vc!";
				lotr_announce += "\n\n*(to be notified for future readings, use the command \"" 
						+ Main.PREFIX +  "lotr add\", and use \"" + Main.PREFIX + "lotr remove\""
						+ " to be removed from the announcements!)*";
				e.getChannel().sendMessage(lotr_announce).queue();
				
				return;
			}
			else
			{
				// ^lotr remove
				if(args[1].equalsIgnoreCase("remove"))
				{
					//if user is already in list
					if(ids.contains(e.getAuthor().getId()))
					{
						ids.remove(e.getAuthor().getId()); //remove them from the list
						e.getChannel().sendMessage("Removed " + e.getMember().getEffectiveName()
								+ " from Jokie LOTR pings.").queue();
					}
					else
						e.getChannel().sendMessage("You are not in the Jokie LOTR ping list!").queue();
					
					return;
				}
				
				// ^lotr add
				if(args[1].equalsIgnoreCase("add"))
				{
					//if user is not already in list
					if(!ids.contains(e.getAuthor().getId()))
					{
						ids.add(e.getAuthor().getId()); //add them to list
						e.getChannel().sendMessage("Added " + e.getMember().getEffectiveName()
								+ " to Jokie LOTR pings.").queue();
					}
					else
						e.getChannel().sendMessage("You are already in the Jokie LOTR ping list!").queue();
					
					return;
				}
				
				return;
			}
		}
	}
	
	//write arraylist to file when we close the listener
	public void close() throws IOException
	{
		PrintWriter pw = null;
		
		pw = new PrintWriter(new FileWriter(new File(FILE_LOCATION)));
		
		//write arraylist to file
		for(String id : ids)
			pw.println(id);
		
		pw.close();
	}
}
