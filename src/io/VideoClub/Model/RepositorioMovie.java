/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.VideoClub.Model;

import io.VideoClub.Model.Enums.MovieCategory;
import io.VideoClub.Model.Enums.ProductsTypes;
import io.VideoClub.Model.Product.Status;

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
public class RepositorioMovie extends HashSet<Movie> {

    private static RepositorioMovie instance = null;
    static List<Movie> movies;

    private RepositorioMovie() {
        movies = new ArrayList<>();
    }

    public static void main(String[] args) {
        RepositorioMovie r = RepositorioMovie.getInstance();
        //r.loadMovie();
        Movie m = new Movie("Batman", "Super heroes", 0, Status.RESERVED, MovieCategory.Horror);
        System.out.println(movies.add(m));
        for (Movie movies : r.movies) {
            System.out.println(movies);

        }
        r.saveMovie();
    }

    public static RepositorioMovie getInstance() {
        if (instance == null) {
            instance = new RepositorioMovie();
        }
        return instance;

    }

    @Override
    public String toString() {
        String result = "-----REPOSITORIO-----\n";
        for (Movie m : instance) {
            result += "> " + m + "\n";

        }
        return result;
    }

    public void saveMovie() {
        try {
            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();

            Document doc = build.newDocument();
            Element raiz = doc.createElement("Listapeliculas");

            doc.appendChild(raiz);
            for (Movie peliculas : movies) {
                Element m = doc.createElement("Pelicula");
                Element nombre = doc.createElement("Nombre");
                nombre.appendChild(doc.createTextNode(peliculas.getName()));
                Element d = doc.createElement("Descripcion");
                d.appendChild(doc.createTextNode(peliculas.getDescription()));
                Element precio = doc.createElement("Precio");
                precio.appendChild(doc.createTextNode("" + peliculas.getPrize()));
                Element status = doc.createElement("Estado");
                status.appendChild(doc.createTextNode("" + peliculas.getStatus()));
                Element tipo = doc.createElement("Tipo");
                tipo.appendChild(doc.createTextNode("" + peliculas.getType()));
                Element key = doc.createElement("Key");
                key.appendChild(doc.createTextNode("" + peliculas.getKey()));
                Element edad = doc.createElement("Edad");
                edad.appendChild(doc.createTextNode("" + peliculas.getMinAge()));

                m.appendChild(nombre);
                m.appendChild(d);
                m.appendChild(precio);
                m.appendChild(status);
                m.appendChild(tipo);
                m.appendChild(key);
                m.appendChild(edad);
                raiz.appendChild(m);
            }
            TransformerFactory tFact = TransformerFactory.newInstance();
            Transformer trans = tFact.newTransformer();
            trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xlst} indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("listapeliculas.xml"));
            trans.transform(source, result);

        } catch (ParserConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerConfigurationException ex) {
            System.out.println(ex);
        } catch (TransformerException ex) {
            System.out.println(ex);
        }
    }

    public void loadMovie() {
        File file = new File("listapeliculas.xml");
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

                    MovieCategory type = MovieCategory.Action;
                    if (eElement.getElementsByTagName("Tipo").item(0).getTextContent().equals("Horror")) {
                        type = MovieCategory.Horror;
                    } else if (eElement.getElementsByTagName("Tipo").item(0).getTextContent().equals("Love")) {
                        type = MovieCategory.Love;
                    } else if (eElement.getElementsByTagName("Tipo").item(0).getTextContent().equals("SciFi")) {
                        type = MovieCategory.SciFi;

                        movies.add(new Movie(type, key, status, ProductsTypes.Peliculas, nombre, descripcion, precio, edad));

                    }
                    
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
