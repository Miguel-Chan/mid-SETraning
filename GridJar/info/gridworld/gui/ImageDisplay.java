package info.gridworld.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;



























public class ImageDisplay
  extends AbstractDisplay
{
  private Class cl;
  private String imageFilename;
  private static final String imageExtension = ".gif";
  private Map<String, Image> tintedVersions = new HashMap();
  




  public ImageDisplay(Class cl)
    throws IOException
  {
    this.cl = cl;
    imageFilename = cl.getName().replace('.', '/');
    URL url = cl.getClassLoader().getResource(imageFilename + ".gif");
    

    if (url == null) {
      throw new FileNotFoundException(imageFilename + ".gif" + " not found.");
    }
    tintedVersions.put("", ImageIO.read(url));
  }
  


  public void draw(Object obj, Component comp, Graphics2D g2)
  {
    Color color;
    

    Color color;
    

    if (obj == null) {
      color = null;
    } else
      color = (Color)getProperty(obj, "color");
    String imageSuffix = (String)getProperty(obj, "imageSuffix");
    if (imageSuffix == null) {
      imageSuffix = "";
    }
    Image tinted = (Image)tintedVersions.get(color + imageSuffix);
    if (tinted == null)
    {
      Image untinted = (Image)tintedVersions.get(imageSuffix);
      if (untinted == null)
      {
        try
        {
          URL url = cl.getClassLoader().getResource(imageFilename + imageSuffix + ".gif");
          
          if (url == null) {
            throw new FileNotFoundException(imageFilename + imageSuffix + ".gif" + " not found.");
          }
          untinted = ImageIO.read(url);
          tintedVersions.put(imageSuffix, untinted);
        }
        catch (IOException ex)
        {
          untinted = (Image)tintedVersions.get("");
        }
      }
      if (color == null) {
        tinted = untinted;
      }
      else {
        FilteredImageSource src = new FilteredImageSource(untinted.getSource(), new TintFilter(color));
        
        tinted = comp.createImage(src);
        

        tintedVersions.put(color + imageSuffix, tinted);
      }
    }
    int width = tinted.getWidth(null);
    int height = tinted.getHeight(null);
    int size = Math.max(width, height);
    

    g2.scale(1.0D / size, 1.0D / size);
    g2.clip(new Rectangle(-width / 2, -height / 2, width, height));
    g2.drawImage(tinted, -width / 2, -height / 2, null);
  }
  


  private static class TintFilter
    extends RGBImageFilter
  {
    private int tintR;
    
    private int tintG;
    
    private int tintB;
    

    public TintFilter(Color color)
    {
      canFilterIndexColorModel = true;
      int rgb = color.getRGB();
      tintR = (rgb >> 16 & 0xFF);
      tintG = (rgb >> 8 & 0xFF);
      tintB = (rgb & 0xFF);
    }
    

    public int filterRGB(int x, int y, int argb)
    {
      int alpha = argb >> 24 & 0xFF;
      int red = argb >> 16 & 0xFF;
      int green = argb >> 8 & 0xFF;
      int blue = argb & 0xFF;
      
      double lum = (0.2989D * red + 0.5866D * green + 0.1144D * blue) / 255.0D;
      












      double scale = 1.0D - 4.0D * ((lum - 0.5D) * (lum - 0.5D));
      
      red = (int)(tintR * scale + red * (1.0D - scale));
      green = (int)(tintG * scale + green * (1.0D - scale));
      blue = (int)(tintB * scale + blue * (1.0D - scale));
      return alpha << 24 | red << 16 | green << 8 | blue;
    }
  }
}
