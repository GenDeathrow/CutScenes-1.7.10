package com.gendeathrow.cutscene.MovieRender;

import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.VideoFormatKeys.DataClassKey;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import org.monte.media.Buffer;
import org.monte.media.BufferFlag;
import org.monte.media.Codec;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.Registry;
import org.monte.media.image.Images;

import com.gendeathrow.cutscene.utils.RenderAssist;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MovieReader 
{

	public org.monte.media.MovieReader in;
	ArrayList<BufferedImage> frames;
	
	public MovieReader()
	{
		this.in = null;
	}
	
	
	
	public BufferedImage[] read(File file) throws IOException 
	{
		        frames =new ArrayList<BufferedImage> ();
		        
		        in = Registry.getInstance().getReader(file);

		        Format format = new Format(DataClassKey, BufferedImage.class);

		        int track=in.findTrack(0,new Format(MediaTypeKey,MediaType.VIDEO));
		        Codec codec=Registry.getInstance().getCodec(in.getFormat(track), format);

		        try {
		            Buffer inbuf = new Buffer();
		            Buffer codecbuf = new Buffer();
		            do {
		                in.read(track, inbuf);
		                codec.process(inbuf, codecbuf);
		                if (!codecbuf.isFlag(BufferFlag.DISCARD)) {
		                    frames.add(Images.cloneImage((BufferedImage)codecbuf.data));
		                }
		                
		            } while (!inbuf.isFlag(BufferFlag.END_OF_MEDIA));
		        } finally {
		            in.close();
		        }
		        
		        return frames.toArray(new BufferedImage[frames.size()]);
		    
    }
	
public void draw() throws IOException
{
    int track = 0;
    while (track < in.getTrackCount() && in.getFormat(track).get(MediaTypeKey) != MediaType.VIDEO) 
    {
        track++;
    }

    // Read images from the track
    BufferedImage img = null;
    do {
   //     img = in.read(track, img);
        
        
		DynamicTexture texture = new DynamicTexture(this.frames.get(track));
        
		ResourceLocation resource = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("nukll", texture);
	
		RenderAssist.bindTexture(resource);
        RenderAssist.drawTexturedModalRect(0, 0, 100, 100);

        
    } while (img != null);
}

}
