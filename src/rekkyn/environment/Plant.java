package rekkyn.environment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Plant extends EntityLiving {
    
    public float rootX, rootY;
    public boolean connected = true;
    
    public Plant(float x, float y, int energy) {
        super(x, y);
        this.energy = energy - 50;
        super.size = 50;
        rootX = x;
        rootY = y;
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        System.out.println(energy);

        if (alive) {
            if (energy > 100 && World.rand.nextInt(10) == 0) grow();
            //if (energy > 500) sendSeed(100);
            
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
        energy -= size * 0.25;
        energy += size * 0.2; // sun power
        if (connected) {
            energy -= Terrain.changeEnergy(rootX, rootY, (int) (-size * 0.2));
        }
    }
    
    public void sendSeed(int energy) {
        this.energy -= energy;
        Vector2f v = new Vector2f(World.rand.nextInt(360));
        v.scale(rand.nextFloat() * 10);
        EntitySeed seed = new EntitySeed(rootX, rootY, energy);
        seed.velocity.set(v);
        World.add(seed);
    }
    
    @Override
    public void setDead() {
        super.setDead();
        connected = false;
    }
    
}
