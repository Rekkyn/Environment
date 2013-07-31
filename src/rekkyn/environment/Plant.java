package rekkyn.environment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Plant extends Entity {
    
    public int energy;
    public float rootX, rootY;
    public boolean connected = true;
    public int health = 1000;
    
    public Plant(float x, float y, int energy) {
        super(x, y);
        this.energy = energy;
        super.size = 1000;
        rootX = x;
        rootY = y;
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        
        if (World.tickCount % 12 == 0) {
        energyTick();
        }
        
        if (energy <= 0) {
            if (size > 50) {
                size--;
                energy++;
            } else {
                setDead();
            }
        }
        
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
            if (health < 0) health = 0;
        }
                
        col = Colour.setColorByHealth(health / 1000F, col);
    }

    private void energyTick() {
        energy += size * 0.8; //sun power
        energy -= size; // cost of living        
        System.out.println(energy);
    }

    private void setDead() {
        connected = false;
    }
    
}
