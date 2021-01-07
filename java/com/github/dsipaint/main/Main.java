package com.github.dsipaint.main;
import javax.security.auth.login.LoginException;

import com.github.dsipaint.listeners.CommandListener;
import com.github.dsipaint.listeners.StopListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main
{
	public static JDA jda;
	public static final String PREFIX = "^";
	public static CommandListener commandlistener;
	
	public static void main(String[] args)
	{
		try
		{
			jda = JDABuilder.createDefault("")
					.enableIntents(GatewayIntent.GUILD_MEMBERS)
					.setMemberCachePolicy(MemberCachePolicy.ALL)
					.build();
		}
		catch (LoginException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			jda.awaitReady();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		commandlistener = new CommandListener();
		jda.addEventListener(commandlistener);
		jda.addEventListener(new StopListener());
	}
}
