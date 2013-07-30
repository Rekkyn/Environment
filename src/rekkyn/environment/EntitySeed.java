package rekkyn.environment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class EntitySeed extends Entity {
    
    public int energy;
    int time = 0;

    public EntitySeed(float x, float y, int energy) {
        super(x, y);
        this.energy = energy;
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        time++;
        //if (time > 600 && Math.abs(velocity.length()) < 0.1) {
            
        //}
    }
}
