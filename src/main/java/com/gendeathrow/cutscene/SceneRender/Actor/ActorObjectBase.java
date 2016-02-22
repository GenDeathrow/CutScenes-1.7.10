package com.gendeathrow.cutscene.SceneRender.Actor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ListIterator;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import com.gendeathrow.cutscene.SceneRender.ActorObject;
import com.gendeathrow.cutscene.SceneRender.SceneObject;
import com.gendeathrow.cutscene.SceneRender.SegmentObject;
import com.gendeathrow.cutscene.SceneRender.Transition;
import com.gendeathrow.cutscene.SceneRender.Transition.TransitionType;
import com.gendeathrow.cutscene.client.audio.GuiPlaySound;
import com.gendeathrow.cutscene.core.CutScene;
import com.gendeathrow.cutscene.utils.RenderAssist;
import com.gendeathrow.cutscene.utils.RenderAssist.Alignment;
import com.google.gson.JsonArray;
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
public class ActorObjectBase implements Comparable
{
	@SerializedName("resource")
	private String resourcePath;
	private String displayText;
	private ActorObject.ActorType type;
	public transient boolean isDone; 
	
	public transient SegmentObject segment;
	
	@SerializedName("duration")
	public int tickLength;
	
	public long timeStart;
	private long timeDuration;
	
	public int actorTick;
	public int startTick;
	
	private int offsetX;
	private int offsetY;
	
	private int posY;
	private int posX;
	
	private int imageWidth;
	private int imageHeight;
	
	private boolean imageStretchWidth;
	private boolean imageStretchHeight;
	
	private transient int width;
	private transient int height;
	
	public TransitionType transitionIn;
	public TransitionType transitionOut;
	
	private Transition transition;
	
	public int zLevel;
	
	private transient ResourceLocation resourceLocation;
	
	transient SoundHandler soundHandler;
	private transient boolean hasPlaySound;
	
	// Sound 
	private float volume;
	private float pitch;
	private boolean canRepeat;
	private int repeatDelay;
	
	private Alignment alignment;
	
	private int[] textRGBColor;
	

	public ActorObjectBase()
	{
		this.type = ActorObject.ActorType.TEXT;
		this.alignment = RenderAssist.Alignment.CENTER_CENTER;
		this.textRGBColor = new int[]{255,255,255};
		this.transition = new Transition();
		this.transitionIn = TransitionType.Fade;
		this.transitionOut = TransitionType.Fade;
		this.zLevel = 1;
		this.width = 0;
		this.height = 0;
		this.tickLength = 5;
		this.timeDuration = 5000;
		
		this.volume = 1;
		this.pitch = 1;
		this.canRepeat = false;
		this.repeatDelay = 1;
		this.hasPlaySound = false;
		this.isDone = false;
		
		this.imageStretchHeight = false;
		this.imageStretchWidth = false;
		
	}
	
	
	public void init(SegmentObject segmentObj)
	{
		this.segment = segmentObj;
		
		if(type == ActorObject.ActorType.IMAGE && this.resourcePath != null)
		{
			try 
			{
				//this.resourceLocation = RenderAssist.ExternalResouceLocation(Loader.instance().getConfigDir()+ File.separator +"CustomCutScenes"+ File.separator +"assets/images"+ File.separator +this.resourcePath);

				//this.resourceLocation = new ResourceLocation("ccsfiles","textures/gui/"+ Utils.encodeName(path)+".png");
				
				this.resourceLocation = RenderAssist.getActorResourceLocation(Minecraft.getMinecraft(), this.resourcePath, type);
				
				BufferedImage getImage = ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(this.resourceLocation).getInputStream());
				
				if(this.imageHeight == 0) 
				{
					int constraints = 1;
					if(this.imageWidth != 0)
					{
						constraints = (getImage.getWidth() / this.imageWidth);
					}
					
					this.imageHeight = getImage.getHeight() * constraints;
				}
				if(this.imageWidth == 0) 
				{
					int constraints = 1;
					if(this.imageHeight != 0)
					{
						constraints = (getImage.getHeight() / this.imageHeight);
					}
					
					this.imageWidth = getImage.getWidth() * constraints;
				}
				
				this.width = this.imageWidth;
				this.height = this.imageHeight;
				
				getImage = null;
				
			} catch (IOException e) 
			{
				CutScene.logger.log(Level.ERROR, "Failed to load!  \""+ Loader.instance().getConfigDir() + File.separator +"CustomCutScenes"+ File.separator +"assets"+ File.separator + this.resourcePath+"\" "+ e);
				e.printStackTrace();  
			}
		}
		else if(type == ActorObject.ActorType.SOUND && this.resourcePath != null)
		{
			this.resourceLocation = RenderAssist.getActorResourceLocation(Minecraft.getMinecraft(), this.resourcePath, type);
		}
		
	}
	
	public ResourceLocation getResource()
	{
		return new ResourceLocation(CutScene.MODID ,Loader.instance().getConfigDir()+"\\"+ this.resourcePath);
	}
	
	public long getStartTime()
	{
		return this.timeStart;
	}
	public long getStopTime()
	{
		return (this.timeStart + this.timeDuration);
	}
	
	public long getLength()
	{
		return this.timeDuration;
	}
	
	public long getActorDuration()
	{
		return (Minecraft.getSystemTime() - this.startTime);
	}
	
	boolean firstload = true;
	private long startTime;
	
	@SideOnly(Side.CLIENT)
	public void DrawActor(SceneObject sceneObject, SegmentObject segment, Minecraft mc)
	{
		if (firstload) { firstload = false;  this.startTime = Minecraft.getSystemTime();}

		FontRenderer fontObj = sceneObject.guiParent.fontObj;
		if(type == ActorObject.ActorType.IMAGE)
		{
			this.DrawImage(mc);
		}
		else if(type == ActorObject. ActorType.TEXT)
		{
			this.DrawText(mc, fontObj);
		}
		else if (type == ActorObject.ActorType.SOUND && !this.hasPlaySound)
		{
			Minecraft.getMinecraft().getSoundHandler().playSound(new GuiPlaySound(sceneObject.guiParent, this.resourceLocation, this.volume, this.pitch, this.canRepeat, this.repeatDelay));
			this.hasPlaySound = true;
		}
		
		if(sceneObject.showDebug && alignment != null) 
		{
			int posx = this.alignment.x + this.offsetX;
			int posy = this.alignment.y + this.offsetY;
			
			fontObj.drawString("Actor Frame:"+ this.actorTick ,posx ,posy - fontObj.FONT_HEIGHT, RenderAssist.getColorFromRGBA(255, 255, 255, 255));
			RenderAssist.drawUnfilledRect( posx ,posy, this.width +posx, this.height +posy, RenderAssist.getColorFromRGBA(255, 255, 255, 255));
		}
		this.actorTick++;
	}
	
	private void DrawText(Minecraft mc, FontRenderer fontObj)
	{
		List wrap = fontObj.listFormattedStringToWidth(displayText, mc.currentScreen.width - 30);
		ListIterator itWrap = wrap.listIterator();

		//TODO  suppose to be there gives error-> this.transition.update(this);
		
		//System.out.println(this.transition.alpha + "<<<<<<<<<<<<<<<<<<<<<<<"+this.displayText.substring(0, 5));
		
		if(this.transition.alpha < 5) return;
		
		GL11.glEnable(GL11.GL_BLEND);

		int index = 0;
		while(itWrap.hasNext())
		{
			int lineOffset = index * fontObj.FONT_HEIGHT + 2;
	
			String line = StatCollector.translateToLocal((String) itWrap.next());
			
			
			
			int textWidth = fontObj.getStringWidth(line);
			this.alignment.getScreenAlignment(mc, alignment, textWidth, fontObj.FONT_HEIGHT);

			if(this.transition.alpha != 0) fontObj.drawString(line, alignment.x + this.offsetX, alignment.y + this.offsetY + lineOffset , RenderAssist.getColorFromRGBA(this.textRGBColor[0], this.textRGBColor[1], this.textRGBColor[2], this.transition.alpha));
			
			this.width = textWidth > this.width ? textWidth : this.width;

			 		
			this.height = index+1 * fontObj.FONT_HEIGHT + 2;
			index++;
		}

		GL11.glDisable(GL11.GL_BLEND);

	}
	
	
	private void DrawImage(Minecraft mc)
	{
		ScaledResolution resolution= new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		
		int scaledImageWidth = this.imageWidth;
		int scaledImageHeight = this.imageHeight;
		
		if(this.resourceLocation == null) return;
		
		this.alignment.getScreenAlignment(mc, alignment, this.imageWidth, this.imageHeight);
		
		if(this.imageStretchHeight) 
		{
			this.alignment.y = 0;
			scaledImageHeight = resolution.getScaledHeight();
		}
		if(this.imageStretchWidth) 
		{
			this.alignment.x = 0;
			scaledImageWidth = resolution.getScaledWidth();
		}
		
		//TODO suppost to be there ---> this.transition.update(this);
		
		RenderAssist.bindTexture(this.resourceLocation);
		RenderAssist.drawTexturedModalRect(alignment.x + this.offsetX ,alignment.y  + this.offsetY, scaledImageWidth, scaledImageHeight, this.transition.alpha);

		//System.out.println(alignment.x +" "+ alignment.y+ " "+ this.offsetY+" "+ this.posX);
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
	
	public class ActorDurationDeserializer implements JsonDeserializer<Long>
	{
		
	  @Override
	  public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)  throws JsonParseException
	  {
		  long val = 0;

		  if (json.isJsonArray())
		  {
			  JsonArray timeList = json.getAsJsonArray();

			  int minInMilisecs = 0;
			  int secInMilisecs = 0;
			  int milisecs = 0;
			  
			  if(timeList.size() != 3) 
			  {
				  CutScene.logger.log(Level.ERROR, "Tried to Deserialize Duration into Milisecs! Time is not in correct format in json [min,secs,milisecs]");
				  return val;
			  }
			  minInMilisecs = (timeList.get(0).getAsInt() * 60000);
			  secInMilisecs = (timeList.get(1).getAsInt() * 1000);
			  milisecs = timeList.get(2).getAsInt();
			  
			  val = (long)(minInMilisecs + secInMilisecs + milisecs);
		  }
		  else
		  {
			  val = json.getAsLong();
		  }
	    
	    return val;
	  }

	}
	
//	public class ActorImageSizeDeserializer implements JsonDeserializer<Integer>
//	{
//		
//	  @Override
//	  public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)  throws JsonParseException
//	  {	
//		  try 
//		  {
//			  int i = json.getAsInt();
//			  
//			  return i;
//		  }
//		  catch(NumberFormatException er)
//		  { 
//			  try
//			  {
//				  String string = json.getAsString();
//				  
//				  Matcher matcher;
//				  
//				  if(string.toLowerCase().contains("screenwidth"))
//				  {
//					  Pattern widthPattern = Pattern.compile("screenwidth");
//					  
//					  string = string.replaceAll("screenwidth", Integer.toString(Minecraft.getMinecraft().currentScreen.width));
//					    
//				  }
//				  else if(string.toLowerCase().contains("screenheight"))
//				  {
//					  Pattern heightPattern = Pattern.compile("screenheight");
//					  string = string.replaceAll("screenheight", Integer.toString(Minecraft.getMinecraft().currentScreen.height));
//					  
//				  }
//				  
//				  int i = MathHelper.eval("("+ string +")");
//				  
//				  return i;
//			  }
//			  catch(Exception e)
//			  {
//				  e.printStackTrace();
//			  }
//		  }
//		  return 10;
//	  }
//	}
	

	@Override
	public int compareTo(Object arg0) 
	{
		int actorCompare = ((ActorObject)arg0).zLevel;
		return this.zLevel-actorCompare;
	}


	
}
