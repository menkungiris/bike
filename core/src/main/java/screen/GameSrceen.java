package screen;

import gamemotor.core.DebugDrawBox2D;
import character.Gokun;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sprite.Sprite;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by Menkung on 2/11/14.
 */
public class GameSrceen extends Screen {
    private ScreenStack ss;
    private Sprite sprite;
    private Gokun g;
    private Gokun gg;

    public static float M_PER_PIXEL = 1 / 26.6666667f;
    private static int width = 24;
    private static int height = 18;

    private World world;
    private Boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;


    public GameSrceen(ScreenStack ss){
        this.ss = ss;
    }

    @Override
    public void wasShown() {
        super.wasShown();

    }

    @Override
    public void wasAdded() {
        final HomeScreen home = new HomeScreen(ss);
        final ScoreScreen score = new ScoreScreen(ss);

        Image bgImage = assets().getImage("images/bgtest.png");
        bgImage.addCallback(new Callback<Image>() {
            @Override
            public void onSuccess(Image result) {

            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!", cause);
            }
        });
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        layer.add(bgLayer);

        Image back = assets().getImage("images/back.png");
        back.addCallback(new Callback<Image>() {
            @Override
            public void onSuccess(Image result) {

            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });
        ImageLayer backLayer = graphics().createImageLayer(back);
        layer.add(backLayer);

        backLayer.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerStart(Pointer.Event event) {
                ss.remove(ss.top());
            }
        });

        Vec2 gravity = new Vec2(0.0f, 10.0f);
        world = new World(gravity, true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if(contact.getFixtureA().getBody() == g.body()||
                   contact.getFixtureB().getBody() == g.body()){g.contact(contact);}

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });

        if(showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int) (width / GameSrceen.M_PER_PIXEL),
                    (int) (height / GameSrceen.M_PER_PIXEL)
            );
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_aabbBit |
                    DebugDraw.e_jointBit |
                    DebugDraw.e_shapeBit);
            debugDraw.setCamera(0, 0, 1f / GameSrceen.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }

        Body groud = world.createBody(new BodyDef());
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsEdge(new Vec2(2f, height-2),
                new Vec2(width-2f, height-2));
        groud.createFixture(groundShape, 0.0f);

        Body groud2 = world.createBody(new BodyDef());
        PolygonShape groundShape2 = new PolygonShape();
        groundShape2.setAsEdge(new Vec2(2f, 2),
                new Vec2(width-2f,2 ));
        groud2.createFixture(groundShape2, 0.0f);

        Body wall = world.createBody(new BodyDef());
        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsEdge(new Vec2(2f, 0),
                new Vec2(50f, 480));
        wall.createFixture(wallShape, 0.0f);

        Body wall2 = world.createBody(new BodyDef());
        PolygonShape wall2Shape = new PolygonShape();
        wall2Shape.setAsEdge(new Vec2(width-2f, 0),
                new Vec2(width-50f, 480));
        wall2.createFixture(wall2Shape, 0.0f);





        PlayN.keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyDown(Keyboard.Event event) {

                if(event.key() ==Key.R){
                    ss.remove(ss.top());
                }
                if(event.key() ==Key.H){
                    g.setState(Gokun.State.BACK);

                }
                if(event.key() ==Key.K){
                    g.setState(Gokun.State.RUN);

                }
                if(event.key() ==Key.U){
                    g.setState(Gokun.State.UP);

                }
                if(event.key() ==Key.J){
                    g.setState(Gokun.State.DOWN);

                }
                if(event.key() ==Key.P){
                    g.setState(Gokun.State.ATTK);
                }
                if (event.key() == Key.ESCAPE) {
                    ss.remove(ss.top());
                }
                if (event.key() == Key.S) {
                    ss.push(score);
                }

            }


            @Override
            public void onKeyUp(Keyboard.Event event) {
//                if(event.key()== Key.A){
//                    z.setState(Zombie.State.IDLE);
//                }
//                if(event.key()== Key.D){
//                    z.setState(Zombie.State.IDLE);
//                }
//                if(event.key()== Key.W){
//                    m.setState(Motor.State.IDLE);
//                }
                if(event.key() ==Key.H){
                    g.setState(Gokun.State.IDLE);
                }
                if(event.key() ==Key.K){
                    g.setState(Gokun.State.IDLE);
                }
                if(event.key() ==Key.U){
                    g.setState(Gokun.State.IDLE);

                }
                if(event.key() ==Key.J){
                    g.setState(Gokun.State.IDLE);

                }
                if(event.key() ==Key.P){
                    g.setState(Gokun.State.IDLE);
                }
            }
        });

         g = new Gokun(world,200f,200f,10.0f);
        this.layer.add(g.layer());




    }

    @Override
    public void update(int delta) {

        super.update(delta);
        world.step(0.033f, 10, 10);
        g.update(delta);

    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        g.paint(clock);

        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
    }
}
