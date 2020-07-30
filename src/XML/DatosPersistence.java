package XML;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;

import AlmacenTemp.Temp;

import org.w3c.dom.*;



public class DatosPersistence {
	private String PARTIDA = null;
	private String DINERO = null;
	private String SALUD = null;
	private String MUNICIONEXPECIAL = null;
	private ArrayList<String> rolev;
	
	public boolean readXML(String xml) {
        rolev = new ArrayList<String>();
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the    
            // XML file
            dom = db.parse(xml);

            Element doc = dom.getDocumentElement();

            PARTIDA = getTextValue(PARTIDA, doc, "PARTIDA");
            if (PARTIDA != null) {
                if (!PARTIDA.isEmpty())
                    rolev.add(PARTIDA);
            }
            DINERO = getTextValue(DINERO, doc, "DINERO");
            if (DINERO != null) {
                if (!DINERO.isEmpty())
                    rolev.add(DINERO);
            }
            SALUD = getTextValue(SALUD, doc, "SALUD");
            if (SALUD != null) {
                if (!SALUD.isEmpty())
                    rolev.add(SALUD);
            }
            MUNICIONEXPECIAL = getTextValue(MUNICIONEXPECIAL, doc, "MUNICIONEXPECIAL");
            if ( MUNICIONEXPECIAL != null) {
                if (!MUNICIONEXPECIAL.isEmpty())
                    rolev.add(MUNICIONEXPECIAL);
            }
            return true;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return false;
    }
	
	
	
	
	private String getTextValue(String def, Element doc, String tag) {
	    String value = def;
	    NodeList nl;
	    nl = doc.getElementsByTagName(tag);
	    if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
	        value = nl.item(0).getFirstChild().getNodeValue();
	    }
	    return value;
	}




	public void guardarDomcomoFile(String ruta_archivo){
		 Document dom;
		    Element e = null;

		    // instance of a DocumentBuilderFactory
		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		    try {
		        // use factory to get an instance of document builder
		        DocumentBuilder db = dbf.newDocumentBuilder();
		        // create instance of DOM
		        dom = db.newDocument();

		        // create the root element
		        Element rootEle = dom.createElement("datos");

		        // create data elements and place them under root
		        e = dom.createElement("PARTIDA");
		        e.appendChild(dom.createTextNode("1"));
		        rootEle.appendChild(e);

		        e = dom.createElement("DINERO");
		        e.appendChild(dom.createTextNode(Temp.getPelas()+""));
		        rootEle.appendChild(e);

		        e = dom.createElement("SALUD");
		        e.appendChild(dom.createTextNode(Temp.getSalud()+""));
		        rootEle.appendChild(e);

		        e = dom.createElement("MUNICIONEXPECIAL");
		        e.appendChild(dom.createTextNode(Temp.getMunExp()+""));
		        rootEle.appendChild(e);

		        dom.appendChild(rootEle);

		        try {
		            Transformer tr = TransformerFactory.newInstance().newTransformer();
		            tr.setOutputProperty(OutputKeys.INDENT, "yes");
		            tr.setOutputProperty(OutputKeys.METHOD, "xml");
		            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

		            // send DOM to file
		            tr.transform(new DOMSource(dom), 
		                                 new StreamResult(new FileOutputStream(ruta_archivo)));

		        } catch (TransformerException te) {
		            System.out.println(te.getMessage());
		        } catch (IOException ioe) {
		            System.out.println(ioe.getMessage());
		        }
		    } catch (ParserConfigurationException o) {
		        System.out.println("Error instanciando " + o);
		    }
		
	}
	public void sacarXML(){
		for(int i = 0; i<rolev.size();i++){
			System.out.println(rolev.get(i));
	}
	
	}
	
	public void pasarDatosAtemp(){
		Temp.setPelas(Integer.parseInt(rolev.get(1)));	
		Temp.setSalud(Integer.parseInt(rolev.get(2)));	
		Temp.setMunExp(Integer.parseInt(rolev.get(3)));	
	}
	
	
}
