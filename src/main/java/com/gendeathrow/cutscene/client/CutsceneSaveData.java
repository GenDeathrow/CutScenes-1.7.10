package com.gendeathrow.cutscene.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ReportedException;

import org.apache.logging.log4j.Level;

import com.gendeathrow.cutscene.core.CutScene;
import com.gendeathrow.cutscene.core.Settings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CutsceneSaveData 
{
	/**
	 * Configuration version number. If changed the version file will be reset to defaults to prevent glitches
	 */
	static final String CONFIG_VERSION = "1.0.0";
	
	/**
	 * The version of the configs last loaded from file. This will be compared to the version number above when determining whether a reset is necessary
	 */
	static String LOADED_VERSION = "1.0.0";
	
    //protected static final String dirName = Minecraft.getMinecraft().mcDataDir+"";
    
   // protected static File dir = new File(dirName);

    protected static final String dirName = null;
    
    protected static File dir = null;

	
    public static String SettingsData = "CutScene_Settings";
    
    private static int loadAttempts = 1;
    
    
    public static boolean loadConfig(String name) {
        return loadConfig(name, null);
    }

    public static boolean loadConfig(String name, String dirName) 
    {
//        if (dirName == null) 
//        {
////        	dir = new File(Minecraft.getMinecraft().mcDataDir.getPath() + "saves" + File.separator + Minecraft.getMinecraft().getIntegratedServer().getFolderName());
//        }
//
//      //File file = new File(dir, fileName);

        String fileName = name + ".dat";
        
        Minecraft mc = Minecraft.getMinecraft();
        File file = null;
        if(mc.isIntegratedServerRunning())
        {
        	System.out.println(Minecraft.getMinecraft().mcDataDir.getPath()+  "saves" + File.separator + Minecraft.getMinecraft().getIntegratedServer().getFolderName() + File.separator + fileName);
        	   file = new File(Minecraft.getMinecraft().mcDataDir.getPath(),  "saves" + File.separator + Minecraft.getMinecraft().getIntegratedServer().getFolderName() + File.separator + fileName);  	
        }else if(MinecraftServer.getServer() != null)
        {
        	CutScene.logger.log(Level.WARN, "Servers dont save/load data for what cutscenes a player has seen. Yet!!");
        	return false;
        	//System.out.println(Minecraft.getMinecraft().mcDataDir.getPath()+  "saves" + File.separator +  MinecraftServer.getServer().getFolderName() + File.separator + fileName);
        	//  file = new File(Minecraft.getMinecraft().mcDataDir.getPath(),  "saves" + File.separator + MinecraftServer.getServer().getFolderName() + File.separator + fileName);
        }
        
      
        if(file == null) return false;

        if (!file.exists()) 
        {
            CutScene.logger.warn("Config load canceled, file ("+ file.getAbsolutePath()  +")does not exist. This is normal for first run.");
            if(loadAttempts <= 3)
            {
            	CutScene.logger.warn(loadAttempts +" Attempt to load....");
            	loadAttempts++;
            	
            	saveConfig(name);
            	loadConfig(name);
            }
            return false;
        } else {
        	CutScene.logger.info("Config load successful.");
        }
        try {
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(new FileInputStream(file));
            
            if (nbt.hasNoTags() || !nbt.hasKey(SettingsData))
            {
            	return false;
            }

           Settings.hasPlayedSeenLogin = nbt.getCompoundTag(SettingsData).getBoolean("hasSeenLogin");
            		  
           LOADED_VERSION = nbt.getCompoundTag(SettingsData).getString("CONFIG_VERSION");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return LOADED_VERSION.equals(CONFIG_VERSION);
    }

    public static void saveConfig(String name) {
        saveConfig(name, null);
    }

    public static void saveConfig(String name, String dirName) {

       String fileName = name + ".dat";
       File file = new File(dir, fileName);
  
       Minecraft mc = Minecraft.getMinecraft();
       
       if(mc.isIntegratedServerRunning())
       {
       	   file = new File(Minecraft.getMinecraft().mcDataDir.getPath(),  "saves" + File.separator + Minecraft.getMinecraft().getIntegratedServer().getFolderName() + File.separator + fileName);  	
       }else if(MinecraftServer.getServer() != null)
       {
    	   CutScene.logger.log(Level.WARN, "Servers dont save/load data for what cutscenes a player has seen. Yet!!");
    	   return;
       	  //file = new File(Minecraft.getMinecraft().mcDataDir.getPath(),  "saves" + File.separator + MinecraftServer.getServer().getFolderName() + File.separator + fileName);
       }
       
       //file = new File(Minecraft.getMinecraft().mcDataDir.getPath(),  "saves" + File.separator + Minecraft.getMinecraft().getIntegratedServer().getFolderName() + File.separator +fileName);

       try {
            NBTTagCompound nbt = new NBTTagCompound();
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            NBTTagCompound globalNBT = new NBTTagCompound();

            globalNBT.setString("CONFIG_VERSION", CONFIG_VERSION); // VERY IMPORTANT
            
            globalNBT.setBoolean("hasSeenLogin", Settings.hasPlayedSeenLogin);

            nbt.setTag(SettingsData, globalNBT);
            	
            CompressedStreamTools.writeCompressed(nbt, fileOutputStream);
            
            fileOutputStream.close();
            CutScene.logger.info("Saved cut scene properties");
        } catch (IOException e) {
            throw new ReportedException(new CrashReport("An error occured while saving", new Throwable()));
        }
    }

    public static File[] getConfigs() {
        return dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".dat");
            }
        });
    }

}
