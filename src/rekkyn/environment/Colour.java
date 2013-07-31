package rekkyn.environment;

import java.util.Random;

import org.newdawn.slick.Color;

public class Colour {
    
    static Random rand = new Random();
    
    public static Color randColour() {
        float hue = rand.nextFloat() * 360F;
        float sat = 0.7F;
        float bri = 0.8F;
        return new Color(java.awt.Color.HSBtoRGB(hue, sat, bri));
    }
    
    public static Color setHSB(float hue, float sat, float bri) {
        return new Color(java.awt.Color.HSBtoRGB(hue, sat, bri));
    }
    
    public static Color setColorByHealth(float percent, Color col) {
        float hsb[] = new float[3];
        java.awt.Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), hsb);
        hsb[1] = 0.4F * percent + 0.3F;
        hsb[2] = 0.5F * percent + 0.3F;
        return new Color(java.awt.Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }
    
    public static Color mutateHue(Color col) {
        float[] hsb = new float[3];
        java.awt.Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), hsb);
        System.out.println(hsb[0]);
        hsb[0] += World.rand.nextGaussian() / 45;
        System.out.println(hsb[0]);
        return new Color(java.awt.Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }
}
