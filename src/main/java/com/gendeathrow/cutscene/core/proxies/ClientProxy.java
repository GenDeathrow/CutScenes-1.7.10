package com.gendeathrow.cutscene.core.proxies;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import com.gendeathrow.cutscene.client.ClientTickHandler;
import com.gendeathrow.cutscene.client.Gui_EventHandler;
import com.gendeathrow.cutscene.utils.Utils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy{


	private final Minecraft mc = Minecraft.getMinecraft();

	@Override
	public boolean isClient() {
		return true;
	}

	@Override
	public boolean isOpenToLAN() {
		if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
			return Minecraft.getMinecraft().getIntegratedServer().getPublic();
		} else {
			return false;
		}
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		// TODO Auto-generated method stub
		
		Utils.LoadResources();
		//SoundAssist.LoadAlData();
		
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {

		super.init(event);
		
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub
		super.postInit(event);
		
		

	}

	@Override
	public void registerTickHandlers() 
	{
		super.registerTickHandlers();
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());

	}

	@Override
	public void registerEventHandlers() {

		super.registerEventHandlers();

		
		MinecraftForge.EVENT_BUS.register(new Gui_EventHandler());
		//MinecraftForge.EVENT_BUS.register(new SaveData());
		
//	
//		UpdateNotification updateManager = new UpdateNotification();
//		MinecraftForge.EVENT_BUS.register(updateManager);
//		FMLCommonHandler.instance().bus().register(updateManager);
//		
//		SkillzKeybinds keybindManager = new SkillzKeybinds();
//		keybindManager.register();
//		
//		MinecraftForge.EVENT_BUS.register(keybindManager);
//		FMLCommonHandler.instance().bus().register(keybindManager);


		
		super.registerEventHandlers();
	}
	
	@Override
	public void registerRenders()
	{

	}

}
