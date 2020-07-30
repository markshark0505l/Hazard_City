package Entidades;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Efectos.Animation;
import TileMap.TileMap;

public class BalaPistola extends EntidadJuego{

	public boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	public boolean explo = false;
	
	public BalaPistola (TileMap tm, boolean right, int vel){
		super(tm);
		animacion = new Animation();
		moveSpeed=vel;
	
		
		
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		
		width =60;
		height=60;
		cwidth=5;
		cheight=5;
		
		//cargar sprites
		try{
			
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream("/Sprites/Efectos/tiro.gif"));
				
			
			sprites = new BufferedImage[4];
			for(int i = 0; i< sprites.length; i++){
				sprites[i] = spritesheet.getSubimage (i * width, 0, width, height);
			}
			
			hitSprites = new BufferedImage[3];
			for(int i = 0; i< hitSprites.length; i++){
				hitSprites[i] = spritesheet.getSubimage (i * width, height, width, height);
			}
			
			animacion = new Animation();
			animacion.setFrames(sprites);
			animacion.setDelay(70);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setHit(){
		hit = true;
		animacion.setFrames(hitSprites);
		dx = 0;
	}
	public boolean getHit(){
		return  hit;
	}
	
	public boolean shouldRemove(){
		return remove;
	}

	
	public void update(){
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit){
			setHit();
		}
		
		animacion.update();
		if(hit&&animacion.hasPlayedOnce()){
			remove=true;
		}
		
	}
	
	public void draw(Graphics2D g){
		
		setMapPosition();
		
		if(facingRight) {
			g.drawImage(
				animacion.getImage(),
				(int)((x + xmap - width / 2)),
				(int)((y + ymap - height / 2)-10),
				null
			);
		}
		else {
			g.drawImage(
				animacion.getImage(),
				(int)(x + xmap - width / 2 + width),
				(int)((y + ymap - height / 2)-10),
				-width,
				height,
				null
			);
			
		}
		
	}
	
}
