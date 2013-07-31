package rekkyn.environment;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Plant extends EntityLiving {
    
    public float rootX, rootY;
    public boolean connected = true;
    
    public float seedSpeed;
    public int seedEnergy;
    public int seedTime = 60;
    private List traits;
    
    public Plant(float x, float y, int energy, List traits) {
        super(x, y);
        this.energy = energy - 50;
        super.size = 50;
        rootX = x;
        rootY = y;
        this.traits = traits;
        setTraits(traits);
    }
    
    private void setTraits(List traits) {
        col = (Color) traits.get(0);
        seedSpeed = (Float) traits.get(1);
        seedEnergy = (Integer) traits.get(2);
        seedTime = (Integer) traits.get(3);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        
        System.out.println(energy);
        
        if (alive) {
            
            // run AI
            if (energy > 150) grow();
            if (energy < 100 && size > 200) shrink();
            if (energy > seedEnergy * 2) sendSeed();
            
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
        energy += size * 0.3; // sun power
        if (connected) {
            energy -= Terrain.changeEnergy(rootX, rootY, (int) (-size * 0.2));
        }
    }
    
    public void sendSeed() {
        if (energy < seedEnergy) return;
        energy -= seedEnergy;
        Vector2f v = new Vector2f(World.rand.nextInt(360));
        v.scale(seedSpeed);
        EntitySeed seed = Mutator.mutateNewSeed(this);
        seed.velocity.set(v);
        World.add(seed);
    }
    
    @Override
    public void setDead() {
        super.setDead();
        connected = false;
    }
    
}
