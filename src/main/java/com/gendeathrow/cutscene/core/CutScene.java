package com.gendeathrow.cutscene.core;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.gendeathrow.cutscene.SceneRender.SceneObject;
import com.gendeathrow.cutscene.SceneRender.SegmentObject;
import com.gendeathrow.cutscene.core.proxies.CommonProxy;
import com.gendeathrow.cutscene.utils.GsonReader;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = CutScene.MODID, version = CutScene.VERSION, name = CutScene.Name)
public class CutScene
{
	    public static final String MODID = "cutscene";
	    public static final String VERSION = "GD_CS_VER";
	    public static final String Name = "CutScene";
	    public static final String Proxy = "com.gendeathrow.cutscene.core.proxies";
	    public static final String Channel = "SK_GenD";
		private static final String GsonRea = null;
	    
	    public static  Logger logger;
	    
	    @Instance(CutScene.MODID)
	    public static CutScene instance;
	    
		@SidedProxy(clientSide = CutScene.Proxy + ".ClientProxy", serverSide = CutScene.Proxy + ".CommonProxy")
		public static CommonProxy proxy;

		public SimpleNetworkWrapper network;
		
		public ConfigHandler config;
		
		@EventHandler
		public void preInit(FMLPreInitializationEvent event)
		{
			System.out.println("VERSION:"+ VERSION);
			
			logger = event.getModLog();
			
			this.config = new ConfigHandler(event);
		    try
		    {
		      this.config.load();
		    }
		    catch (Exception e)
		    {
		      this.logger.log(Level.ERROR, "Error while loading config file. Why does this always happen");
		      throw new RuntimeException(e);
		    }
		    
			
			
			proxy.preInit(event);

			//this.network = NetworkRegistry.INSTANCE.newSimpleChannel(Channel);
			//this.network.registerMessage(PacketSkillz.HandlerServer.class, PacketSkillz.class, 0, Side.SERVER);
			//this.network.registerMessage(PacketSkillz.HandlerClient.class, PacketSkillz.class, 1, Side.CLIENT);
			
//			PacketDispatcher.registerPackets();
			
		}
	    @EventHandler
	    public void init(FMLInitializationEvent event)
	    {
	    	proxy.init(event);
	    }
	    @EventHandler
		public void postInit(FMLPostInitializationEvent event)
		{
			proxy.postInit(event);
			
//			//SceneObject test = new GsonReader().GsonReadFromFile(null);
//			
//			//System.out.println("Closeing Ticks"+ test.closeOnTicks +"  bk:"+ test.defaultBackgroundColor);
//			
//			ArrayList segs = test.screenSegments;
//			
//			for(Object seg : segs)
//			{
//				
//				
////				System.out.println(seg.getClass().getSimpleName());
////				SegmentObject bes = (SegmentObject) seg;
////				System.out.println("segments:"+ bes.getTickLength());
//				
//			}
//////			Iterator Segments = test.getSegmentIterator();
////			while(Segments.hasNext())
////			{
////				SegmentObject segment = (SegmentObject) Segments.next();
////				
////				System.out.println("Segment: "+ segment.getTickLength());
////			}
			
		}
		
		@EventHandler
		public void serverStart(FMLServerStartingEvent event)
		{
			//MinecraftServer server = MinecraftServer.getServer();
			//ICommandManager command = server.getCommandManager();
			//ServerCommandManager manager = (ServerCommandManager) command;
			
//			manager.registerCommand(new CommandPhysics());

		}
}

