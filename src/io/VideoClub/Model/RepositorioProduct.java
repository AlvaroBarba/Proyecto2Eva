/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.VideoClub.Model;


import io.VideoClub.Model.Enums.ProductsTypes;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.xml.sax.SAXException;

/**
 *
 * @author ANDREA
 */
public class RepositorioProduct extends HashSet<Product> {
    private static RepositorioProduct instance=null;
    List<Product> products;
    private RepositorioProduct(){
        products = new ArrayList<>();
    }
  
    
    public static RepositorioProduct getInstance(){
        if(instance==null){
            instance=new RepositorioProduct();
        }
        return instance;
    
    }

    @Override
    public String toString() {
        String result= "-----REPOSITORIO-----\n";
        for(Product p:instance){
            result+="> "+p+"\n";
                    
        }
        return result;
    }
    
    public void saveProduct() {
        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();

            Document doc = build.newDocument();
            Element raiz = doc.createElement("Listaproductos");

            doc.appendChild(raiz);
            for (Product productos : products) {
                Element p = doc.createElement("Productos");
                Element nombre = doc.createElement("Nombre");
                nombre.appendChild(doc.createTextNode(productos.getName()));
                Element d = doc.createElement("Descripcion");
                d.appendChild(doc.createTextNode(productos.getDescription()));
                Element precio = doc.createElement("Precio");
                precio.appendChild(doc.createTextNode("" + productos.getPrize()));
                Element status = doc.createElement("Estado");
                status.appendChild(doc.createTextNode("" + productos.getStatus()));
                Element tipo = doc.createElement("Tipo");
                tipo.appendChild(doc.createTextNode("" + productos.getType()));
                Element key = doc.createElement("Key");
                key.appendChild(doc.createTextNode("" + productos.getKey()));
        
                p.appendChild(nombre);
                p.appendChild(d);
                p.appendChild(precio);
                p.appendChild(status);
                p.appendChild(tipo);
                p.appendChild(key);
                raiz.appendChild(p);
            }
            TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans = tFact.newTransformer();
            trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xlst} indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("listaproductos.xml"));
            trans.transform(source, result);

        } catch (ParserConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerException ex) {
            System.out.println(ex);
        }
    }
      public void loadProduct() {
        File file = new File("listaproductos.xml");
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Listaproductos");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String nombre = eElement.getElementsByTagName("Nombre").item(0).getTextContent();
                    String descripcion = eElement.getElementsByTagName("Descripcion").item(0).getTextContent();
                    double precio = Double.parseDouble(eElement.getElementsByTagName("Precio").item(0).getTextContent());
                    int edad = Integer.parseInt(eElement.getElementsByTagName("Edad").item(0).getTextContent());
                    String key = eElement.getElementsByTagName("Key").item(0).getTextContent();

                    Product.Status status = Product.Status.RESERVED;
                    if (eElement.getElementsByTagName("Estado").item(0).getTextContent().equals("AVAILABLE")) {
                        status = Product.Status.AVAILABLE;
                    }

                   ProductsTypes type = ProductsTypes.Juegos;
                    if (eElement.getElementsByTagName("Tipo").item(0).getTextContent().equals("Peliculas")) {
                        type = ProductsTypes.Peliculas;
                    } else if (eElement.getElementsByTagName("Tipo").item(0).getTextContent().equals("Otros")) {
                        type = ProductsTypes.Otros;
                        
                        products.add(new Product(nombre, descripcion, precio, status, type) {});
                    } 

                    }
                    
                }

            } catch (ParserConfigurationException ex) {
            Logger.getLogger(RepositorioProduct.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(RepositorioProduct.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositorioProduct.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

