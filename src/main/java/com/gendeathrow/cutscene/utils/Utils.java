package com.gendeathrow.cutscene.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;

import com.gendeathrow.cutscene.core.Settings;

public class Utils 
{
	
	
	
	public static String getTranslation(String[] textList)
	{
		Language currentLang = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();
		
		return null;
	}
	

	public static String getTimeFormater(long millis)
	{
		
		long mill = (millis) % 1000;		
		long second = (millis / 1000) % 60;
		long minute = (millis / (1000 * 60)) % 60;

		String time = String.format("%02d:%02d:%d", minute, second, mill);

		return time;
	}

	public static void LoadResources()
	{
	   try
	    {
	      ArrayList soundAssets = new ArrayList();
	      ArrayList imagesAssets = new ArrayList();
	      ArrayList langAssets = new ArrayList();

	      File f = new File("custom-cutscene-files");
	      File[] listFiles = f.listFiles();
	      
	      if (listFiles != null)
	      {
	        for (int i = 0; i < listFiles.length; i++)
	        {
	          if (listFiles[i].getName().endsWith(".ogg"))
	          {
	            soundAssets.add(listFiles[i].getName().substring(0, listFiles[i].getName().length() - 4));

	          }else if(listFiles[i].getName().endsWith(".png"))
	          {
	        	  imagesAssets.add(listFiles[i].getName().substring(0, listFiles[i].getName().length() - 4));
	          }else if(listFiles[i].getName().endsWith(".lang"))
	          {
	        	  langAssets.add(listFiles[i].getName().substring(0, listFiles[i].getName().length() - 5));
	          }
	          
	        }
	      }

	      if (Settings.update_resources)
	      {
	        JarOutputStream out = new JarOutputStream(new FileOutputStream("mods/custom_cutscene_files.jar"));
	        File json = new File("mods/sounds.json");
	        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(json), "UTF-8"));

	        writer.write(123);
	        writer.newLine();

	        byte[] buf = new byte[1024];
	        out.putNextEntry(new JarEntry("assets/"));
	        out.putNextEntry(new JarEntry("assets/ccsfiles/"));
	        out.putNextEntry(new JarEntry("assets/ccsfiles/sounds/"));
	        out.putNextEntry(new JarEntry("assets/ccsfiles/textures/"));
	        out.putNextEntry(new JarEntry("assets/ccsfiles/textures/gui/"));
	        out.putNextEntry(new JarEntry("assets/ccsfiles/lang/"));

	        byte[] buf2 = {-54, -2, -70, -66, 0, 0, 0, 50, 0, 33, 1, 0, 25, 99, 99, 115, 102, 105, 108, 101, 115, 47, 99, 111, 109, 109, 111, 110, 47, 66, 97, 115, 101, 67, 108, 97, 115, 115, 7, 0, 1, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 7, 0, 3, 1, 0, 14, 66, 97, 115, 101, 67, 108, 97, 115, 115, 46, 106, 97, 118, 97, 1, 0, 25, 76, 99, 112, 119, 47, 109, 111, 100, 115, 47, 102, 109, 108, 47, 99, 111, 109, 109, 111, 110, 47, 77, 111, 100, 59, 1, 0, 5, 109, 111, 100, 105, 100, 1, 0, 8, 99, 99, 115, 102, 105, 108, 101, 115, 1, 0, 7, 118, 101, 114, 115, 105, 111, 110, 1, 0, 1, 48, 1, 0, 36, 99, 112, 119, 47, 109, 111, 100, 115, 47, 102, 109, 108, 47, 99, 111, 109, 109, 111, 110, 47, 77, 111, 100, 36, 69, 118, 101, 110, 116, 72, 97, 110, 100, 108, 101, 114, 7, 0, 11, 1, 0, 23, 99, 112, 119, 47, 109, 111, 100, 115, 47, 102, 109, 108, 47, 99, 111, 109, 109, 111, 110, 47, 77, 111, 100, 7, 0, 13, 1, 0, 12, 69, 118, 101, 110, 116, 72, 97, 110, 100, 108, 101, 114, 1, 0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 12, 0, 16, 0, 17, 10, 0, 4, 0, 18, 1, 0, 4, 116, 104, 105, 115, 1, 0, 27, 76, 99, 99, 115, 102, 105, 108, 101, 115, 47, 99, 111, 109, 109, 111, 110, 47, 66, 97, 115, 101, 67, 108, 97, 115, 115, 59, 1, 0, 7, 112, 114, 101, 73, 110, 105, 116, 1, 0, 56, 40, 76, 99, 112, 119, 47, 109, 111, 100, 115, 47, 102, 109, 108, 47, 99, 111, 109, 109, 111, 110, 47, 101, 118, 101, 110, 116, 47, 70, 77, 76, 80, 114, 101, 73, 110, 105, 116, 105, 97, 108, 105, 122, 97, 116, 105, 111, 110, 69, 118, 101, 110, 116, 59, 41, 86, 1, 0, 38, 76, 99, 112, 119, 47, 109, 111, 100, 115, 47, 102, 109, 108, 47, 99, 111, 109, 109, 111, 110, 47, 77, 111, 100, 36, 69, 118, 101, 110, 116, 72, 97, 110, 100, 108, 101, 114, 59, 1, 0, 5, 101, 118, 101, 110, 116, 1, 0, 53, 76, 99, 112, 119, 47, 109, 111, 100, 115, 47, 102, 109, 108, 47, 99, 111, 109, 109, 111, 110, 47, 101, 118, 101, 110, 116, 47, 70, 77, 76, 80, 114, 101, 73, 110, 105, 116, 105, 97, 108, 105, 122, 97, 116, 105, 111, 110, 69, 118, 101, 110, 116, 59, 1, 0, 4, 67, 111, 100, 101, 1, 0, 18, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 97, 98, 108, 101, 1, 0, 15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 25, 82, 117, 110, 116, 105, 109, 101, 86, 105, 115, 105, 98, 108, 101, 65, 110, 110, 111, 116, 97, 116, 105, 111, 110, 115, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0, 12, 73, 110, 110, 101, 114, 67, 108, 97, 115, 115, 101, 115, 0, 33, 0, 2, 0, 4, 0, 0, 0, 0, 0, 2, 0, 1, 0, 16, 0, 17, 0, 1, 0, 27, 0, 0, 0, 47, 0, 1, 0, 1, 0, 0, 0, 5, 42, -73, 0, 19, -79, 0, 0, 0, 2, 0, 28, 0, 0, 0, 12, 0, 1, 0, 0, 0, 5, 0, 20, 0, 21, 0, 0, 0, 29, 0, 0, 0, 6, 0, 1, 0, 0, 0, 8, 0, 1, 0, 22, 0, 23, 0, 2, 0, 27, 0, 0, 0, 53, 0, 0, 0, 2, 0, 0, 0, 1, -79, 0, 0, 0, 2, 0, 28, 0, 0, 0, 22, 0, 2, 0, 0, 0, 1, 0, 20, 0, 21, 0, 0, 0, 0, 0, 1, 0, 25, 0, 26, 0, 1, 0, 29, 0, 0, 0, 6, 0, 1, 0, 0, 0, 13, 0, 30, 0, 0, 0, 6, 0, 1, 0, 24, 0, 0, 0, 3, 0, 31, 0, 0, 0, 2, 0, 5, 0, 32, 0, 0, 0, 10, 0, 1, 0, 12, 0, 14, 0, 15, 38, 9, 0, 30, 0, 0, 0, 16, 0, 1, 0, 6, 0, 2, 0, 7, 115, 0, 8, 0, 9, 115, 0, 10};

	        //byte[] buf2 = IOUtils.toByteArray(new FileInputStream(ConfigHandler.configDir + "/BaseClass.class"));
	        
	        //System.out.println("buffer:"+Arrays.toString(buf2));
	        
	        out.putNextEntry(new JarEntry("ccsfiles/common/BaseClass.class"));
	        out.write(buf2, 0, buf2.length);

	        //Add Sound assets
	        addSoundAssets(f, out, writer, buf, soundAssets);
	        
	        addImageAssets(f, out, buf, imagesAssets); 
	        
	        addLangAssets(f, out, buf, langAssets);
	        
	        writer.newLine();
	        writer.write(125);
	        writer.close();

	        FileInputStream in2 = new FileInputStream(json);
	        out.putNextEntry(new JarEntry("assets/ccsfiles/sounds.json"));
	        int len;
	        while ((len = in2.read(buf)) > 0)
	          out.write(buf, 0, len);
	        out.closeEntry();
	        in2.close();
	        out.close();
	        json.delete();
	      }
	    }
	    catch (IOException e)
	    {
	      System.out.println(e.toString());
	    }
	  }
	
	
	private static void addSoundAssets(File f, JarOutputStream out, BufferedWriter writer, byte[] buf,ArrayList soundAssets) throws IOException
	{
        for (int i = 0; i < soundAssets.size(); i++)
        {
          if (i > 0)
          {
            writer.write(44);
            writer.newLine();
          }
          
	        writer.write("\"" + encodeName((String)soundAssets.get(i)) + "\": {\"category\": \"master\",\"sounds\": [{\"name\": \"" + encodeName((String)soundAssets.get(i)) + "\",\"stream\": false}]}");
	        
	        out.putNextEntry(new JarEntry("assets/ccsfiles/sounds/" + encodeName((String)soundAssets.get(i)) + ".ogg"));
	          FileInputStream f1 = new FileInputStream(new File(f, (String)soundAssets.get(i) + ".ogg"));
	          int len;
	          while ((len = f1.read(buf)) > 0)
	            out.write(buf, 0, len);
	          f1.close();
	         
	          System.out.println((String)soundAssets.get(i));
        }
	}
	
	private static void addLangAssets(File f, JarOutputStream out, byte[] buf,ArrayList langAssets) throws IOException
	{
        for (int i = 0; i < langAssets.size(); i++)
        {
		        out.putNextEntry(new JarEntry("assets/ccsfiles/lang/" + (String)langAssets.get(i) + ".lang"));
	          FileInputStream f1 = new FileInputStream(new File(f, (String)langAssets.get(i) + ".lang"));
	          int len;
	          while ((len = f1.read(buf)) > 0)
	            out.write(buf, 0, len);
	          f1.close();
	         
	          System.out.println((String)langAssets.get(i));
        }
	}
	
	private static void addImageAssets(File f, JarOutputStream out, byte[] buf,ArrayList imageAssets) throws IOException
	{
        for (int i = 0; i < imageAssets.size(); i++)
        {

           out.putNextEntry(new JarEntry("assets/ccsfiles/textures/gui/" + encodeName((String)imageAssets.get(i)) + ".png"));
	
           FileInputStream f1 = new FileInputStream(new File(f, (String)imageAssets.get(i) + ".png"));
	          int len;
	          while ((len = f1.read(buf)) > 0)
	            out.write(buf, 0, len);
	          f1.close();
	         
	       
//	          if(new File(f,(String)imageAssets.get(i) +".png.mcmeta").exists())
//	          {
//	              out.putNextEntry(new JarEntry("assets/ccsfiles/textures/gui/" + encodeName((String)imageAssets.get(i)) + ".png.mcmeta"));
//	          	
//	              FileInputStream f2 = new FileInputStream(new File(f, (String)imageAssets.get(i) + ".png.mcmeta"));  
//	              
//		          int lenb;
//		          while ((lenb = f2.read(buf)) > 0)
//		            out.write(buf, 0, lenb);
//		          f2.close();
//	          }
	          
	          System.out.println((String)imageAssets.get(i));

        }
	}
	
	  public static String encodeName(String name)
	  {
	    String str = "";
	    for (int i = 0; i < name.length(); i++)
	    {
	      if (i != 0)
	        str = str + "_";
	      str = str + Integer.toString(Character.codePointAt(name, i));
	    }
	    return str;
	  }
}
