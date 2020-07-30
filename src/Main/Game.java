package Main;

import javax.swing.JFrame;

import XML.MainXML;

public class Game {
	
	/**ANOTACION Markel:
	 * This was a triumph!
	 * I'm making a note here:
	 * "HUGE SUCCESS!"
	 **/
	
	public static void main(String[] args) {		

		
		MainXML Datos= new MainXML();
		Datos.CargarDatos();
		
		JFrame window = new JFrame("Hazard City");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.pack();
		window.setVisible(true);
		
	}
	
}
