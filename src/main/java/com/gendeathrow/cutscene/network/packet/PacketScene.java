package com.gendeathrow.cutscene.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

import com.gendeathrow.cutscene.client.gui.CutSceneGui;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketScene implements IMessage
{

	private String scene;
	private int controlID;
	
	public PacketScene() {}
	
	public PacketScene(String whichScene,SceneControls control )
	{
		this.scene = whichScene;
		this.controlID = control.getControlID();
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.scene = ByteBufUtils.readUTF8String(buf);
		this.controlID = ByteBufUtils.readVarInt(buf, 5);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeUTF8String(buf, this.scene);
		ByteBufUtils.writeVarInt(buf, this.controlID, 5);
	}
	
	
	public static class HandlerServer implements IMessageHandler<PacketScene,IMessage>
	{
		@Override
		public IMessage onMessage(PacketScene packet, MessageContext ctx)
		{

			return null;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static class HandlerClient implements IMessageHandler<PacketScene,IMessage>
	{

		@Override
		public IMessage onMessage(PacketScene message, MessageContext ctx) 
		{
			System.out.println("client packet recieved: "+ message.scene);
			
			if(message.controlID == SceneControls.Play.id)
			{
				Minecraft.getMinecraft().displayGuiScreen(new CutSceneGui("/customcutscenes/splashscreen.json"));
			}
			
			return null;
		}
	
	}
	
	public enum SceneControls
	{
		Play("play",1),
		Stop("stop",0);
		
		String control;
		int id;
		
		SceneControls(String control,int id)
		{
			this.control = control;
			this.id = id;
		}
		
		public int getControlID()
		{
			return this.id;
		}
	}

}
