package rekkyn.environment;

import org.newdawn.slick.geom.Vector2f;

public class Collision {
    
    public static void doCollision(Entity A, Entity B) {
        
        if (A.invMass + B.invMass == 0) return;
        
        // Calculate relative velocity
        Vector2f Bvelocity = B.velocity.copy();
        Vector2f rv = Bvelocity.sub(A.velocity);
        
        float penetration = 0;
                
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
                    penetration = xOverlap;
                } else {
                    // Point toward B knowing that n points from A to B
                    if (B.y - A.y < 0) {
                        normal.set(0, -1);
                    } else {
                        normal.set(0, 1);
                    }
                    penetration = yOverlap;
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
        float restitution = Math.min(A.restitution, B.restitution);
        
        // Calculate impulse scalar
        float j = -(1F + restitution) * velAlongNormal;
        j /= A.invMass + B.invMass;
        
        // Apply impulse
        Vector2f normal2 = normal.copy();
        Vector2f impulse = new Vector2f(normal2.scale(j));
        
        Vector2f impulse2 = impulse.copy();
        
        A.velocity.sub(impulse.scale(A.invMass));
        B.velocity.add(impulse2.scale(B.invMass)); //TODO: make forces jazz
        
        float percent = 0.2F; // usually 20% to 80%
        float slop = 0.01F; // usually 0.01 to 0.1
        Vector2f correctionA = normal.scale(Math.max( penetration - slop, 0.0f ) / (A.invMass + B.invMass) * percent);
        Vector2f correctionB = correctionA.copy();
        A.x -= correctionA.scale(A.invMass).x;
        B.x += correctionB.scale(B.invMass).x;
        A.y -= correctionA.y;
        B.y += correctionB.y;
    }
    
    public static void doEdgeCollision(Entity e) {
        if (e.x1 < 0) {
            e.x = e.edgeLength / 2;
            e.velocity.x = -e.velocity.x * 0.4F;
        }
        if (e.y1 < 0) {
            e.y = e.edgeLength / 2;
            e.velocity.y = -e.velocity.y * 0.4F;
        }
        if (e.x2 > Game.width) {
            e.x = Game.width - e.edgeLength / 2;
            e.velocity.x = -e.velocity.x * 0.4F;
        }
        if (e.y2 > Game.height) {
            e.y = Game.height - e.edgeLength / 2;
            e.velocity.y = -e.velocity.y * 0.4F;
        }
    }
}
