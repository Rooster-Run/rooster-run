package uk.ac.aston.teamproj.game.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.MPClient;
import uk.ac.aston.teamproj.game.net.packet.IceEffect;
import uk.ac.aston.teamproj.game.screens.multi.MultiPlayScreen;
import uk.ac.aston.teamproj.game.sprites.Bomb;
import uk.ac.aston.teamproj.game.sprites.Brick;
import uk.ac.aston.teamproj.game.sprites.Coin;
import uk.ac.aston.teamproj.game.sprites.EndPlane;
import uk.ac.aston.teamproj.game.sprites.IceCube;
import uk.ac.aston.teamproj.game.sprites.RectangularObject;
import uk.ac.aston.teamproj.game.sprites.Lightning;
import uk.ac.aston.teamproj.game.sprites.Mud;


public class MultiWorldContactListener extends WorldContactListener {
	
	public MultiWorldContactListener(MultiPlayScreen playScreen) {
		super(playScreen);
	}

	
	protected void reactToIceCollision() {
		IceEffect packet = new IceEffect();
		packet.setToken(MultiPlayScreen.sessionID);
		packet.setPlayerID(MultiPlayScreen.myID);
		MPClient.client.sendTCP(packet);
	}

}
