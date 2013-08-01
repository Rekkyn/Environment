package rekkyn.environment;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class EntitySeed extends EntityLiving {
    
    int lifeTime = 0;
    int seedTime = 60;
    List traits;
    Plant parent;
    
    public EntitySeed(float x, float y, int energy, List traits, Plant parent) {
        super(x, y);
        this.energy = energy;
        size = 40;
        this.traits = traits;
        setTraits(traits);
        this.parent = parent;
    }
    
    private void setTraits(List traits) {
        col = (Color) traits.get(0);
        seedTime = (Integer) traits.get(3);
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        lifeTime++;
        if (lifeTime > seedTime && Math.abs(velocity.length()) < 0.1) {
            Plant plant = new Plant(x, y, energy, traits);
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
        //g.drawLine(x, y, x + velocity.x * 10, y + velocity.y * 10);
        g.popTransform();
    }
    
    /*public void onHit(Entity e) {
        if (!e.equals(parent)) {
        Terrain.changeEnergy(x, y, size);
        remove();
        }
    }*/

    
}
