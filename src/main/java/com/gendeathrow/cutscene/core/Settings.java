package com.gendeathrow.cutscene.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Settings 
{

	
	public static boolean update_resources = true;
	public static boolean dev_Enviroment = true;
	
	public static boolean trigger_OnPlayer_Login = false;
	public static boolean trigger_OnPlayer_Login_Always = false;
	public static String file_OnPlayer_Login = "login.json";
	
	
	
	@SideOnly(Side.CLIENT)
	public static boolean hasPlayedSeenLogin = false;
	
}
