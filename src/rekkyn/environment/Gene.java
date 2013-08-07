package rekkyn.environment;

public class Gene {
    
    Condition condition;
    Output output;
    EntityLiving entity;
    
    public Gene() {
        
    }
    
    public void setGene(Condition condition, Output output, EntityLiving entity) {
        this.condition = condition;
        this.output = output;
        this.entity = entity;
    }
    
    public boolean execute() {
        if (condition.execute()) {
            return output.execute();
        }
        return false;
    }
    
    public class Condition {
        Stats stat;
        Operators operator;
        int number;
        
        public Condition(Stats stat, Operators operator, int number) {
            this.stat = stat;
            this.operator = operator;
            this.number = number;
        }
        
        public boolean execute() {
            int input;
            if (stat == Stats.ENERGY) {
                input = entity.energy;
            } else if (stat == Stats.SIZE) {
                input = entity.size;
            } else {
                return false;
            }
            
            if (operator == Operators.GREATERTHAN) {
                return input > number;
            } else if (operator == Operators.LESSTHAN) {
                return input < number;
            }
            
            return false;
        }
    }
    
    public class Output {
        
        Actions action;
        
        public Output(Actions action) {
            this.action = action;
        }
        
        public boolean execute() {
            if (action == Actions.GROW) {
                return entity.grow();
            } else if (action == Actions.SHRINK) {
                return entity.shrink();
            } else if (action == Actions.SEED) {
                return ((Plant) entity).sendSeed();
            }
            return false;
        }
    }
    
    public enum Stats {
        ENERGY, SIZE;
    }
    
    public enum Operators {
        LESSTHAN, GREATERTHAN;
    }
    
    public enum Actions {
        GROW, SHRINK, SEED;
    }

}