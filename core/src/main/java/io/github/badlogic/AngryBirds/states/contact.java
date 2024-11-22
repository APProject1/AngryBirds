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
            ((Block) userDataB).health-=1;
            if (((Block) userDataB).health==0){
                ((Block) userDataB).dispose(level);
            }
        } else if (userDataA instanceof Block && userDataB instanceof Bird) {
            ((Block) userDataA).health-=1;
            if (((Block) userDataA).health==0){
                ((Block) userDataA).dispose(level);
            }
        }
        else if (userDataA instanceof Bird && userDataB instanceof Pig) {
            ((Pig) userDataB).health-=1;
            if (((Pig) userDataB).health==0){
                ((Pig) userDataB).dispose(level);
            }
        } else if (userDataA instanceof Pig && userDataB instanceof Bird) {
            ((Pig) userDataA).health-=1;
            System.out.println(((Pig) userDataA).health);
            if (((Pig) userDataA).health==0){
                ((Pig) userDataA).dispose(level);
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
