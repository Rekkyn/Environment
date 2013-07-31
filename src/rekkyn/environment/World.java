package rekkyn.environment;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class World extends BasicGameState {
    
    public static List<Entity> entities = new ArrayList<Entity>();
    float accumulator = 0.0F;
    static float partialTicks;
    public static final float timesetp = 50 / 3; // 1/60 second
    
    // public static final float timesetp = 500;
    
    public World(int state) {}
    
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        Entity e = new Entity(100, 100);
        e.playerControlled = true;
        
        Entity e2 = new Entity(100, 150);
        
        Entity wall = new Wall(200, 200, 100);
        
        EntitySeed seed = new EntitySeed(300, 300, 100);
        seed.velocity.set(5, 0);
        
        add(e);
        add(e2);
        add(wall);
        add(seed);
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(new Color(209, 217, 224));
        g.fillRect(0, 0, Game.width, Game.height);
        
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            
            e.render(container, game, g);
        }
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        accumulator += delta;
        
        while (accumulator >= timesetp) {
            tick(container, game, delta);
            accumulator -= timesetp;
        }
        
        partialTicks = accumulator / timesetp;
    }
    
    public void tick(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        
        Input input = container.getInput();
        
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
                        
            for (int j = 0; j < entities.size(); j++) {
                Entity e2 = entities.get(j);
                if (e2 != e && e.intersects(e2) && e.isCollidable() && e2.isCollidable()) {
                    e.onHit(e2);
                    Collision.doCollision(e, e2);
                }
            }
            
            Collision.doEdgeCollision(e);
            e.update(container, game, delta);
            
            if (e.removed) {
                entities.remove(i--);
            }
        }
        
        if (input.isMousePressed(0)) {
            int mouseX = Mouse.getX();
            int mouseY = Game.height - Mouse.getY();
            
            Entity e = new Entity(mouseX, mouseY);
            add(e);
        }
    }
    
    public static void add(Entity entity) {
        entity.removed = false;
        entities.add(entity);
        entity.init();
    }
    
    public static List<Entity> getEntities() {
        return entities;
    }
    
    @Override
    public int getID() {
        return Game.WORLD;
    }
    
}
