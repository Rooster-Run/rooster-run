package uk.ac.aston.teamproj.game.scenes;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.Player;
import uk.ac.aston.teamproj.game.screens.multi.MultiPlayScreen;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayersTab.
 */
public class PlayersTab implements Disposable {
	
	/** The Constant BAR_WIDTH. */
	private static final float BAR_WIDTH = 400;
	
	/** The Constant BAR_HEIGHT. */
	private static final float BAR_HEIGHT = 32;
	
	/** The Constant PLAYER_RADIUS. */
	private static final float PLAYER_RADIUS = 30;
	
	/** The stage. */
	private Stage stage;
	
	/** The viewport. */
	private Viewport viewport;
	
	/** The bars. */
	// progress bar
	private Image[] bars;
			
	/** The coins. */
	// coins
	private Image[] coins;
	
	/** The coins labels. */
	private Label[] coinsLabels;
	
	/** The coins collected. */
	private int[] coinsCollected;
	
	/** The hearts. */
	// lives
	private Image[][] hearts;
	
	/** The total players. */
	// players
	private final int totalPlayers;
	
	/** The relative positions. */
	private final float[] relativePositions;
	
	/** The player icons. */
	private final Image[] playerIcons;
	
	/** The player names. */
	private final Label[] playerNames;
	
	/** The map size. */
	private float mapSize;
	
	/**
	 * Instantiates a new players tab.
	 *
	 * @param sb the sb
	 * @param mapLength the map length
	 */
	public PlayersTab(SpriteBatch sb, int mapLength) {
		viewport = new FitViewport(MainGame.V_WIDTH / 3, MainGame.V_HEIGHT / 3, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		this.mapSize = mapLength;
		
		// bar, coins, lives
		Texture barTexture = new Texture("progress_bar/grey_bar.png");
		bars = new Image[MultiPlayScreen.players.size()];
		
		Texture coinTexture = new Texture("progress_bar/coin.png");
		coins = new Image[MultiPlayScreen.players.size()];
		coinsLabels = new Label[MultiPlayScreen.players.size()];
		coinsCollected = new int[MultiPlayScreen.players.size()];
		
		Texture heartTexture = new Texture("progress_bar/heart.png");
		hearts = new Image[MultiPlayScreen.players.size()][3];

		
		// players
		this.totalPlayers = MultiPlayScreen.players.size();
		this.relativePositions = new float[MultiPlayScreen.players.size()];
		this.playerIcons = new Image[MultiPlayScreen.players.size()];
		this.playerNames = new Label[MultiPlayScreen.players.size()];
		
		for (int i = 0; i < MultiPlayScreen.players.size(); i++) {
			playerIcons[i] = new Image(new Texture("progress_bar/player" + (i+1) + ".png"));
			if (MultiPlayScreen.players.get(i).getID() == MultiPlayScreen.myID) {
				playerIcons[i].setColor(1f, 1f, 1f, 1f);
			} else {
				playerIcons[i].setColor(1f, 1f, 1f, 0.6f);
			}
		}
		
		for (int i = 0, posY = 370; i < MultiPlayScreen.players.size(); i++, posY -= BAR_HEIGHT + 10 ) {
			// bars			
			bars[i] = new Image(barTexture);
			bars[i].setColor(1f, 1f, 1f, 0.5f);
			bars[i].setBounds(10, posY, BAR_WIDTH, BAR_HEIGHT);
			
			// coins
			coins[i] = new Image(coinTexture);
			coins[i].setColor(1f, 1f, 1f, 0.6f);
			coins[i].setBounds(500, posY, 32, 32);
			
			// coinsLabels
			coinsLabels[i] = new Label(String.format("\t%02d", coinsCollected[i]), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
			coinsLabels[i].setColor(1f,  1f,  1f,  0.6f);
			coinsLabels[i].setX(540);
			coinsLabels[i].setY(posY + 6);
			coinsLabels[i].setFontScale(1.8f);
			
			playerNames[i] = new Label(MultiPlayScreen.players.get(i).getName(), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
			playerNames[i].setX(20);
			playerNames[i].setY(posY + 6);
			playerNames[i].setFontScale(1.4f);
			playerNames[i].setColor(1f, 1f, 1f, 0.5f);

			// hearts
			hearts[i][0] = new Image(heartTexture);
			hearts[i][1] = new Image(heartTexture);
			hearts[i][2] = new Image(heartTexture);
			
			for (int j = 0, x = 650; j < 3; j++, x += 40) {
				hearts[i][j].setColor(1f, 1f, 1f, 0.6f);
				hearts[i][j].setBounds(x, posY + 4, 32, 25);
			}
		}

	}

	/**
	 * Draw.
	 */
	public void draw() {				
		Group group = new Group();
		for (int i = 0, posY = 370; i < totalPlayers; i++, posY -= BAR_HEIGHT + 10 ) {
			group.addActor(bars[i]);
			group.addActor(playerNames[i]);
			
			playerIcons[i].setBounds(12 + relativePositions[i], posY + 2, PLAYER_RADIUS, PLAYER_RADIUS + 3);
			group.addActor(playerIcons[i]);
			
			for (int j = 0; j < 3; j ++)
				group.addActor(hearts[i][j]);
			
			group.addActor(coins[i]);
			group.addActor(coinsLabels[i]);
		}
		
		stage.addActor(group);
		stage.draw();
		stage.act();
	}
	
	/**
	 * Dispose.
	 */
	@Override
	public void dispose() {
		stage.dispose();
	}

	
	/**
	 * Update.
	 */
	public void update() {		
		for (int i = 0; i < MultiPlayScreen.players.size(); i++) {
			Player p = MultiPlayScreen.players.get(i);
			
			float actualPosition = (p.getPosX()* MainGame.PPM) / 100;
			float percentage = (actualPosition * 100) / mapSize;			
			relativePositions[i] = (percentage * (BAR_WIDTH - PLAYER_RADIUS/2)) / 100;
	
			coinsCollected[i] = p.getCoins();
			coinsLabels[i].setText(String.format("%02d", coinsCollected[i]));

			
			for (int j = p.getLives(); j < 3; j++) 
				hearts[i][j].setVisible(false);
		}
	}
}
