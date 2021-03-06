package rekkyn.environment;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import rekkyn.environment.Gene.Actions;
import rekkyn.environment.Gene.Condition;
import rekkyn.environment.Gene.Operators;
import rekkyn.environment.Gene.Output;
import rekkyn.environment.Gene.Stats;

public class Plant extends EntityLiving {
    
    public float rootX, rootY;
    public boolean connected = true;
    
    public float seedSpeed;
    public int seedEnergy;
    public int seedTime;
    public int maxSize;
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
        maxSize = (Integer) traits.get(4);
    }
    
    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        super.update(container, game, delta);
        
        if (alive) {
            
            Gene gene = new Gene();
            gene.setGene(gene.new Condition(Stats.ENERGY, Operators.GREATERTHAN, 100), gene.new Output(Actions.GROW), this);
            gene.execute();
            
            // run AI
           /* if (energy > 0.0001 * (size - 50) * (size - 50) + 1000 && size < maxSize) grow();
            if (energy < 500 && size > 200) shrink();
            if (energy > seedEnergy * 2 + 50 * seedSpeed * seedSpeed / 2) sendSeed();*/
            
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
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setColor(Color.green);
        if (connected) {
            g.drawLine(rootX, rootY, x, y);
        }
        super.render(container, game, g);
    }
    
    @Override
    public void energyTick() {
        energy -= size * 1.0;
        energy += size * 0.15; // sun power
        if (connected) {
            energy -= Terrain.changeEnergy(rootX, rootY, (int) (-size * 0.9));
        }
    }
    
    public boolean sendSeed() {
        if (energy < seedEnergy) return false;
        energy -= seedEnergy;
        energy -= 50 * seedSpeed * seedSpeed / 2;
        Vector2f v = new Vector2f(World.rand.nextInt(360));
        v.scale(seedSpeed);
        EntitySeed seed = Mutator.mutateNewSeed(this);
        seed.velocity.set(v);
        World.add(seed);
        return true;
    }
    
    @Override
    public void setDead() {
        super.setDead();
        connected = false;
    }
    
}
