package XML;

public class MainXML {

	DatosPersistence dp= new DatosPersistence();
		public void CargarDatos(){
	
		dp.readXML("XMLfolder/datos.xml");
		dp.pasarDatosAtemp();
		
		}
		
		public void GuardarDatos(){
		dp.guardarDomcomoFile("XMLFolder/datos.xml");
		}
		
		


}
