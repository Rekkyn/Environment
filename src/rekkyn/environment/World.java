package rekkyn.environment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class World extends BasicGameState {
    
    float accumulator = 0.0F;
    static float partialTicks;
    
    public World(int state) {}

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {}
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {}
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        accumulator += delta;

        while ( accumulator >= 50/3 )
        {
             tick(container, game, delta);
             accumulator -= 50/3;
        }
        
        partialTicks = accumulator / (50/3);
    }
    
    public void tick(GameContainer container, StateBasedGame game, int delta) {
        
    }

    @Override
    public int getID() {
        return Game.WORLD;
    }
    
}
