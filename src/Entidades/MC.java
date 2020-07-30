package Entidades;

import TileMap.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

import Efectos.Animation;
import Sonidos.PlayerSonido;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MC extends EntidadJuego {
	
	public int playerNumber=1; 

	//static int cont = 0;
	// caracteristicas
	
	//public int contMuerte = 0;
	public boolean aMorir = false;
	public boolean muerto = false;
	private int salud;
	private int maxSalud;
	private int pelas;
	//private int arma;
	//private int armadura;
	private int munExp;
	
	public boolean munExpAc = false;
	
	
	//disparando
	private boolean disparando;
	private ArrayList<BalaPistola> balas; 
	
	//espadazo
	private boolean espadazo;
	//private int distanciaEspadazo;

	//Golpeado
	private boolean ostiau;
	private long ostiauTiempo;
	
	//agachado
	//private boolean agacharse; 
	
	// animations\
	
	
	private ArrayList<BufferedImage[]> sprites;
	
	
	private final int[] numFrames = {
		2, 6, 1, 1, 5, 1, 0
	};
	
	// animation actions
	private static final int QUIETO = 0;
	private static final int ANDANDO = 1;
	private static final int SALTANDO = 2;
	private static final int CAYENDO = 3;
	private static final int DISPARANDO = 4;
	private static final int ATAQUECQC = 5;
	//private static final int agachado = 6;
	
	//caracteristicas como enemigo
	public  boolean blokeodireccion=false;
	public  boolean blokeodireccion2=false;
	public boolean recolectado;
	public boolean puedeGolpear = true;
	
	public int objetivoX=0;
	public int objetivoY=0;
	public boolean objetivoON=false;
	public boolean palau=false;
	//audio
	private HashMap<String , PlayerSonido> sfx;
	
	public MC(TileMap tm) {
		
		super(tm);
		
		width = 60;
		height = 60;
		cwidth = 25;
		cheight =50;

		
		moveSpeed = 0.8;
		maxSpeed = 3;
		stopSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		facingRight = true;
		
		salud = maxSalud = 60;
		//distanciaEspadazo=80;
		salud = 60;
		maxSalud = 60;
		pelas = 10;
		//arma=1;
		//armadura=1;
		balas = new ArrayList<BalaPistola>();
		recolectado = false;
		
		// load sprites
		setPlayerSkin();

		
		animacion = new Animation();
		currentAction = QUIETO;
		animacion.setFrames(sprites.get(QUIETO));
		animacion.setDelay(1500);
		
		sfx = new HashMap<String, PlayerSonido> ();
		sfx.put("disparo", new PlayerSonido("/SFX/Beretta.mp3"));
		sfx.put("tiro1", new PlayerSonido("/SFX/tiro1.mp3"));
		sfx.put("tiro2", new PlayerSonido("/SFX/tiro2.mp3"));
		
		
		
	}

	public int randomNumeros(){
	    int randomInt;
	    randomInt=0;
	    Random randomGenerator = new Random();
	    randomInt = randomGenerator.nextInt(2-1+1) + 1;
	    return randomInt;
	}
	
	public void setPlayerSkin(){
		if(playerNumber==5){
			moveSpeed = 0;
			maxSpeed = 0;
		}
		if(playerNumber==3){
			moveSpeed = 0.05;
			maxSpeed = 2;
		}
		try {
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"/Sprites/Personajes/playersprites"+playerNumber+".gif"
				)
			);
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 7; i++) {		
				BufferedImage[] bi =
					new BufferedImage[numFrames[i]];	
				for(int j = 0; j < numFrames[i]; j++) {	
					if(i != 4) {
						bi[j] = spritesheet.getSubimage(
								j * width,
								i * height,
								width,
								height
						);
					}
					else {
						bi[j] = spritesheet.getSubimage(
						
								j * width * 2,
								i * height,
								width * 2,
								height
						);
					}	
				}
				sprites.add(bi);			
			}	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public int getHealth() { return salud; }
	public int getMaxHealth() { return maxSalud; }
	public double getDx(){return (int)dx;	}
	public boolean getMuerto(){
		return muerto;
	}
	
	public void setNOMuerto(){
		muerto=false;
	}
	public void setMuerto(){
		muerto=true;
	}
	public boolean getDisparando(){
		return disparando;
	}
	
	
	public void setDisparando() { 
		disparando = true;
	}


	private void getNextPosition() {
		
		// movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		// cannot move while attacking, except in air
		if(
		(currentAction == ATAQUECQC || currentAction == DISPARANDO) &&
		!(jumping || falling)) {
			dx = 0;
		}
		
		// jumping
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;	
		}
		
		// falling
		if(falling) {
			
			dy += fallSpeed;
			
			if(dy > 0) jumping = false;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			
			if(dy > maxFallSpeed) dy = maxFallSpeed;
			
		}
		
	}
	
	public void setGolpe(){
		if(playerNumber==1||playerNumber==2){
			if(facingRight){
				dx=-12;
				
			}else{
				dx=+12;
			}
			setJumping(true);
		}else{
			if(facingRight){
				dx=-5;
			}else{
				dx=+5;
			}
		}
	}
	
	public int getSalud(){
		return salud;
	}
	public void setSalud(int salud){
		this.salud=salud;
	}
		
	public int getMunExp(){
		return munExp;
	}
	
	public void setMunExp(int munExp){
		this.munExp=munExp;
	}
	
	public void masPelas(){
		pelas++;
	}
	public int getpelas(){
		return pelas;
	}
	public void setPelas(int pel){
		this.pelas= pel;
	}
	public String getpelasString(){
		return ""+pelas;
	}
	
	public void setaMorir(boolean b){
		aMorir = b;
	}
	
	public boolean getaMorir(){
		return aMorir;
	}
	
	public boolean getRecolectado(){
		return recolectado;
	}
	public void Recolectar(){
		recolectado=true;
	}
	
	public int getBalas(){
		return balas.size();
	}
	
	public boolean getBalaExplo(int posicion){
		if (balas.get(posicion).explo){
			return balas.get(posicion).hit;
		}
		return false;
	}
	
	public int getBalaX(int posicion){
		return balas.get(posicion).getx();
	}
	public int getBalaY(int posicion){
		return balas.get(posicion).gety();
		
	}
	
	public void setBalaX(int posicion,int x){
		balas.get(posicion).setDx(x);
	}
	public void setBalaY(int posicion,int y){
		balas.get(posicion).setDy(y);	}
	
	public void hitBala(int numBala){
		if(playerNumber==4){}else{
		sfx.get("tiro"+randomNumeros()).play();
		}
		
		balas.get(numBala).setHit();
	}
	
	public boolean gethitBala(int posicion){
		return balas.get(posicion).getHit();
	}
	
	public void setZonaColision(int w, int h){
		cwidth = w;
		cheight =h;
	}
	
	public void setPuedeGolpear(boolean b){
		this.puedeGolpear=b;
	}
	public boolean getPuedeGolpear(){
		return puedeGolpear;
	}
	
	public void update() {
		
		
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		//pa parar ataque cuando animacion concluya
		if(currentAction == DISPARANDO){
			if(playerNumber==3){
			if(animacion.getFrame()==4){
			muerto = true;}
			}
			if(playerNumber==5){}
			else{
			if(animacion.hasPlayedOnce()){
				//para que el sonido se pare al terminar de disparar
				//sfx.get("disparo").stop();
				disparando = false;
			}
			}
			

		}
		/*if(currentAction == ATAQUECQC){
			if(animacion.hasPlayedOnce()){ espadazo = false;}
		}*/
		if(muerto==false){
		if (disparando && currentAction != DISPARANDO){
			if(playerNumber!=3&&playerNumber!=5){
				if(playerNumber==1||playerNumber==2){
					BalaPistola bala = new BalaPistola(tileMap, facingRight,20);
					bala.setPosition(x, y);
					if(munExpAc==true){
						bala.explo=true;
						munExpAc=false;
						munExp--;		
					}
					balas.add(bala);
				}
				else{
					BalaPistola bala = new BalaPistola(tileMap, facingRight,5);
					bala.setPosition(x, y);
					balas.add(bala);	
				}
	
	
			}
		}
		//updatear balas
		for(int i = 0; i< balas.size(); i++){
			balas.get(i).update();
			if(balas.get(i).shouldRemove()){
				balas.remove(i);
				i--;
			}
		}
		
		// set animation
		if(espadazo) {
			if(currentAction != ATAQUECQC) {
				currentAction = ATAQUECQC;
				animacion.setFrames(sprites.get(ATAQUECQC));
				animacion.setDelay(50);
				width = 60;
			}
		}
		else if(disparando) {
			if(currentAction != DISPARANDO) {
				
				currentAction = DISPARANDO;
				animacion.setFrames(sprites.get(DISPARANDO));
				if(playerNumber==5){
					animacion.setDelay(200);
				}
				else if(playerNumber==1||playerNumber==2||playerNumber==4){
				animacion.setDelay(65);
				sfx.get("disparo").play();
				}else if(playerNumber==3){
					animacion.setDelay(100);
				}
				width = 120;
				
				
			}
		}
		else if(dy > 0) {

			 if(currentAction != CAYENDO) {
				currentAction = CAYENDO;
				animacion.setFrames(sprites.get(CAYENDO));
				animacion.setDelay(100);
				width = 60;
			}
		}
		else if(dy < 0) {
			if(currentAction != SALTANDO) {
				currentAction = SALTANDO;
				animacion.setFrames(sprites.get(SALTANDO));
				animacion.setDelay(-1);
				width = 60;
			}
		}
		else if(left || right) {
			if(currentAction != ANDANDO) {
				currentAction = ANDANDO;
				animacion.setFrames(sprites.get(ANDANDO));
				animacion.setDelay(150);
				width = 60;
			}
		}
		else {
			if(currentAction != QUIETO) {
				currentAction = QUIETO;
				animacion.setFrames(sprites.get(QUIETO));
				if(playerNumber==6){animacion.setDelay(2500);}
				else{
				animacion.setDelay(1000);
				}
				width = 60;
			}
		}
		
		animacion.update();
		
		// set direction
		if(currentAction != ATAQUECQC && currentAction != DISPARANDO) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
		
	}
	}
	
	
	
	public void draw(Graphics2D g) {
		
		setMapPosition();
		
		//pintar balas
		if(balas.size()>0){
		for(int i = 0; i<balas.size(); i++){
			balas.get(i).draw(g);
		}
		}
		
		// draw player
		if(ostiau) {
			long elapsed =
				(System.nanoTime() - ostiauTiempo) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		
		if(facingRight) {
			g.drawImage(
				animacion.getImage(),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				null
			);
		}
		else {
			g.drawImage(
				animacion.getImage(),
				(int)(x + xmap - width / 2 + width),
				(int)(y + ymap - height / 2),
				-width,
				height,
				null
			);
			
		}
		
	}
	
}

















