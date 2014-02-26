package gamemotor.core;

import playn.core.*;
import playn.core.util.Clock;
import screen.GameSrceen;
import screen.HomeScreen;
import tripleplay.game.ScreenStack;



public class Mygame extends Game.Default {

        public static  final  int UPDATE_RATE = 25;
        private ScreenStack ss = new ScreenStack();
        protected  final Clock.Source clock = new Clock.Source(UPDATE_RATE);

  public Mygame() {
    super(UPDATE_RATE); // call update every 33ms (30 times per second)

  }

  @Override
  public void init() {
    final HomeScreen home = new HomeScreen(ss);
    final GameSrceen tohome = new GameSrceen(ss);
    ss.push(home);

  }

  @Override
  public void update(int delta) {
      ss.update(delta);
  }

  @Override
  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
      clock.paint(alpha);
      ss.paint(clock);
  }
}
