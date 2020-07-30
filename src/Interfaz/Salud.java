package Interfaz;

//import Main.GamePanel;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Salud {
	
	private BufferedImage image;
	
	int salud;
	
	private double x;
	private double y;

	

	
	public Salud(String s) {
		salud=100;
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
	//	BufferedImage image = new BufferedImage(salud, 50, BufferedImage.TYPE_INT_RGB);
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
	}
	
}









