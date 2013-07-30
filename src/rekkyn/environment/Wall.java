package rekkyn.environment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Wall extends Entity {

    public Wall(float x, float y) {
        super(x, y);
        invMass = 0;
        restitution = 0.6F;
    }
    
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        invMass = 0;
    }
}
