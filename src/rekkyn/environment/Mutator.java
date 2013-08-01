package rekkyn.environment;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

public class Mutator {
    
    public static EntitySeed mutateNewSeed(Plant plant) {
        Color col = plant.col;
        float seedSpeed = plant.seedSpeed;
        int seedEnergy = plant.seedEnergy;
        int seedTime = plant.seedTime;
        int maxSize = plant.maxSize;
        seedSpeed += World.rand.nextGaussian() * 3;
        seedEnergy += World.rand.nextGaussian() * 10;
        seedTime += World.rand.nextGaussian() * 10;
        maxSize += World.rand.nextGaussian() * 10;
        seedSpeed = Math.abs(seedSpeed);
        seedEnergy = Math.abs(seedEnergy);
        seedTime = Math.abs(seedTime);
        maxSize = Math.abs(maxSize);
        col = Colour.mutateHue(col);
        List traits = new ArrayList();
        traits.add(col);
        traits.add(seedSpeed);
        traits.add(seedEnergy);
        traits.add(seedTime);
        traits.add(maxSize);
        return new EntitySeed(plant.x, plant.y, plant.seedEnergy, traits, plant);
    }
}
