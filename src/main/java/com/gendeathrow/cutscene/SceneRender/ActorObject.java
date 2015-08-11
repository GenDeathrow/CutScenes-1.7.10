package com.gendeathrow.cutscene.SceneRender;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ListIterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.util.Color;
import org.lwjgl.util.ReadableColor;

import com.gendeathrow.cutscene.client.gui.CutSceneGui;
import com.gendeathrow.cutscene.core.CutScene;
import com.gendeathrow.cutscene.utils.RenderAssist;
import com.gendeathrow.cutscene.utils.RenderAssist.Alignment;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Actors are images / sound / text added to a current segment or cutscene
 * @author GenDeathrow
 */
public class ActorObject 
{
	@SerializedName("resource")
	private String resourcePath;
	private String displayText;
	private ActorType type;
	
	public int tickLength;
	public int actorTick;
	public int startTick;
	public int endTick;
	
	private int offsetX;
	private int offsetY;
	
	private int posY;
	private int posX;
	
	private int imageWidth;
	private int imageHeight;
	
	private float zLevel;
	
	public transient ResourceLocation resourceLocation;
	
	transient SoundHandler soundHandler;
	
	private Alignment alignment;
	
	private int[] textRGBColor; 
	
	public ActorObject()
	{
		this.type = ActorType.TEXT;
		this.endTick = 60;
		this.alignment = RenderAssist.Alignment.CENTER_CENTER;
		this.textRGBColor = new int[]{255,255,255};
	}
	
	
	public void init()
	{
		if(type == ActorType.IMAGE && this.resourcePath != null)
		{
			try 
			{
				this.resourceLocation = RenderAssist.ExternalResouceLocation(Loader.instance().getConfigDir()+"\\"+this.resourcePath);
				
			} catch (IOException e) 
			{
				//CutScene.logger.log(Level.ERROR, "Failed to load!  \""+ Loader.instance().getConfigDir()+"\\"+this.resourcePath+"\" "+ e);
				e.printStackTrace();  
			}
		}
		else if(type == ActorType.SOUND && this.resourcePath != null)
		{
			//soundHandler = Minecraft.getMinecraft().getSoundHandler();
			//SoundEventAccessorComposite sound = soundHandler.getSound(this.resourceLocation);
			//sound.addSoundToEventPool(sound);
		}
		
		
	}
	
	public ResourceLocation getResource()
	{
		return new ResourceLocation(CutScene.MODID ,Loader.instance().getConfigDir()+"\\"+ this.resourcePath);
	}
	
	public int getEndTick()
	{
		return this.startTick + this.tickLength;
	}
	
	public String getDisplayText()
	{
		return this.displayText;
	}
	
	@SideOnly(Side.CLIENT)
	public void DrawActor(CutSceneGui cutSceneGui, SegmentObject segment, Minecraft mc, FontRenderer fontObj)
	{
		//System.out.println("type:"+ this.type.type);
		
		if(type == ActorType.IMAGE)
		{
			this.DrawImage(mc);
		}
		else if(type == ActorType.TEXT)
		{
			this.DrawText(mc, fontObj);
		}
		else if (type == ActorType.SOUND)
		{
			//this.playSound();
		}
		fontObj.drawString("Actor Frame:"+ this.actorTick ,0, 30, RenderAssist.getColorFromRGBA(255, 255, 255, 255));
	}
	
	
	private void DrawText(Minecraft mc, FontRenderer fontObj)
	{
		List wrap = fontObj.listFormattedStringToWidth(displayText, mc.currentScreen.width - 30);
		ListIterator itWrap = wrap.listIterator();

		int index = 0;
		while(itWrap.hasNext())
		{
			int lineOffset = index * fontObj.FONT_HEIGHT + 2;
			String line = (String) itWrap.next();
			
			int textWidth = fontObj.getStringWidth(line);
			
			this.alignment.getScreenAlignment(mc, alignment, textWidth, fontObj.FONT_HEIGHT);

			fontObj.drawString(line, alignment.x + this.offsetX, alignment.y + this.offsetY + lineOffset , RenderAssist.getColorFromRGBA(this.textRGBColor[0], this.textRGBColor[1], this.textRGBColor[2], 255));
			index++;
		}

	}
	
	private void DrawImage(Minecraft mc)
	{

		if(this.resourceLocation == null) return;
		
		this.alignment.getScreenAlignment(mc, alignment, this.imageWidth, this.imageHeight);
		
		RenderAssist.bindTexture(this.resourceLocation);
		RenderAssist.drawTexturedModalRect(alignment.x + this.offsetX ,alignment.y  + this.offsetY, this.imageWidth, this.imageHeight, this.zLevel);
		
		//System.out.println(alignment.x +" "+ alignment.y+ " "+ this.offsetY+" "+ this.posX);
	}
	
	private void playSound()
	{
		//SoundEventAccessorComposite sound = soundHandler.getSound(this.resourceLocation);
		
	}
	
	public enum ActorType
	{
		TEXT("text"),
		IMAGE("image"),
		SOUND("sound");
	
		String type;
		
		ActorType(String type)
		{
			this.type=type;
		}
	}
	
	public class ActorTypeDeserializer implements JsonDeserializer<ActorType>
	{
	  @Override
	  public ActorType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)  throws JsonParseException
	  {
	    ActorType[] types = ActorType.values();
	    for (ActorType type : types)
	    {
	      if (type.type.equals(json.getAsString()))
	        return type;
	    }
	    return null;
	  }

	}
	
}
