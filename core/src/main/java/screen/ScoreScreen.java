package screen;

import playn.core.*;
import playn.core.util.Callback;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by Menkung on 2/11/14.
 */
public class ScoreScreen extends Screen{

    private ScreenStack ss;

    public ScoreScreen(ScreenStack ss){
        this.ss = ss;
    }

    @Override
    public void wasAdded() {
        final HomeScreen home = new HomeScreen(ss);

        Image bgImage = assets().getImage("images/bgscore.png");
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

        bgLayer.addListener(new Pointer.Adapter(){

        });

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
                ss.remove(ss.top());
            }
        });

//        PlayN.keyboard().setListener(new Keyboard.Adapter() {
//            @Override
//            public void onKeyDown(Keyboard.Event event) {
//                if (event.key() == Key.ESCAPE) {
//                    ss.remove(ss.top());
//                }
//
//            }
//        });
    }

    @Override
    public void update(int delta) {
        super.update(delta);

    }
}
