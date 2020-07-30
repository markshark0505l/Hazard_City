package Efectos;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entidades.EntidadJuego;
import TileMap.TileMap;

public class Explosion extends EntidadJuego {


	
	private int width, height;
	
	private Animation animacion;
	private BufferedImage[] sprites;
	
	private boolean eliminar;
	
	public Explosion (TileMap tm){
		super(tm);

		width=200;
		height=200;
		
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Efectos/Explosion.png"));
			sprites = new BufferedImage[10];
			for (int i= 0; i<sprites.length;i++){
				sprites[i]=spritesheet.getSubimage(i*width, 0, width, height);
			}
		}catch (Exception e) {

		}
		
		animacion = new Animation();
		animacion.setFrames(sprites);
		animacion.setDelay(50);
	}
	
	public void update(){
		animacion.update();
		if(animacion.hasPlayedOnce()){
			eliminar=true;
		}
	}
	
	public boolean paEliminar(){ return eliminar;}
	

	
	public void draw(Graphics2D g){
		setMapPosition();
		//System.out.println(x+"=========="+y);
		//g.drawImage(animacion.getImage(),x+xmap-width/2, y + ymap - height/2, null);
		
		g.drawImage(
				animacion.getImage(),
				(int)(x + xmap - width / 2),
				(int)(y + ymap - height / 2),
				null
			);
	}
	
	
}
