package character;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.JointDef;
import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import screen.GameSrceen;
import sprite.Sprite;
import sprite.SpriteLoader;

/**
 * Created by Menkung on 2/11/14.
 */
public class Gokun {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private int frame;
    private float x;
    private Body body;
    public enum State {
        IDLE,ATTK,RUN,BACK,DIE,UP,DOWN
    };
    private  State state = State.IDLE;
    private int e =0;
    private int offset = 0;

    private boolean contacted;
    private Body other;
    private int hp = 100;

    public Gokun(final World world, final float x_px, final float y_px,final float angle){
        this.sprite = SpriteLoader.getSprite("images/ext/gokun.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setTranslation(x_px, y_px + 13f);
                body = initphysicBody(world,
                        GameSrceen.M_PER_PIXEL * x_px,
                        GameSrceen.M_PER_PIXEL * y_px,angle);
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!", cause);
            }
        });

        sprite.layer().addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                state = State.ATTK;
                spriteIndex = -1;
                e = 0;

            }
        });
    }

    public void update(int delta){
        if (!hasLoaded)return;
        e += delta;

//        if (this.state == state.RUN){
//            x+=2f;
//            sprite.layer().setTranslation(x,400f);
//        }
//        if (this.state == state.BACK){
//            x-=2f;
//            sprite.layer().setTranslation(x,400f);
//        }
        if(e > 150){
            switch (state){
                case IDLE: offset = 0; frame = 2;
                    break;
                case ATTK:
                    offset = 4; frame = 3;
                    break;
                case RUN: offset = 2; frame = 1;
                    body.applyLinearImpulse(new Vec2(5f, 0f),body.getPosition());
                    break;
                case UP: offset = 2; frame=1;
                    body.applyLinearImpulse(new Vec2(0f, -50f),body.getPosition());
                    break;
                case DOWN: offset = 0; frame=2;
                    body.applyLinearImpulse(new Vec2(0f, 50f),body.getPosition());
                    break;
                case BACK: offset = 3; frame = 1;
                    body.applyLinearImpulse(new Vec2(-5f, 0f),body.getPosition());
                    break;
                case DIE: offset = 7; frame = 1;
            }

            spriteIndex = offset + ((spriteIndex + 1)%frame);
            sprite.setSprite(spriteIndex);
            e = 0;
        }

    }

    public ImageLayer layer(){
        return this.sprite.layer();
    }

    public void paint(Clock clock){
        if (!hasLoaded)return;
        sprite.layer().setTranslation(
                (body.getPosition().x / GameSrceen.M_PER_PIXEL)-10,
                body.getPosition().y / GameSrceen.M_PER_PIXEL);
        //sprite.layer().setRotation(body.getAngle());
    }

    public void setState(State state){
        this.state = state;
    }
    public State getState() { return this.state;}

    private Body initphysicBody(World world,float x, float y, float angle){
        JointDef jointdef;
        jointdef.bodyA = body;

        JointDef.collideConnected = false;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0, 0);
        Body body = world.createBody(bodyDef);

        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyType.DYNAMIC;
        bodyDef2.position = new Vec2(0, 0);
        Body body2 = world.createBody(bodyDef2);

        CircleShape circleShape = new CircleShape();
        circleShape.m_radius = getRadius();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.5f;
        circleShape.m_p.set(0, 0);
        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), angle);

        CircleShape circleShape2 = new CircleShape();
        circleShape2.m_radius = getRadius();
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = circleShape2;
        fixtureDef2.density = 10f;
        fixtureDef2.friction = 0.5f;
        fixtureDef2.restitution = 0.5f;
        circleShape2.m_p.set(0, 0);
        body2.createFixture(fixtureDef2);
        body2.setLinearDamping(0.2f);
        body2.setTransform(new Vec2(x, y), angle);

//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(25 * GameSrceen.M_PER_PIXEL / 2,
//                sprite.layer().height() * GameSrceen.M_PER_PIXEL / 3);
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = shape;
//        fixtureDef.density = 40f;
//        fixtureDef.friction = 1f;
//        fixtureDef.restitution = 1f;
//        body.createFixture(fixtureDef);
//
//        body.setLinearDamping(0.2f);
//        body.setTransform(new Vec2(x,y), 0.1f);
        return body;
    }

    float getRadius() {
        //return 1.50f;
        return 1.0f;
    }

    

    public Body body(){return this.body;}
    public int getHP(){return hp;}

    public void contact(Contact contact){
        contacted = true;
        int contactcheck = 0;
        hp-=100;

        if(hp <= 0){
            setState(State.DIE);

        }

        if(contact.getFixtureA().getBody() == body){
            other = contact.getFixtureB().getBody();
        } else {
            other = contact.getFixtureA().getBody();
        }
    }
}
