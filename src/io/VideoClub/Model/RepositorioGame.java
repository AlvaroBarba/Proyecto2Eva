/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.VideoClub.Model;

import io.VideoClub.Model.Enums.GameCategory;
import io.VideoClub.Model.Enums.ProductsTypes;
import io.VideoClub.Model.Product.Status;
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
public class RepositorioGame extends HashSet<Game> {

    private static RepositorioGame instance = null;
    static List<Game> games;

    private RepositorioGame() {
        games = new ArrayList<>();
    }

    public static void main(String[] args) {
        RepositorioGame r = RepositorioGame.getInstance();
        r.loadGame();
        //Game g = new Game("LOL", "Juego truño", 20, Status.RESERVED, GameCategory.Cars);
       // System.out.println(games.add(g));
        for (Game games : r.games) {
            System.out.println(games);

        }
       // r.saveGame();
    }

    public static RepositorioGame getInstance() {
        if (instance == null) {
            instance = new RepositorioGame();
        }
        return instance;

    }

    @Override
    public String toString() {
        String result = "-----REPOSITORIO-----\n";
        for (Game g : instance) {
            result += "> " + g + "\n";

        }
        return result;
    }

    public void saveGame() {
        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();

            Document doc = build.newDocument();
            Element raiz = doc.createElement("Listajuegos");

            doc.appendChild(raiz);
            for (Game juegos : games) {
                Element j = doc.createElement("Juego");
                Element nombre = doc.createElement("Nombre");
                nombre.appendChild(doc.createTextNode(juegos.getName()));
                Element d = doc.createElement("Descripcion");
                d.appendChild(doc.createTextNode(juegos.getDescription()));
                Element precio = doc.createElement("Precio");
                precio.appendChild(doc.createTextNode("" + juegos.getPrize()));
                Element status = doc.createElement("Estado");
                status.appendChild(doc.createTextNode("" + juegos.getStatus()));
                Element tipo = doc.createElement("Tipo");
                tipo.appendChild(doc.createTextNode("" + juegos.getType()));
                Element key = doc.createElement("Key");
                key.appendChild(doc.createTextNode("" + juegos.getKey()));
                Element edad = doc.createElement("Edad");
                edad.appendChild(doc.createTextNode("" + juegos.getMinAge()));

                j.appendChild(nombre);
                j.appendChild(d);
                j.appendChild(precio);
                j.appendChild(status);
                j.appendChild(tipo);
                j.appendChild(key);
                j.appendChild(edad);
                raiz.appendChild(j);
            }
            TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans = tFact.newTransformer();
            trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xlst} indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("listajuegos.xml"));
            trans.transform(source, result);

        } catch (ParserConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerException ex) {
            System.out.println(ex);
        }
    }

    public void loadGame() {
        File file = new File("listajuegos.xml");
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Listajuegos");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String nombre = eElement.getElementsByTagName("Nombre").item(0).getTextContent();
                    String descripcion = eElement.getElementsByTagName("Descripcion").item(0).getTextContent();
                    double precio = Double.parseDouble(eElement.getElementsByTagName("Precio").item(0).getTextContent());
                    int edad = Integer.parseInt(eElement.getElementsByTagName("Edad").item(0).getTextContent());
                    String key = eElement.getElementsByTagName("Key").item(0).getTextContent();

                    Status status = Status.RESERVED;
                    if (eElement.getElementsByTagName("Estado").item(0).getTextContent().equals("AVAILABLE")) {
                        status = Status.AVAILABLE;
                    }

                    GameCategory type = GameCategory.Shooter;
                    if (eElement.getElementsByTagName("Tipo").item(0).getTextContent().equals("Adventures")) {
                        type = GameCategory.Adventures;
                    } else if (eElement.getElementsByTagName("Tipo").item(0).getTextContent().equals("Cars")) {
                        type = GameCategory.Cars;
                    }

                    games.add(new Game(type, key, status, ProductsTypes.Juegos, nombre, descripcion, precio, edad));

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
