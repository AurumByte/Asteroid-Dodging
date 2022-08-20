package tutorial.game;

import dev.aurumbyte.sypherengine.core.GameManager;
import dev.aurumbyte.sypherengine.core.SypherEngine;
import dev.aurumbyte.sypherengine.core.config.EngineConfig;
import dev.aurumbyte.sypherengine.core.ecs.GameObject;
import dev.aurumbyte.sypherengine.core.graphics.Renderer;
import dev.aurumbyte.sypherengine.util.math.Vector2;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends GameManager {
    //Player field
    Player player = new Player(this.getInputHandler());
    boolean asteroidSpawned = false;
    float timeSinceSpawnedAsteroid = 0f;
    boolean canSpawnAsteroid = true;

    float timeOfSurvival = 0f;


    Vector2 scorePosition = new Vector2(10, 30);

    @Override
    public void init(SypherEngine sypherEngine) {
        sypherEngine.getRenderer().setBackgroundColor(Color.BLACK);
        entities.add(player);
    }

    @Override
    public void update(float v) {
        timeOfSurvival += v;

        //removing the asteroid once it goes offscreen
        for(GameObject gameObject : entities) {
            if(gameObject instanceof Asteroid asteroid){
                if(asteroid.getTransform().getPosition().xPos > 730) {
                    entities.remove(asteroid);
                }
            }
        }

        //Collisions
        Vector2 playerPosition = player.getTransform().getPosition();
        float playerWidth = 50;
        float asteroidRadius;

        for(GameObject entity : entities) {
            if(entity instanceof Asteroid asteroid) {
                if (!asteroid.hasCollided()) {
                    asteroidRadius = asteroid.getRadius();
                    Vector2 asteroidPosition = asteroid.getTransform().getPosition();
                    if (playerPosition.yPos < asteroidPosition.yPos + asteroidRadius
                            && playerPosition.yPos + playerWidth > asteroidPosition.yPos - asteroidRadius) {
                        if (
                                (playerPosition.xPos + playerWidth > asteroidPosition.xPos - asteroidRadius
                                        && playerPosition.xPos + playerWidth < asteroidPosition.xPos + asteroidRadius)
                                        || (playerPosition.xPos < asteroidPosition.xPos + asteroidRadius
                                        && playerPosition.xPos > asteroidPosition.xPos - asteroidRadius)
                        ) {
                            asteroid.setHasCollided(true);
                            timeOfSurvival = 0;
                        }
                    }
                }
            }
        }

        //Asteroid spawning

        if(asteroidSpawned){
            timeSinceSpawnedAsteroid += v;
        }

        if(timeSinceSpawnedAsteroid >= 0.5f){
            canSpawnAsteroid = true;
            asteroidSpawned = false;
            timeSinceSpawnedAsteroid = 0f;
        }

        if(canSpawnAsteroid){
            entities.add(new Asteroid());
            canSpawnAsteroid = false;
            asteroidSpawned = true;
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.drawText(
                "Time Survived: " + (int)timeOfSurvival,
                scorePosition,
                Color.WHITE,
                Font.font(40)
        );
    }

    public static void main(String[] args){
        EngineConfig engineConfig = new EngineConfig();
        engineConfig.setWindowResolution(640, 720);
        engineConfig.setTitle("Asteroid Dodging");

        SypherEngine.init(new Main(), engineConfig);
        SypherEngine.run();
    }
}
