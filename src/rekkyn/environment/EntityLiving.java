package rekkyn.environment;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class EntityLiving extends Entity {
    
    public int energy;
    public int health = 1000;
    public boolean alive = true;
    public int deathTimer = 0;
    
    public EntityLiving(float x, float y) {
        super(x, y);
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        
        if (alive) {
            if (World.tickCount % 30 == 0) {
                energyTick();
            }
            
            if (energy <= 0 || health <= 0) {
                setDead();
                /*if (size > 50) {
                    size-= 20;
                    energy += 20;
                } else {
                    setDead();
                }*/
            }
        } else {
            deathTimer++;
            if (deathTimer < 1800) {
            col = Colour.setColorByHealth((1800 - deathTimer) / 1800F, col);
            } else {
                col = Colour.setColorByHealth(0, col);
            }
            if (size > 50) {
                if (World.rand.nextInt(10) == 0) {
                size -= Terrain.changeEnergy(x, y, 1);
                }
            } else {
                Terrain.changeEnergy(x, y, size);
                remove();
            }
        }
        
    }
    
    public boolean grow() {
        if (energy <= 0) return false;
        energy -= 0.0001 * (size - 50) * (size - 50) + 1;
        size++;
        return true;
    }
    
    public boolean shrink() {
        if (size <= 50) return false;
        energy++;
        size--;
        return true;
    }
    
    public void setDead() {
        alive = false;
    }
    
    public void energyTick() {}
    
}
