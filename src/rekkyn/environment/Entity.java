package rekkyn.environment;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class Entity {
    
    public float x;
    public float y;
    public float motionX;
    public float motionY;
    public float prevMotionX;
    public float prevMotionY;
    public boolean removed;
    Input input;
    public boolean onEdgeX;
    public boolean onEdgeY;
    public float size;
    float edgeLength;
    public float x1;
    public float y1;
    public float x2;
    public float y2;
    public Color col;
    Random rand = new Random();
    
    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
        size = 50;
        col = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
    }
    
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(col);
        g.pushTransform();
        g.translate(World.partialTicks * motionX, World.partialTicks * motionY);
        g.fillRect(x - edgeLength / 2, y - edgeLength / 2, edgeLength, edgeLength);
        g.popTransform();
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        input = container.getInput();
        GameState state = game.getCurrentState();
        if (!(state instanceof World)) return;
        prevMotionX = motionX;
        prevMotionY = motionY;
                
        edgeLength = (float) Math.sqrt(size);
        x1 = x - edgeLength / 2;
        x2 = x + edgeLength / 2;
        y1 = y - edgeLength / 2;
        y2 = y + edgeLength / 2;
        
        x += motionX;
        y += motionY;
        
        onEdgeX = false;
        onEdgeY = false;
        if (x1 < 0) {
            x = edgeLength / 2;
            motionX = -motionX * 0.4F;
            onEdgeX = true;
        }
        if (y1 < 0) {
            y = edgeLength / 2;
            motionY = -motionY * 0.4F;
            onEdgeY = true;
        }
        if (x2 > Game.width) {
            x = Game.width - edgeLength / 2;
            motionX = -motionX * 0.4F;
            onEdgeX = true;
        }
        if (y2 > Game.height) {
            y = Game.height - edgeLength / 2;
            motionY = -motionY * 0.4F;
            onEdgeY = true;
        }
        
        for (int i = 0; i < World.getEntities().size(); i++) {
            Entity e = World.getEntities().get(i);
            if (e != this && intersects(e)) {
                onHit(e);
                float xOverlap = 0;
                float yOverlap = 0;
                if (e.x2 > x1 && x2 > x2) {
                    xOverlap = x2 - x1;
                }
                if (x2 > e.x1 && x1 < e.x1) {
                    xOverlap = e.x1 - x2;
                }
                if (x1 == e.x1) {
                    xOverlap = edgeLength;
                }
                
                if (e.y2 > y1 && y2 > e.y2) {
                    yOverlap = e.y2 - y1;
                }
                if (y2 > e.y1 && y1 < e.y1) {
                    yOverlap = e.y1 - y2;
                }
                if (y1 == e.y1) {
                    yOverlap = edgeLength;
                }
                
                if (xOverlap != 0 && yOverlap != 0) {
                    if (Math.abs(xOverlap) < Math.abs(yOverlap)) {
                        x += xOverlap + xOverlap / Math.abs(xOverlap) * 0.1 + -prevMotionX * 0.1;
                        if (e.onEdgeX) {
                            motionX = -prevMotionX * 0.8F;
                        } else {
                            e.motionX = prevMotionX * 0.8F;
                            motionX = e.prevMotionX * 0.8F;
                        }
                    } else {
                        y += yOverlap + yOverlap / Math.abs(yOverlap) * 0.1 + -prevMotionY * 0.1;
                        if (e.onEdgeY) {
                            motionY = -prevMotionY * 0.8F;
                        } else {
                            e.motionY = prevMotionY * 0.8F;
                            motionY = e.prevMotionY * 0.8F;
                        }
                    }
                }
            }
        }
        
        
        int numberOfKeys = 0;
        float inputX = 0;
        float inputY = 0;
        
            input = container.getInput();
            if (input.isKeyDown(Input.KEY_UP)) {
                inputY -= 0.2;
                numberOfKeys++;
            }
            if (input.isKeyDown(Input.KEY_DOWN)) {
                inputY += 0.2;
                numberOfKeys++;
            }
            if (input.isKeyDown(Input.KEY_LEFT)) {
                inputX -= 0.2;
                numberOfKeys++;
            }
            if (input.isKeyDown(Input.KEY_RIGHT)) {
                inputX += 0.2;
                numberOfKeys++;
            }
            
        
        if (numberOfKeys >= 2) {
            inputX /= Math.sqrt(2);
            inputY /= Math.sqrt(2);
        }
        
        motionX += inputX;
        motionY += inputY;
        
        motionX *= 0.95;
        motionY *= 0.95;
    }
    
    public void readFromOptions() {}
    
    public void writeToOptions() {}
    
    public void onRightClicked() {}
    
    public void onHit(Entity e) {}
    
    public void remove() {
        removed = true;
    }
    
    public boolean intersects(Entity e) {
        if (e.x1 > x2 || x1 > e.x2) return false;
        if (e.y1 > y2 || y1 > e.y2) return false;
        return true;
        
    }
    
    public void init() {}
    
}
