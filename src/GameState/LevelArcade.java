package GameState;

import Main.GamePanel;
import TileMap.*;
import XML.MainXML;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Sonidos.PlayerSonido;
import Entidades.*;

import java.util.Random;

import AlmacenTemp.Temp;
import Efectos.Explosion;

public class LevelArcade extends GameState {
	
	MainXML Datos= new MainXML();

	public static boolean dosJug;
	
	private SpritesExtra caraPlayer1;
	private SpritesExtra caraPlayer2;
	private SpritesExtra caraPlayer3;

	private SpritesExtra debajoSalud;
	private SpritesExtra barraSalud;
	
	private TileMap tileMap;
	private Background bg;
	
	private MC player1;
	private MC player2;
	
	private ArrayList<Explosion> explosiones;
	
	//private MC enemigosKamikaze;
	private int cantEne= 1;
	private ArrayList<MC> arrayPlacadores; 
	private boolean habilitaGenerador= true;
	private MC franc1;

	
	
	//private ArrayList<MC> enemigosSniper;
	
	
	//Paldinero

	private Font font;
	private Font textMuerte;

	
	private PlayerSonido musicaFondo;
	private PlayerSonido sonidoMuerte;


	
	
	
	public LevelArcade(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		musicaFondo = new PlayerSonido("/Musica/Box16.mp3");
		sonidoMuerte = new PlayerSonido("/Musica/YOUDIED.mp3");
		/**ANOTACION Markel:
		 * Investigation Start!
		 **/
		musicaFondo.play();
	}
	
	public void init() {
		dosJug=false;
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/tilesetB&N.gif");
		tileMap.loadMap("/Maps/Arena.map");
		tileMap.setPosition(0, 0);
		//tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/bg1.gif", 0.1);
		
		//primer jugador
		player1 = new MC(tileMap);
		player1.playerNumber=1;
		player1.setPosition(700, 400);
		//segundo jugador
		player2 = new MC(tileMap);
		player2.playerNumber=2;
		player2.setPlayerSkin();
		player2.setPosition(820, 820);
		
		//Del temp a clase
		player1.setPelas(Temp.getPelas());
		player1.setSalud(Temp.getSalud());
		player1.setMunExp(Temp.getMunExp());		

		arrayPlacadores = new ArrayList<MC>();

		caraPlayer1 = new SpritesExtra("/Interfaz/P1Face.gif");
		caraPlayer1.setPosition(0, 0);
		caraPlayer2 = new SpritesExtra("/Interfaz/P1Face2.gif");
		caraPlayer2.setPosition(0, 0);
		caraPlayer3 = new SpritesExtra("/Interfaz/P1Face3.gif");
		caraPlayer3.setPosition(0, 0);
		debajoSalud =  new SpritesExtra("/Interfaz/bajosalud.gif");
		debajoSalud.setPosition(59, 1);
		barraSalud =  new SpritesExtra("/Interfaz/salud.gif");
		barraSalud.setPosition(-1, 2);
		barraSalud.cambiarSalud(player1.getSalud());
		font = new Font("Arial", Font.PLAIN, 10);
		textMuerte = new Font("Century Gothic",Font.PLAIN,100);
		
		explosiones = new ArrayList<Explosion>();

		franc1 = new MC(tileMap);
		franc1.playerNumber=4;
		franc1.setPlayerSkin();
		franc1.setPosition(80, 480);
		franc1.setJumping(true);
		franc1.objetivoON=false;
		
	}
	
	
	public void update() {
		if (player1.getSalud()<=0){
			/**ANOTACION Markel:
			 * YOU DIED 
			 **/
			Temp.setDeDonde(0);
			Temp.setPelas(0);
			Temp.setSalud(60);
			Temp.setMunExp(0);
			Datos.GuardarDatos();
			musicaFondo.stop();
			sonidoMuerte.play();
			long millis = System.currentTimeMillis();
            while(millis+8500>=System.currentTimeMillis()) { 	
            }            
			//de clase a temp
			
			gsm.setState(GameStateManager.LEVELBASE);
		}
		
		creaEnemigos();
		
		//System.out.println(player1.getx()+"-----"+player1.gety());
		// update player	
		player1.update();
		if(dosJug==true){player2.update();}
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player1.getx(),
			GamePanel.HEIGHT / 2 - player1.gety()
		);
		
		patronesIA();
		for(int i = 0; i< arrayPlacadores.size(); i++){	
			arrayPlacadores.get(i).update();

		}
		
		if(player1.getSalud()<=40){
			caraPlayer1 = new SpritesExtra("/Interfaz/P1Face.gif");
			caraPlayer1.setPosition(0, 0);
		}
		//background movimiento
		//bg.setPosition(tileMap.getx(), tileMap.gety());
		
		for(int i = 0; i< explosiones.size(); i++){
			explosiones.get(i).update();
			if(explosiones.get(i).paEliminar()){
				explosiones.remove(i);
				i--;
			}
		}
		
		for(int e = 0; e< player1.getBalas(); e++){
			if(player1.getBalaExplo(e)){
				Explosion explosion = new Explosion(tileMap);
				explosion.setPosition(player1.getBalaX(e), player1.getBalaY(e));
				explosiones.add(explosion);
			}
			
		}
		if(franc1.getMuerto()==false){
		franc1.update();
		}

		
	}
	
	public void draw(Graphics2D g) {
		//System.out.println(player1.getSalud());

		// dibujar bg
		if(bg!=null){
		bg.draw(g);
		}
		// dibujar tilemap
		if(tileMap!=null){
		tileMap.draw(g);
		}
		// dibujar jugador/es
		if(player1!=null){
		player1.draw(g);
		}
		if(dosJug==true){player2.draw(g);}
		
		for(int i = 0; i< arrayPlacadores.size(); i++){
			arrayPlacadores.get(i).draw(g);
		}
		if(franc1.getMuerto()==false){
		franc1.draw(g);
		}
		
		/*if(enemigosKamikaze!=null){
		enemigosKamikaze.draw(g);
		}*/
		
		for(int i = 0; i< explosiones.size(); i++){
			explosiones.get(i).draw(g);
		}
		debajoSalud.draw(g);
		barraSalud.draw(g);
		if(player1.getSalud()>40){
			caraPlayer1.draw(g);
		}else if (player1.getSalud()>10){
			caraPlayer2.draw(g);
		}else{
			caraPlayer3.draw(g);
		}
		
		g.setFont(font);
		g.setColor(Color.GRAY);
		g.drawString(player1.getMunExp()+"", 61, 35);
		g.setColor(Color.RED);
		g.drawString(player1.getpelasString(), 61, 22);
		
		//Muerto
		if (player1.getSalud()<=0){
			g.setFont(textMuerte);
			//System.out.println("AAAAAAAAAAA");
			g.drawString("YOU DIED", 120, 180);
		}
		
	}
	
	public void keyPressed(int k) {	

		if (k == KeyEvent.VK_ENTER){
			if(player1.gety()>=660){
				if(player1.gety()<=698){
					if (player1.getx()>=625){
						if (player1.getx()<= 695){
							musicaFondo.stop();
							//de clase a temp
							Temp.setPelas(player1.getpelas());
							Temp.setSalud(player1.getSalud());
							Temp.setMunExp(player1.getMunExp());
							Temp.setDeDonde(2);
							gsm.setState(GameStateManager.LEVELBASE);
						}
					}
				}
			}
		}
		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_A){player1.setLeft(true);}
		if (k == KeyEvent.VK_D){player1.setRight(true);}
		if (k == KeyEvent.VK_J){player1.setJumping(true);}

		if (k == KeyEvent.VK_K){player1.setDisparando();}
		if (k == KeyEvent.VK_L){if(player1.getMunExp()>0){player1.munExpAc=true;player1.setDisparando();}}
		/*if (k == KeyEvent.VK_L){player.setAtacandoCQC();}*/

		
	}
	
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		if (k == KeyEvent.VK_A){player1.setLeft(false);}
		if (k == KeyEvent.VK_D){player1.setRight(false);}
		if (k == KeyEvent.VK_J){player1.setJumping(false);}

		
	}
	
	
	private void creaEnemigos(){
		
		if(arrayPlacadores.size()==10){
			if(habilitaGenerador){
			cantEne++;
			habilitaGenerador=false;
			}
		}else if(arrayPlacadores.size()==11){
			habilitaGenerador=true;
		}
		else if(arrayPlacadores.size()==20){
			if(habilitaGenerador){
			cantEne++;
			habilitaGenerador=false;
			}
		}else if(arrayPlacadores.size()==21){
			habilitaGenerador=true;
		}
		else if(arrayPlacadores.size()==30){
			if(habilitaGenerador){
			cantEne++;
			habilitaGenerador=false;
			}
		}else if(arrayPlacadores.size()==31){
			habilitaGenerador=true;
		}
		else if(arrayPlacadores.size()==40){
			if(habilitaGenerador){
			cantEne++;
			habilitaGenerador=false;
			}
		}
		
		
		if(randomNumeros()<60){
		for(int i = 0; i<cantEne; i++ ){
			MC placador = new MC(tileMap);
			placador.playerNumber=3;
			placador.setPlayerSkin();
			placador.setPosition(randomNumeros(), 400);
			arrayPlacadores.add(placador);

		}
		}
		
		if(franc1.getMuerto()){
		if(randomNumeros()<51){
			franc1.setNOMuerto();
			
		}
		}
		//arrayPlacadores.get(0)
		
		/*enemigosKamikaze.playerNumber=3;
		enemigosKamikaze.setPlayerSkin();
		enemigosKamikaze.setPosition(200, 400);*/
	}
	
	private void patronesIA(){
		
		/**ANOTACION Markel:
		 * Don't worry, about a thing
		 * cause evey little thing gonna be al right.
		 **/
		if(randomNumeros()<60){
			if(franc1.objetivoON==false){
			franc1.objetivoX=player1.getx();
			franc1.objetivoY=player1.gety()+20;
			franc1.setDisparando();
			franc1.objetivoON=true;

			}
		}
			//}
				for(int e = 0; e< franc1.getBalas(); e++){
					if(franc1.gethitBala(e)==false){
					if(franc1.getBalaY(e)>franc1.objetivoY){
						franc1.setBalaY(e, -2);
					}
					if((franc1.getBalaY(e)<franc1.objetivoY)) {
						franc1.setBalaY(e, 2);
					}
					if(franc1.getBalaX(e)<franc1.objetivoX+2&&franc1.getBalaX(e)>franc1.objetivoX-2){
						franc1.hitBala(e);
					}

				}
				}

				if(franc1.getBalas()==0){
				franc1.objetivoON=false;
				}
				
				
			if(franc1.getMuerto()==false){
				for(int e = 0; e< player1.getBalas(); e++){
					if((franc1.gety())+21>player1.getBalaY(e)
					   &&(franc1.gety())-21<player1.getBalaY(e)
					   &&(franc1.getx())-21<player1.getBalaX(e)
					   &&(franc1.getx())+21>player1.getBalaX(e)){
						Explosion explosion = new Explosion(tileMap);
						explosion.setPosition(player1.getBalaX(e), player1.getBalaY(e));
						explosiones.add(explosion);
						player1.hitBala(e);
						franc1.setMuerto();

					}
				}
				
				for(int e = 0; e< franc1.getBalas(); e++){
					if((player1.gety())+21>franc1.getBalaY(e)
					   &&(player1.gety())-21<franc1.getBalaY(e)
					   &&(player1.getx())-21<franc1.getBalaX(e)
					   &&(player1.getx())+21>franc1.getBalaX(e)){
						franc1.hitBala(e);
						player1.setGolpe();
						if(player1.getSalud()<=0){}
						else{
						player1.setSalud(player1.getSalud()-15);
						barraSalud.cambiarSalud(-15);
						}


					}
				}
				
			}
		

		
		for(int i = 0; i< arrayPlacadores.size(); i++){
			if(arrayPlacadores.get(i).getaMorir()){
				if (arrayPlacadores.get(i).getRecolectado()==false){
					player1.masPelas();
					arrayPlacadores.get(i).Recolectar();
				}
			}
			else{

				
				
				
			if(arrayPlacadores.get(i).getDisparando()==false){
		if(arrayPlacadores.get(i).blokeodireccion==true){
			if(arrayPlacadores.get(i).getx()>1110||arrayPlacadores.get(i).getx()<200||player1.gety()>580){
				arrayPlacadores.get(i).blokeodireccion=false;			
			}
		}else if(arrayPlacadores.get(i).blokeodireccion2==true){
			if(arrayPlacadores.get(i).gety()>580){
				arrayPlacadores.get(i).blokeodireccion2=false;				
			}
		}
		else{
		
		if(arrayPlacadores.get(i).getx()< player1.getx()){
			arrayPlacadores.get(i).setLeft(false);
			arrayPlacadores.get(i).setRight(true);
		}else{
			arrayPlacadores.get(i).setRight(false);
			arrayPlacadores.get(i).setLeft(true);
		}
		//Patrones antiguos
		/*if(enemigosKamikaze.getx()<500&&enemigosKamikaze.getx()>480
			|| enemigosKamikaze.getx()<390&&enemigosKamikaze.getx()>375
			|| enemigosKamikaze.getx()<300&&enemigosKamikaze.getx()>285
			|| enemigosKamikaze.getx()<215&&enemigosKamikaze.getx()>195
			|| enemigosKamikaze.getx()<858&&enemigosKamikaze.getx()>840){
			
			enemigosKamikaze.setJumping(true);
			
		}*/
		
		if(player1.getx()>410&&player1.getx()<951&&player1.gety()<580){
		if(arrayPlacadores.get(i).getx()<970&&arrayPlacadores.get(i).getx()>951
		   ||arrayPlacadores.get(i).getx()>350&&arrayPlacadores.get(i).getx()<370
		   ||arrayPlacadores.get(i).getx()>620&&arrayPlacadores.get(i).getx()<640
		   ||arrayPlacadores.get(i).getx()>682&&arrayPlacadores.get(i).getx()<695){
			arrayPlacadores.get(i).setJumping(true);
		}else if(arrayPlacadores.get(i).gety()>650){
			arrayPlacadores.get(i).setRight(true);
			arrayPlacadores.get(i).blokeodireccion=true;
			
		}
		}
		if(player1.gety()>636&&player1.gety()<700&&arrayPlacadores.get(i).gety()<580){
			arrayPlacadores.get(i).setRight(true);
			arrayPlacadores.get(i).blokeodireccion2=true;
		}

	}
		if(arrayPlacadores.get(i).getDx()==0){
			arrayPlacadores.get(i).setPuedeGolpear(true);
			arrayPlacadores.get(i).setJumping(true);
		}
		}
			
			for(int e = 0; e< player1.getBalas(); e++){
			if((arrayPlacadores.get(i).gety())+arrayPlacadores.get(i).getCHeight()>player1.getBalaY(e)
			   &&(arrayPlacadores.get(i).gety())-arrayPlacadores.get(i).getCHeight()<player1.getBalaY(e)
			   &&(arrayPlacadores.get(i).getx())-arrayPlacadores.get(i).getCWidth()<player1.getBalaX(e)
			   &&(arrayPlacadores.get(i).getx())+arrayPlacadores.get(i).getCWidth()>player1.getBalaX(e)){

				player1.hitBala(e);
				//arrayPlacadores.get(i).setZonaColision(10, 20);
				arrayPlacadores.get(i).setDisparando();
				arrayPlacadores.get(i).setaMorir(true);;	
				

			}
			}
			for(int e = 0; e< explosiones.size(); e++){
				if((arrayPlacadores.get(i).gety())>explosiones.get(e).gety()-150
					&&(arrayPlacadores.get(i).gety())<explosiones.get(e).gety()+150
				    &&(arrayPlacadores.get(i).getx())<explosiones.get(e).getx()+150
					&&(arrayPlacadores.get(i).getx())>explosiones.get(e).getx()-150){
					arrayPlacadores.get(i).setDisparando();
					arrayPlacadores.get(i).setaMorir(true);;
				}
			}
			if((arrayPlacadores.get(i).gety())+arrayPlacadores.get(i).getCHeight()-10>player1.gety()
				&&(arrayPlacadores.get(i).gety())-arrayPlacadores.get(i).getCHeight()+10<player1.gety()
				&&(arrayPlacadores.get(i).getx())-arrayPlacadores.get(i).getCWidth()+10<player1.getx()
				&&(arrayPlacadores.get(i).getx())+arrayPlacadores.get(i).getCWidth()-10>player1.getx()){
				if (arrayPlacadores.get(i).getPuedeGolpear()){
				arrayPlacadores.get(i).setGolpe();
				arrayPlacadores.get(i).setPuedeGolpear(false);
				player1.setGolpe();
				if(player1.getSalud()<=0){}
				else{
				player1.setSalud(player1.getSalud()-5);
				barraSalud.cambiarSalud(-5);
				}
				}		
				
			}
	}
	}
	}
	
	

	public int randomNumeros(){
	    int randomInt;
	    randomInt=0;
	    Random randomGenerator = new Random();
	    randomInt = randomGenerator.nextInt(1250-50+1) + 50;
	    return randomInt;
	}
	
}












