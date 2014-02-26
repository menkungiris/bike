package gamemotor.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import gamemotor.core.Mygame;

public class MygameActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new Mygame());
  }
}
