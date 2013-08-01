package rekkyn.environment;

import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class Terrain {
    static int[][] energy = new int[Game.width / 20][Game.height / 20];
    
    public static int differenceLevel = 2000;
    public static int random = 10;
    public static int energyFlow = 100;
    public static int starting = 10000;
    
    static Random rand = new Random();
    
    public static void init() {
        for (int i = 0; i < energy.length; i++) {
            for (int j = 0; j < energy[0].length; j++) {
                energy[i][j] = starting;
            }
        }
    }
    
    public static int getEnergy(int x, int y) {
        return energy[x][y];
    }
    
    public static int changeEnergy(float x, float y, int amount) {
        if (x < 0) x = 0;
        if (x > Game.width - 1) x = Game.width - 1;
        if (y < 0) y = 0;
        if (y > Game.height - 1) y = Game.height - 1;
        energy[(int) (x / 20)][(int) (y / 20)] += amount;
        if (energy[(int) (x / 20)][(int) (y / 20)] < 0) {
            amount -= energy[(int) (x / 20)][(int) (y / 20)];
            energy[(int) (x / 20)][(int) (y / 20)] = 0;
        }

        return amount;
    }
    
    public static void update(GameContainer container, StateBasedGame game, int delta) {
        if (container.getInput().isMousePressed(0)) {
            int mouseX = Mouse.getX() / 20;
            int mouseY = (Game.height - Mouse.getY()) / 20;
            energy[mouseX][mouseY] += 100;
        }
        if (container.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
            int mouseX = Mouse.getX() / 20;
            int mouseY = (Game.height - Mouse.getY()) / 20;
            energy[mouseX][mouseY] -= 100;
        }
        
        for (int i = 0; i < energy.length; i++) {
            for (int j = 0; j < energy[0].length; j++) {
                if (energy[i][j] < starting) {
                    energy[i][j] ++;
                }
                if (j > 0) {
                    if (energy[i][j - 1] + differenceLevel < energy[i][j]) {
                        if (rand.nextInt(random) == 0) {
                            energy[i][j - 1] += energyFlow;
                            energy[i][j] -= energyFlow;
                        }
                    }
                }
                if (j < energy[0].length - 1) {
                    if (energy[i][j + 1] + differenceLevel < energy[i][j]) {
                        if (rand.nextInt(random) == 0) {
                            energy[i][j + 1] += energyFlow;
                            energy[i][j] -= energyFlow;
                        }
                    }
                }
                if (i > 0) {
                    if (energy[i - 1][j] + differenceLevel < energy[i][j]) {
                        if (rand.nextInt(random) == 0) {
                            energy[i - 1][j] += energyFlow;
                            energy[i][j] -= energyFlow;
                        }
                    }
                }
                if (i < energy.length - 1) {
                    if (energy[i + 1][j] + differenceLevel < energy[i][j]) {
                        if (rand.nextInt(random) == 0) {
                            energy[i + 1][j] += energyFlow;
                            energy[i][j] -= energyFlow;
                        }
                    }
                }
            }
        }
        
    }
    
}
