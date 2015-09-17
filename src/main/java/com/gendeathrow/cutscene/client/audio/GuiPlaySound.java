package com.gendeathrow.cutscene.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPlaySound implements ISound, ITickableSound
{
	private GuiScreen gui;
	protected final ResourceLocation resourceLocation;	
    protected float volume = 1.0F;
    protected float pitch = 1.0F;
    protected boolean canRepeatbool = false;
    protected int repeatDelay = 0;
    
	public GuiPlaySound(GuiScreen gui, ResourceLocation resource)
	{
		this(gui, resource, 1);
	}
	
	public GuiPlaySound(GuiScreen gui, ResourceLocation resource, float volume)
	{
		this(gui, resource, volume, 1);
	}
	
	public GuiPlaySound(GuiScreen gui, ResourceLocation resource, float volume, float pitch)
	{
		this(gui, resource, volume, pitch, false, 0);
	}

	public GuiPlaySound(GuiScreen gui, ResourceLocation resource, float volume, float pitch, boolean canRepeat, int repeatDelay)
	{
		this.gui = gui;
		this.resourceLocation = resource;
		this.volume = volume;
		this.pitch = pitch;
		this.canRepeatbool = canRepeat;
		this.repeatDelay = repeatDelay;
	}
	
	public GuiPlaySound setRepeat(boolean repeat, int delay)
	{
		this.repeatDelay = delay;
		this.canRepeatbool = repeat;
		return this;
	}
	
	@Override
	public ResourceLocation getPositionedSoundLocation() 
	{
		return this.resourceLocation;
	}

	@Override
	public boolean canRepeat() 
	{
		return this.canRepeatbool;
	}

	@Override
	public int getRepeatDelay() 
	{
		return this.repeatDelay;
	}

	@Override
	public float getVolume() 
	{
		return this.volume;
	}

	@Override
	public float getPitch() 
	{
		return this.pitch;
	}

	@Override
	public float getXPosF() 
	{
		return 0;
	}

	@Override
	public float getYPosF() 
	{
		return 0;
	}

	@Override
	public float getZPosF() 
	{
		return 0;
	}

	@Override
	public AttenuationType getAttenuationType() 
	{
		return AttenuationType.NONE;
	}

	private boolean done = false;
	
	@Override
	public void update() 
	{
		if(Minecraft.getMinecraft().currentScreen != this.gui)
		{
			this.volume -= .025;
			if(this.volume <= 0) 
			{
				this.volume = 0;
				this.done = true;
			}
		}
		
		if(!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this)) this.done = true;
			
	}

	@Override
	public boolean isDonePlaying() 
	{
		if(this.done) return true;
		return false;
	}

}
