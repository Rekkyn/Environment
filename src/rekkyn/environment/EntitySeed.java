package rekkyn.environment;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class EntitySeed extends Entity {
    
    public int energy;
    int time = 0;
    int seedTime = 600;
    
    public EntitySeed(float x, float y, int energy) {
        super(x, y);
        this.energy = energy;
        size = 40;
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        time++;
        if (time > seedTime && Math.abs(velocity.length()) < 0.1) {
            Plant plant = new Plant(x, y, energy);
            World.add(plant);
            remove();
        }
    }
    
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(col);
        g.pushTransform();
        g.translate(World.partialTicks * (x - prevX), World.partialTicks * (y - prevY));
        g.fillOval(x - edgeLength / 2, y - edgeLength / 2, edgeLength, edgeLength);
        g.setColor(Color.black);
        g.drawLine(x, y, x + velocity.x * 10, y + velocity.y * 10);
        g.popTransform();
    }
    
    @Override
    public boolean isCollidable() {
        return false;
    }
}
