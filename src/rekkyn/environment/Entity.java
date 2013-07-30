package rekkyn.environment;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class Entity {
    
    public float x;
    public float y;
    public float prevX;
    public float prevY;
    public Vector2f velocity;
    public Vector2f prevVelocity;
    public boolean removed;
    Input input;
    public boolean onEdgeX;
    public boolean onEdgeY;
    public int size;
    public float invMass;
    float edgeLength;
    public float x1;
    public float y1;
    public float x2;
    public float y2;
    public Color col;
    Random rand = new Random();
    public boolean playerControlled;
    public float restitution = 0.8F;
        
    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
        size = 500;
        invMass = 1F / size;
        col = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
        velocity = new Vector2f(0, 0);
        prevVelocity = new Vector2f(0, 0);
    }
    
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(col);
        g.pushTransform();
        g.translate(World.partialTicks * (x - prevX), World.partialTicks * (y - prevY));
        g.fillRect(x - edgeLength / 2, y - edgeLength / 2, edgeLength, edgeLength);
        g.setColor(Color.black);
        g.drawLine(x, y, x + velocity.x * 10, y + velocity.y * 10);
        g.popTransform();
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        input = container.getInput();
        GameState state = game.getCurrentState();
        if (!(state instanceof World)) return;
        
        prevVelocity.set(velocity);
        
        invMass = 1F / size;
                        
        edgeLength = (float) Math.sqrt(size);
        x1 = x - edgeLength / 2;
        x2 = x + edgeLength / 2;
        y1 = y - edgeLength / 2;
        y2 = y + edgeLength / 2;
        
        prevX = x;
        prevY = y;
        
        x += velocity.x;
        y += velocity.y;
        
        onEdgeX = false;
        onEdgeY = false;
        if (x1 < 0) {
            x = edgeLength / 2;
            velocity.x = -velocity.x * 0.4F;
            onEdgeX = true;
        }
        if (y1 < 0) {
            y = edgeLength / 2;
            velocity.y = -velocity.y * 0.4F;
            onEdgeY = true;
        }
        if (x2 > Game.width) {
            x = Game.width - edgeLength / 2;
            velocity.x = -velocity.x * 0.4F;
            onEdgeX = true;
        }
        if (y2 > Game.height) {
            y = Game.height - edgeLength / 2;
            velocity.y = -velocity.y * 0.4F;
            onEdgeY = true;
        }
                
        float speed = 0.2F;
        if (playerControlled) {
            Vector2f inputV = new Vector2f();
            
            input = container.getInput();
            if (input.isKeyDown(Input.KEY_UP)) {
                inputV.add(new Vector2f(0, -1));
            }
            if (input.isKeyDown(Input.KEY_DOWN)) {
                inputV.add(new Vector2f(0, 1));
            }
            if (input.isKeyDown(Input.KEY_LEFT)) {
                inputV.add(new Vector2f(-1, 0));
            }
            if (input.isKeyDown(Input.KEY_RIGHT)) {
                inputV.add(new Vector2f(1, 0));
            }
            
            inputV.normalise();
            inputV.scale(speed);
            
            velocity.add(inputV);
        }
        
        velocity.scale(0.95F);
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
    
    public boolean isCollidable() {
        return true;
    }
    
}
