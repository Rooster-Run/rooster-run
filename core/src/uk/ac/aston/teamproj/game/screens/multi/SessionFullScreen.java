package uk.ac.aston.teamproj.game.screens.multi;

import com.badlogic.gdx.graphics.Texture;
import uk.ac.aston.teamproj.game.MainGame;

/**
 * @author Junaid, Marcus, Suleman
 * @since 17.03.2021
 * @date 17/03/2021
 */

public class SessionFullScreen extends ErrorScreen {

	public SessionFullScreen(MainGame game) {
		super(game);
	}

	@Override
	protected void initialiseBackground() {
		background = new Texture("background_screens/session_full.png");
	}

}
