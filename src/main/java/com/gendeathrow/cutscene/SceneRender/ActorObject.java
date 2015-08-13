package com.gendeathrow.cutscene.SceneRender;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ListIterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import com.gendeathrow.cutscene.SceneRender.transitions.Transition;
import com.gendeathrow.cutscene.SceneRender.transitions.Transition.TransitionType;
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
public class ActorObject implements Comparable
{
	@SerializedName("resource")
	private String resourcePath;
	private String displayText;
	private ActorType type;
	public SegmentObject segment;
	
	public int tickLength;
	public int actorTick;
	public int startTick;
	
	private int offsetX;
	private int offsetY;
	
	private int posY;
	private int posX;
	
	private int imageWidth;
	private int imageHeight;
	
	public TransitionType transitionIn;
	public TransitionType transitionOut;
	
	private Transition transition;
	
	public int zLevel;
	
	public transient ResourceLocation resourceLocation;
	
	transient SoundHandler soundHandler;
	
	private Alignment alignment;
	
	private int[] textRGBColor; 
	
	public ActorObject()
	{
		this.type = ActorType.TEXT;
		this.alignment = RenderAssist.Alignment.CENTER_CENTER;
		this.textRGBColor = new int[]{255,255,255};
		this.transition = new Transition();
		this.transitionIn = TransitionType.Fade;
		this.transitionOut = TransitionType.Fade;
		this.zLevel = 1;
		
		System.out.println(this.transitionIn.toString());
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
				CutScene.logger.log(Level.ERROR, "Failed to load!  \""+ Loader.instance().getConfigDir()+"\\"+this.resourcePath+"\" "+ e);
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
	
	@SideOnly(Side.CLIENT)
	public void DrawActor(CutSceneGui cutSceneGui, SegmentObject segment, Minecraft mc, FontRenderer fontObj)
	{
		//System.out.println("type:"+ this.type.type);
		
		this.actorTick++;
		
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
		//fontObj.drawString("Actor Frame:"+ this.actorTick ,0, 30, RenderAssist.getColorFromRGBA(255, 255, 255, 255));

	}
	
	
	private void DrawText(Minecraft mc, FontRenderer fontObj)
	{
		List wrap = fontObj.listFormattedStringToWidth(displayText, mc.currentScreen.width - 30);
		ListIterator itWrap = wrap.listIterator();

		this.transition.update(this);
		
		
		int index = 0;
		while(itWrap.hasNext())
		{
			int lineOffset = index * fontObj.FONT_HEIGHT + 2;
			String line = (String) itWrap.next();
			
			int textWidth = fontObj.getStringWidth(line);
			
			this.alignment.getScreenAlignment(mc, alignment, textWidth, fontObj.FONT_HEIGHT);

			GL11.glEnable(GL11.GL_BLEND);
			
			fontObj.drawString(line, alignment.x + this.offsetX, alignment.y + this.offsetY + lineOffset , RenderAssist.getColorFromRGBA(this.textRGBColor[0], this.textRGBColor[1], this.textRGBColor[2], this.transition.alpha));
			
			index++;
		}

	}
	
	private void DrawImage(Minecraft mc)
	{

		if(this.resourceLocation == null) return;
		
		this.alignment.getScreenAlignment(mc, alignment, this.imageWidth, this.imageHeight);

		this.transition.update(this);
		
		RenderAssist.bindTexture(this.resourceLocation);
		RenderAssist.drawTexturedModalRect(alignment.x + this.offsetX ,alignment.y  + this.offsetY, this.imageWidth, this.imageHeight, this.transition.alpha);

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

	@Override
	public int compareTo(Object arg0) 
	{
		int actorCompare = ((ActorObject)arg0).zLevel;
		return this.zLevel-actorCompare;
	}
	
}
