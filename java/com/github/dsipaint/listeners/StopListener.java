package com.github.dsipaint.listeners;
import java.io.IOException;

import com.github.dsipaint.main.Main;
import com.github.dsipaint.main.Utils;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StopListener extends ListenerAdapter
{
	public void onGuildMessageReceived(GuildMessageReceivedEvent e)
	{
		if(Utils.isStaff(e.getMember()))
		{
			String msg = e.getMessage().getContentRaw();
			String[] args = msg.split(" ");
			
			// ^disable lotr
			if(args[0].equalsIgnoreCase(Main.PREFIX + "disable")
					&& args[1].equalsIgnoreCase("lotr"))
			{
				e.getChannel().sendMessage("*Disabling al's lotr code...*").queue();
				
				try
				{
					Main.commandlistener.close();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
				
				Main.jda.shutdown();
				System.exit(0);
			}
			
			// ^shutdown
			if(args[0].equalsIgnoreCase(Main.PREFIX + "shutdown"))
			{
				Main.jda.shutdownNow();
				System.exit(0);
			}
		}
	}
}
