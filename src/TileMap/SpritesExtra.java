package TileMap;


import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class SpritesExtra {
	
	private BufferedImage image;
	
	
	private double x;
	private double y;

	
	
	public SpritesExtra(String s) {
		
		try {
			image = ImageIO.read(
				getClass().getResourceAsStream(s)
			);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setPosition(double x, double y) {
		this.x=x;
		this.y=y;
	}
	
	public void cambiarSalud(int cambio){
		this.x=this.x+cambio;
	}
	
	public void setVector(double dx, double dy) {

	}
	
	public void update() {

	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
		
		/*if(x < 0) {
			g.drawImage(
				image,
				(int)x + GamePanel.WIDTH,
				(int)y,
				null
			);
		}
		if(x > 0) {
			g.drawImage(
				image,
				(int)x - GamePanel.WIDTH,
				(int)y,
				null
			);
		}*/
	}
	
}







