package com.gendeathrow.cutscene.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.Level;

import com.gendeathrow.cutscene.SceneRender.ActorObject;
import com.gendeathrow.cutscene.SceneRender.SceneObject;
import com.gendeathrow.cutscene.core.CutScene;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class GsonReader
{
	public GsonReader(){}
	
	public static SceneObject GsonReadFromFile(String filePath) throws StackOverflowError
	{
		try{

		  //Gson gson = new Gson();  
		    GsonBuilder gsonBuilder = new GsonBuilder();
		    ActorObject ActorObject = new ActorObject();
		    
		    RenderAssist RenderAssist = new RenderAssist();
		    
		    gsonBuilder.registerTypeAdapter(RenderAssist.Alignment.class, RenderAssist.new AlignmentDeserializer());
			gsonBuilder.registerTypeAdapter(ActorObject.ActorType.class, ActorObject.new ActorTypeDeserializer());
			
		    Gson gson = gsonBuilder.create();

		    int[] test = new int[]{0,1,2,3,4,5,6,7,8,9};
		    
		    System.out.println(gson.toJson(test)+ " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		  
		  try {  
		    
		   System.out.println("Reading JSON from a file");  
		   System.out.println("----------------------------"+ Loader.instance().getConfigDir()+"\\"+ filePath);  
		     
		   BufferedReader br = new BufferedReader( new FileReader(Loader.instance().getConfigDir()+"\\"+filePath));  
		     
		   SceneObject screenObj = gson.fromJson(br, SceneObject.class);
		   
           if (br != null)
           {
               try
               {
            	   br.close();
               }
               catch (IOException ioexception)
               {
                   ;
               }
           }

		   return screenObj;
		    
		  } catch (IOException e) {  
		   e.printStackTrace();  
		  }
		  
		}catch(JsonSyntaxException e)
		{
			CutScene.logger.log(Level.ERROR, "There is a Syntax Error in your Json file. You better fix that! \n"+ e);
		}
		return null;  

	}
	
}
