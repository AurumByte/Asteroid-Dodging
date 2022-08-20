package tutorial.game;

import dev.aurumbyte.sypherengine.core.ecs.GameObject;
import dev.aurumbyte.sypherengine.core.event.InputHandler;
import dev.aurumbyte.sypherengine.core.graphics.Renderer;
import dev.aurumbyte.sypherengine.util.math.Vector2;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Objects;

public class Player extends GameObject {
    int speed = 5;
    Image playerSprite = new Image(
            Objects.requireNonNull(Player.class.getResourceAsStream("/shuttle2.png"))
    );
    public Player(InputHandler inputHandler) {
        super(inputHandler);
        this.getTransform().move(new Vector2(320, 600));
    }

    @Override
    public void update(float v) {
        if(this.getTransform().getPosition().xPos >= 640 - 50)
            this.getTransform().getPosition().xPos = 640 - 50;

        if(this.getTransform().getPosition().xPos <=0)
            this.getTransform().getPosition().xPos = 0;

        if(inputHandler.keyListener.isDown(KeyCode.RIGHT))
            this.getTransform().moveX(speed);
        if(inputHandler.keyListener.isDown(KeyCode.LEFT))
            this.getTransform().moveX(-speed);
    }

    @Override
    public void render(Renderer renderer) {
        //renderer.drawRectangle(this.getTransform().getPosition(), 50, 50, true, Color.BLUE);
        renderer.drawImage(playerSprite, this.getTransform().getPosition(), 50, 50);
    }
}
