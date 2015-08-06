package com.gendeathrow.cutscene.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.gendeathrow.cutscene.SceneRender.ActorObject;
import com.gendeathrow.cutscene.SceneRender.SceneObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class GsonReader
{
	public GsonReader(){}
	
	public static SceneObject GsonReadFromFile(String filePath) throws StackOverflowError
	{

		  //Gson gson = new Gson();  
		    GsonBuilder gsonBuilder = new GsonBuilder();
		    ActorObject ActorObject = new ActorObject();
			gsonBuilder.registerTypeAdapter(ActorObject.ActorType.class, ActorObject.new ActorTypeDeserializer());
		    Gson gson = gsonBuilder.create();

	 
		  
		  try {  
		    
		   System.out.println("Reading JSON from a file");  
		   System.out.println("----------------------------"+ Loader.instance().getConfigDir()+"\\"+ filePath);  
		     
		   BufferedReader br = new BufferedReader( new FileReader(Loader.instance().getConfigDir()+"\\"+filePath));  
		     
		   SceneObject screenObj = gson.fromJson(br, SceneObject.class);

		   return screenObj;
		    
		  } catch (IOException e) {  
		   e.printStackTrace();  
		  }
		  
		return null;  

	}
	
}
