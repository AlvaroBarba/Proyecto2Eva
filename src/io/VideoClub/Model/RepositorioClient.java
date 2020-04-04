/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.VideoClub.Model;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author ANDREA
 */
public class RepositorioClient extends HashSet<Client> {
    private static RepositorioClient instance=null;
    List<Client> clients;
    private RepositorioClient(){
        clients=new ArrayList<>();
    }
 
    public static RepositorioClient getInstance(){
        if(instance==null){
            instance=new RepositorioClient();
        }
        return instance;
    
    }
     @Override
    public String toString() {
        String result= "-----REPOSITORIO-----\n";
        for(Client c:instance){
            result+="> "+c+"\n";
                    
        }
        return result;
    }
    public void saveClient(){
        try {
            DocumentBuilderFactory dFact=DocumentBuilderFactory.newInstance();
            DocumentBuilder build= dFact.newDocumentBuilder();
            
            Document doc = build.newDocument();
            Element raiz = doc.createElement("Listaclientes");
            
            doc.appendChild(raiz);
            for(Client cliente:clients){
                Element c=doc.createElement("Cliente");
                Element nombre=doc.createElement("Nombre");
                nombre.appendChild(doc.createTextNode(cliente.getName()));
                Element i=doc.createElement("ID");
                i.appendChild(doc.createTextNode(cliente.getID()));
                Element phone=doc.createElement("Telefono");
                phone.appendChild(doc.createTextNode(cliente.getPhone()));
                Element time= doc.createElement("Time");
                time.appendChild(doc.createTextNode(cliente.getTime().toString()));
                c.appendChild(i);
                c.appendChild(nombre);
                c.appendChild(phone);
                c.appendChild(time);
                raiz.appendChild(c);
            }
            TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans=tFact.newTransformer();
            trans.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
            trans.setOutputProperty(OutputKeys.INDENT,"yes");
            trans.setOutputProperty("{http://xml.apache.org/xlst} indent-amount","4");
            
            DOMSource source=new DOMSource(doc);
            StreamResult result=new StreamResult(new File("listaclientes.xml"));
            trans.transform(source, result);
            
            
            
        } catch (ParserConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerException ex) {
            System.out.println(ex);
        }
    }
    
    public void loadClient(){
    File file = new File("listaclientes.xml");
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Listaclientes");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String id = eElement.getElementsByTagName("ID").item(0).getTextContent();
                    String nombre = eElement.getElementsByTagName("Nombre").item(0).getTextContent();
                    String tiempo = eElement.getElementsByTagName("Time").item(0).getTextContent();
                    LocalDateTime time = LocalDateTime.parse(tiempo);
                    String telefono = eElement.getElementsByTagName("Telefono").item(0).getTextContent();
               

                    clients.add(new Client(id, nombre, telefono, time));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    
    }
    
    
            
}
