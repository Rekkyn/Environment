package rekkyn.environment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {
    
    public Menu(int state) {}

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {}
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.drawString("Menu", Game.width / 2, 25);
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {}
    
    @Override
    public int getID() {
        return Game.MENU;
    }
    
}
