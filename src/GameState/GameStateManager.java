package GameState;


public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public static final int CANTGAMESTATES= 5;
	public static final int MENUSTATE = 0;
	public static final int LEVELBASE = 1;
	public static final int LEVELARENA = 2;
	public static final int LEVEL1 = 3;
	public static final int ARENA2PLAYERS = 4;

	
	public GameStateManager() {
		
		gameStates = new GameState[CANTGAMESTATES];
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE){
			gameStates[state] = new MenuState(this);}
		if(state == LEVELBASE){
			gameStates[state] = new LevelBase(this);}
		if(state == LEVELARENA){
			gameStates[state] = new LevelArcade(this);}
		if(state == LEVEL1){
			gameStates[state] = new Level1(this);}
		if(state == ARENA2PLAYERS){
			gameStates[state] = new Arena2players(this);}
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//gameStates[currentState].init();
	}
	
	public void update() {
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
}








