package com.github.dsipaint.main;
import net.dv8tion.jda.api.entities.Member;

public class Utils
{
	// returns true if member has discord mods role or is the server owner
	public static boolean isStaff(Member m)
	{
		//if has discord mods role or is owner
		if(m.getRoles().contains(m.getGuild().getRoleById("565626094917648386"))
				|| m.isOwner())
			return true;
		
		return false;
	}
}
