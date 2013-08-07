package rekkyn.environment;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
    
    static AppGameContainer appgc;
    public static final String NAME = "Environment";
    public static final int MENU = 0;
    public static final int WORLD = 1;
    public static int width = 20 * 72;
    public static int height = 20 * 45;

    public Game(String name) {
        super(name);
    }
    
    public static void main(String[] args) {
        try {
            appgc = new AppGameContainer(new Game(NAME));
            appgc.setDisplayMode(width, height, true);
            appgc.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        addState(new Menu(MENU));
        addState(new World(WORLD));
    }
}
