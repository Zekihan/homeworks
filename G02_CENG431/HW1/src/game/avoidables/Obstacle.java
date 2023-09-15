package game.avoidables;

public abstract class Obstacle implements IAvoidable{

    private final Move necessaryMove;

    public Obstacle(Move necessaryMove) {
        this.necessaryMove = necessaryMove;
    }


    public Move getNecessaryMove() {
        return necessaryMove;
    }

    public double getAvoidChance(){
        return 1-necessaryMove.getFailChance();
    }

    public int getAvoidPoint(){
        return necessaryMove.getPoint();
    }

    public String avoidResult(){
        return "Hero avoided the obstacle.";
    }

    public String stumbleResult(){
        return "Hero stumbled into the obstacle";
    }

}


