/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.VideoClub.Model;

import io.VideoClub.Model.Enums.ProductsTypes;
import java.io.File;
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
public class RepositorioOtros extends HashSet<Other> {

    private static RepositorioOtros instance = null;
    List<Other> otros;

    private RepositorioOtros() {
        otros = new ArrayList<>();
    }

    public static RepositorioOtros getInstance() {
        if (instance == null) {
            instance = new RepositorioOtros();
        }
        return instance;

    }

    @Override
    public String toString() {
        String result = "-----REPOSITORIO-----\n";
        for (Other o : instance) {
            result += "> " + o + "\n";

        }
        return result;
    }

    public void saveOther() {
        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();

            Document doc = build.newDocument();
            Element raiz = doc.createElement("ListaOtros");

            doc.appendChild(raiz);
            for (Other others : otros) {
                Element o = doc.createElement("Otros");
                Element nombre = doc.createElement("Nombre");
                nombre.appendChild(doc.createTextNode(others.getName()));
                Element d = doc.createElement("Descripcion");
                d.appendChild(doc.createTextNode(others.getDescription()));
                Element precio = doc.createElement("Precio");
                precio.appendChild(doc.createTextNode("" + others.getPrize()));
                Element status = doc.createElement("Estado");
                status.appendChild(doc.createTextNode("" + others.getStatus()));
                Element tipo = doc.createElement("Tipo");
                tipo.appendChild(doc.createTextNode("" + others.getType()));
                Element key = doc.createElement("Key");
                key.appendChild(doc.createTextNode("" + others.getKey()));

                o.appendChild(nombre);
                o.appendChild(d);
                o.appendChild(precio);
                o.appendChild(status);
                o.appendChild(tipo);
                o.appendChild(key);
                raiz.appendChild(o);
            }
            TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans = tFact.newTransformer();
            trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xlst} indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("listaotros.xml"));
            trans.transform(source, result);

        } catch (ParserConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerException ex) {
            System.out.println(ex);
        }
    }

    public void loadOther() {
        File file = new File("listaotros.xml");
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Listapeliculas");
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
                    otros.add(new Other(key, status, ProductsTypes.Otros, nombre, descripcion, precio));
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
