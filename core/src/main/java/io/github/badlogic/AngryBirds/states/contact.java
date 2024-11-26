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
        else if (userDataB instanceof Block && userDataA instanceof Pig) {
            Pig pig=(Pig) userDataA;
            Block block=(Block) userDataB;
            if (block.body.getLinearVelocity().len()>55){
                pig.health-=1;
                if (pig.health<=0){
                    pig.dispose(level);
                }
            }
        }
        else if (userDataA instanceof Block && userDataB instanceof Pig) {
            Pig pig=(Pig) userDataB;
            Block block=(Block) userDataA;
            if (block.body.getLinearVelocity().len()>55){
                pig.health-=1;
                if (pig.health<=0){
                    pig.dispose(level);
                }
            }
        }
        else if (userDataA instanceof Block && userDataB instanceof Block) {
            Block block1=(Block) userDataA;
            Block block2=(Block) userDataB;
            System.out.println("block 1 velocity: "+block1.body.getLinearVelocity());
            System.out.println("block 2 velocity: "+block2.body.getLinearVelocity());
            if (block1.body.getLinearVelocity().len()>55){
                block2.health-=1;
                if (block2.health<=0){
                    block2.dispose(level);
                }
            }
            if (block2.body.getLinearVelocity().len()>55){
                block1.health-=1;
                if (block1.health<=0){
                    block1.dispose(level);
                }
            }

//            block.health-= (int) (bird.body.getLinearVelocity().len()/110)+1;
//            if (block.health<=0){
//                block.dispose(level);
//            }
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
