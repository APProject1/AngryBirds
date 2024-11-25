package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.physics.box2d.*;
import io.github.badlogic.AngryBirds.Elements.*;

public class contact implements ContactListener {
    public Level level;
    public contact(Level level){
        this.level=level;
    }
    @Override
    public void beginContact(Contact contact) {
        // Handle collision start events here
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object userDataA = fixtureA.getBody().getUserData();
        Object userDataB = fixtureB.getBody().getUserData();

        // Check for specific collisions and take actions
        if (userDataA instanceof Bird && userDataB instanceof Block) {
            Block block=(Block) userDataB;
            Bird bird=(Bird) userDataA;
            block.health-= (int) (bird.body.getLinearVelocity().len()/110)+1;
            if (block.health<=0){
                block.dispose(level);
            }
        } else if (userDataA instanceof Block && userDataB instanceof Bird) {
            Block block=(Block) userDataA;
            Bird bird=(Bird) userDataB;
            block.health-= (int) (bird.body.getLinearVelocity().len()/110)+1;
            if (block.health<=0){
                block.dispose(level);
            }
        }
        else if (userDataA instanceof Bird && userDataB instanceof Pig) {
            Pig pig=(Pig) userDataB;
            Bird bird=(Bird) userDataA;
            pig.health-= (int) (bird.body.getLinearVelocity().len()/110)+1;
            if (pig.health<=0){
                pig.dispose(level);
            }
        }
        else if (userDataB instanceof Bird && userDataA instanceof Pig) {
            Pig pig=(Pig) userDataA;
            Bird bird=(Bird) userDataB;
            pig.health-= (int) (bird.body.getLinearVelocity().len()/110)+1;
            if (pig.health<=0){
                pig.dispose(level);
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        // Handle collision end events here
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


}
