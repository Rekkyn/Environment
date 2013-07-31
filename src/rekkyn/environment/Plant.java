package rekkyn.environment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Plant extends Entity {
    
    public int energy;
    
    public float rootX, rootY;
    
    public Plant(float x, float y, int energy) {
        super(x, y);
        this.energy = energy;
        super.size = 50;
        rootX = x;
        rootY = y;
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        
    }
    
}
