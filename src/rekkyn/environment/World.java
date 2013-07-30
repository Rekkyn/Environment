package rekkyn.environment;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class World extends BasicGameState {
    
    public static List<Entity> entities = new ArrayList<Entity>();
    float accumulator = 0.0F;
    static float partialTicks;
    
    public World(int state) {}

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        Entity e = new Entity(100, 100);
        e.playerControlled = true;
        
        Entity e2 = new Entity(100, 150);

        add(e);
        add(e2);
        
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

        while ( accumulator >= 50/3 )
        {
             tick(container, game, delta);
             accumulator -= 50/3;
        }
        
        partialTicks = accumulator / (50/3);
        System.out.println(accumulator);
    }
    
    public void tick(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            
            e.update(container, game, delta);
            
            if (e.removed) {
                entities.remove(i--);
            }
        }
    }
    
    public void add(Entity entity) {
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
