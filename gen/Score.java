package gen;

public class Score {
    private int score;

    public Score(){
        this.score = 0;
    }

    public Score(int score){
        this.score = score;
    }

    public void incrementScore(int score){
        this.score+=score;
    }

    public int getScore(){
        return this.score;
    }
}
