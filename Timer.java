package interface_wordle;
import javafx.animation.AnimationTimer;

public class Timer{
    private long startTime;
    private boolean paused;
    private long elapsedTime;

    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (!paused) {
                elapsedTime = (now - startTime) / 1_000_000_000; // Convertir en secondes
            }
        }
    };

    public void start() {
        startTime = System.nanoTime();
        timer.start();
        paused = false;
    }
    
    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public void reset() {
        startTime = System.nanoTime();
        elapsedTime = 0;
    }
    
    public boolean getPauseStatus() {
    	return paused;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}

