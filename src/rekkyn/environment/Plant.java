package rekkyn.environment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Plant extends EntityLiving {
    
    public float rootX, rootY;
    public boolean connected = true;
    
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
        
        if (alive) {
            if (energy > 0) grow();
            
            float dist = (float) Math.sqrt((rootX - x) * (rootX - x) + (rootY - y) * (rootY - y));
            if (connected) {
                force.x += dist * (rootX - x) * 0.4;
                force.y += dist * (rootY - y) * 0.4;
                if (Math.abs(velocity.length()) < 0.3 && dist < 1) {
                    x = rootX;
                    y = rootY;
                }
                if (dist >= 20) {
                    connected = false;
                }
            } else {
                health--;
            }
        }        
    }
    
    @Override
    public void energyTick() {
        energy -= size;
        energy += size * 1.01; // sun power
        if (connected) {
            energy -= Terrain.changeEnergy(rootX, rootY, -1);
        }
    }
    
    @Override
    public void setDead() {
        super.setDead();
        connected = false;
    }
    
}
