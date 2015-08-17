package com.gendeathrow.cutscene.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.utils.IOUtils;

import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler 
{
	
	static File configDir;

	public ConfigHandler(FMLPreInitializationEvent event)
	{
		this.configDir = event.getModConfigurationDirectory();
	}
	
	public void load()
	{
	      
		    File configFolder = new File(configDir, "CustomCutScenes");

		    if (!configFolder.exists())
		    {
		    	configFolder.mkdir();
		    	new File(configFolder, "assets").mkdir();
		    }
		 
		    File splashScreenConfig = new File(configFolder, "splashscreen.json");
		    
		    if (!splashScreenConfig.exists())
		    {
		      InputStream input = null;

		      OutputStream output = null;
		      try
		      {
		        output = new FileOutputStream(splashScreenConfig);
		        input = getClass().getResourceAsStream("/assets/customcutscene/splashscene_default.json");
		        ByteStreams.copy(input, output);
		        
		        output = new FileOutputStream(new File(configFolder, "/assets/logo.png"));
		        input = getClass().getResourceAsStream("/assets/customcutscene/logo.png");
		        ByteStreams.copy(input, output);
		        
		        output = new FileOutputStream(new File(configFolder, "/assets/cat1.png"));
		        input = getClass().getResourceAsStream("/assets/customcutscene/cat1.png");
		        ByteStreams.copy(input, output);
		        
		        output = new FileOutputStream(new File(configFolder, "/assets/cat2.png"));
		        input = getClass().getResourceAsStream("/assets/customcutscene/cat2.png");
		        ByteStreams.copy(input, output);
		        
		        output = new FileOutputStream(new File(configFolder, "/assets/world.png"));
		        input = getClass().getResourceAsStream("/assets/customcutscene/world.png");
		        ByteStreams.copy(input, output);
		      }
		      catch (FileNotFoundException e1)
		      {
		        e1.printStackTrace();
		      }
		      catch (IOException e)
		      {
		        e.printStackTrace();
		      }
		      finally
		      {
		        IOUtils.closeQuietly(output);
		        IOUtils.closeQuietly(input);
		      }
		    }
	}
	
}
