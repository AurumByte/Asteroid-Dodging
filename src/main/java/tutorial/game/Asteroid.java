package tutorial.game;

import dev.aurumbyte.sypherengine.core.components.Transform;
import dev.aurumbyte.sypherengine.core.ecs.GameObject;
import dev.aurumbyte.sypherengine.core.graphics.Renderer;
import dev.aurumbyte.sypherengine.util.math.Vector2;
import javafx.scene.paint.Color;

import java.util.concurrent.ThreadLocalRandom;

public class Asteroid extends GameObject {
    int speed;
    int radius;
    boolean hasCollided = false;
    public Asteroid() {
        super(null);
        this.getTransform().moveX(ThreadLocalRandom.current().nextInt(20, 620));
        this.speed = ThreadLocalRandom.current().nextInt(6, 10);

        //inside the constructor
        this.radius = ThreadLocalRandom.current().nextInt(30, 60);
    }

    @Override
    public void update(float v) {
        this.getTransform().moveY(speed);
    }

    @Override
    public void render(Renderer renderer) {
        if(!hasCollided) {
            renderer.drawCircle(this.getTransform().getPosition(), radius, true, Color.BROWN);
        }
    }

    public int getRadius() {
        return radius;
    }

    public boolean hasCollided() {
        return hasCollided;
    }

    public void setHasCollided(boolean hasCollided) {
        this.hasCollided = hasCollided;
    }
}
