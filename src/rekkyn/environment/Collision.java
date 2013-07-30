package rekkyn.environment;

import org.newdawn.slick.geom.Vector2f;

public class Collision {
    
    public static void doCollision(Entity A, Entity B) {
        // Calculate relative velocity
        Vector2f Bvelocity = B.velocity.copy();
        Vector2f rv = Bvelocity.sub(A.velocity);
        
        // Calculate overlap on x axis
        float xOverlap = A.edgeLength / 2 + B.edgeLength / 2 - Math.abs(B.x - A.x);
        
        Vector2f normal = new Vector2f(0, 0);
        
        // SAT test on x axis
        if (xOverlap > 0) {
            
            // Calculate overlap on y axis
            float yOverlap = A.edgeLength / 2 + B.edgeLength / 2 - Math.abs(B.y - A.y);
            
            // SAT test on y axis
            if (yOverlap > 0) {
                // Find out which axis is axis of least penetration
                if (xOverlap < yOverlap) {
                    // Point towards B knowing that n points from A to B
                    if (B.x - A.x < 0) {
                        normal.set(-1, 0);
                    } else {
                        normal.set(1, 0);
                    }
                } else {
                    // Point toward B knowing that n points from A to B
                    if (B.y - A.y < 0) {
                        normal.set(0, -1);
                    } else {
                        normal.set(0, 1);
                    }
                }
            }
        }
        
        // Calculate relative velocity in terms of the normal direction
        float velAlongNormal = rv.dot(normal);
        
        // Do not resolve if velocities are separating
        if (velAlongNormal > 0) {
            return;
        }
        
        // Calculate restitution
        float restitution = 0.8F;
        
        // Calculate impulse scalar
        float j = -(1F + restitution) * velAlongNormal;
        j /= 1F / A.size + 1F / B.size;
        
        // Apply impulse
        Vector2f impulse = new Vector2f(normal.scale(j));
        
        Vector2f impulse2 = impulse.copy();
        
        A.velocity.sub(impulse.scale(1F / A.size));
        B.velocity.add(impulse2.scale(1F / B.size)); //TODO: make forces jazz
    }
    
}
