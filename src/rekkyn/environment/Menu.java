package rekkyn.environment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {
    
    public Menu(int state) {}

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {}
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawString("Nothing to see here.", Game.width / 2, 25);
        g.drawString("Press ENTER to start", Game.width / 2, 50);
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();

        if (input.isKeyPressed(Input.KEY_ENTER)) {
            game.enterState(Game.WORLD);
        }
    }
    
    @Override
    public int getID() {
        return Game.MENU;
    }
    
}
