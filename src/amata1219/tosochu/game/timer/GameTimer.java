package amata1219.tosochu.game.timer;

import amata1219.tosochu.game.Game;

public class GameTimer extends Timer {

	public GameTimer(Game game) {
		super(game, game.settings.getTimeLimit());
	}

	@Override
	public void run() {
		if(isZero()){
			end();
			return;
		}
	}

	@Override
	public void start() {
		game.decideHunters();
	}

	@Override
	public void end(){
		if(isCancelled())
			return;

		cancel();
	}

}
