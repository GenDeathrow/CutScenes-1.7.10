package com.gendeathrow.cutscene.core.proxies;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public boolean isClient()
	{
		return false;
	}
	
	public boolean isOpenToLAN()
	{
		return false;
	}
	
	public void preInit(FMLPreInitializationEvent event) 
	{

		
	}

	public void init(FMLInitializationEvent event) 
	{
		registerEventHandlers();
		registerTickHandlers();

	}

	public void postInit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void registerTickHandlers() 
	{
		//FMLCommonHandler.instance().bus().register(new ServerTick());
			
	}

	public void registerEventHandlers() 
	{
//		EventHandler eventManager = new EventHandler();
//		MinecraftForge.EVENT_BUS.register(eventManager);
//		FMLCommonHandler.instance().bus().register(eventManager);
	}
	
	public void registerRenders()
	{
		
	}

}
