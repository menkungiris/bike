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
public class HomeScreen extends Screen {
    private final ScreenStack ss;

    public HomeScreen(ScreenStack ss) {
        this.ss = ss;
    }

    @Override
    public void wasAdded() {
        super.wasAdded();
        final GameSrceen game = new GameSrceen(ss);

        Image bgImage = assets().getImage("images/bg.png");
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

        Image start = assets().getImage("images/start.png");
        start.addCallback(new Callback<Image>() {
            @Override
            public void onSuccess(Image result) {

            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });
        ImageLayer backLayer = graphics().createImageLayer(start);
        layer.add(backLayer);
        backLayer.setTranslation(150,50);

        backLayer.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerStart(Pointer.Event event) {
                ss.push(game);
            }
        });

    }
}

