/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.VideoClub.Controller;

import io.VideoClub.Model.Client;
import io.VideoClub.Model.Data;
import io.VideoClub.Model.Enums.GameCategory;
import io.VideoClub.Model.Enums.MovieCategory;
import io.VideoClub.Model.Enums.ProductsTypes;
import io.VideoClub.Model.Game;
import io.VideoClub.Model.IClient;
import io.VideoClub.Model.Movie;
import io.VideoClub.Model.Other;
import io.VideoClub.Model.Product;
import io.VideoClub.Model.ProductNameComparator;
import io.VideoClub.Model.RepositorioClient;
import io.VideoClub.Model.RepositorioGame;
import io.VideoClub.Model.RepositorioMovie;
import io.VideoClub.Model.RepositorioOtros;
import io.VideoClub.Model.RepositorioProduct;
import io.VideoClub.Model.RepositorioReservation;
import io.VideoClub.Model.Reservation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Alvaro
 */
public class AppController implements IAppController {
    
    RepositorioClient listClients = RepositorioClient.getInstance();
    RepositorioGame listGames = RepositorioGame.getInstance();
    RepositorioMovie listMovies = RepositorioMovie.getInstance();
    RepositorioOtros listOthers = RepositorioOtros.getInstance();
    RepositorioProduct listProducts = RepositorioProduct.getInstance();
    RepositorioReservation listReservations = RepositorioReservation.getInstance();
    
    
    @Override
    public Set<Product> listAllProducts() {
        Set<Product> aux = new TreeSet<>();
        for (Product p : listProducts) {
            if (p.getStatus() != Product.Status.REMOVED) {
                aux.add(p);
            }
        }
        return aux;
    }

    @Override
    public Set<Product> listAllProducts(Comparator c) {

        Set<Product> aux = new TreeSet<>();
        for (Product p : listProducts) {
            if (p.getStatus() != Product.Status.REMOVED) {
                aux.add(p);
            }
        }
        ArrayList<Product> aux2 = new ArrayList<>(aux);
        Collections.sort(aux2, c);
        aux = new TreeSet<>(aux2);

        return aux;

    }

    @Override
    public Set<Product> listAllByType(ProductsTypes type) {
        Set<Product> data = listProducts;
        Set<Product> aux = new TreeSet<>();

        data.forEach((list) -> {
            if (list.getType() == type && list.getStatus() != Product.Status.REMOVED) {
                aux.add(list);

            }
        });
        return aux;

    }

    @Override
    public Set<Product> listAllByName(String name) {
        Set<Product> data = listProducts;
        Set<Product> aux = new TreeSet<>();
        for (Product lista : data) {
            if (lista.getName().equals(name) && lista.getStatus() != Product.Status.REMOVED) {
                aux.add(lista);

            }

        }
        return aux;

    }

    @Override
    public Set<Product> listAllByName(String name, ProductsTypes type) {
        Set<Product> data = listProducts;
        Set<Product> aux = new TreeSet<>();
        for (Product lista : data) {
            if (lista.getType() == type && lista.getName().equals(name) && lista.getStatus() != Product.Status.REMOVED) {
                aux.add(lista);
            }

        }
        return aux;
    }

    @Override
    public Set<Product> listAllByStatus(Product.Status status) {
        Set<Product> data = listProducts;
        Set<Product> aux = new TreeSet<>();
        for (Product lista : data) {
            if (lista.getStatus() == status && lista.getStatus() != Product.Status.REMOVED) {
                aux.add(lista);

            }
        }
        return aux;
    }

    @Override
    public List<Product> listAllDifferentProducts() {
        Set<Product> data = listProducts;
        TreeSet<Product> aux = new TreeSet<>(new ProductNameComparator());
        for (Product lista : data) {
            if (lista.getStatus() != Product.Status.REMOVED) {
                aux.add(lista);
            }
        }
        ArrayList<Product> listaProductos = new ArrayList<>(aux);

        return listaProductos;

    }

    @Override
    public List<Product> listAllDifferentMovies() {
        Set<Product> data = listProducts;
        TreeSet<Movie> aux = new TreeSet<>(new ProductNameComparator());
        for (Product p : data) {
            if (p instanceof Movie && p.getStatus() != Product.Status.REMOVED) {
                aux.add((Movie) p);
            }
        }
        ArrayList<Product> listaProductos = new ArrayList<>(aux);
        return listaProductos;
    }        Set<Product> data = listProducts;


    @Override
    public List<Product> listAllDifferentGames() {
        Set<Product> data = listProducts;
        TreeSet<Game> aux = new TreeSet<>(new ProductNameComparator());
        for (Product p : data) {
            if (p instanceof Game && p.getStatus() != Product.Status.REMOVED) {
                aux.add((Game) p);
            }
        }
        ArrayList<Product> listaProductos = new ArrayList<>(aux);
        return listaProductos;
    }

    @Override
    public Map<Product, Integer> listAllAmountOfProducts(String name) {
        Set<Product> datos = listProducts;
        int count = 0;
        Product producto = null;
        for(Product p : datos){
            if(p.getName().equals(name)){
                producto = p;
                count++;
            }
        }
        Map<Product, Integer> result = new HashMap<>();
        result.put(producto, count);
        return result;
    }

    @Override
    public Map<Product, Integer> listAllAmountOfProducts(ProductsTypes type, String name) {
        Set<Product> datos = listProducts;
        int count = 0;
        Product producto = null;
        for(Product p : datos){
            if(p.getName().equals(name) && p.getType().equals(type)){
                producto = p;
                count++;
            }
        }
        Map<Product, Integer> result = new HashMap<>();
        result.put(producto, count);
        return result;
    }

    @Override
    public Set<IClient> listAllClients() {
        Set<IClient> aux = new TreeSet<>();
        for (IClient c : listClients) {
            if (!c.getName().equals("BORRADO")) {
                aux.add(c);
            }
        }
        return aux;

    }

    @Override
    public Set<IClient> listAllClients(Comparator c) {
        Set<IClient> aux = new TreeSet<>();
        for (IClient cli : listClients) {
            if (!cli.getName().equals("BORRADO")) {
                aux.add(cli);
            }
        }
        ArrayList<IClient> aux2 = new ArrayList<>(aux);
        Collections.sort(aux2, c);
        aux = new TreeSet<>(aux2);

        return aux;

    }

    @Override
    public Set<IClient> listAllClientsWithReservationsNotFinished() {
        Set<Reservation> A = listReservations;
        Set<IClient> aux = new TreeSet<>();
        for (Reservation lista : A) {
            if (lista.status != Reservation.StatusReserve.FINISHED) {
                aux.add(lista.cli);

            }

        }
        return aux;

    }

    public Set<Reservation> listAllReservations(String id) {
        Set<Reservation> reservations = listReservations;
        Set<Reservation> aux = new TreeSet<>();
        reservations.forEach((r) -> {
            if (r.cli.getID().equals(id)) {
                aux.add(r);
            }
        });

        return aux;
    }

    @Override
    public Set<Reservation> listAllReservations() {
        Set<Reservation> reservations = listReservations;
        Set<Reservation> aux = new TreeSet<>();
        for (Reservation r : reservations) {
            aux.add(r);
        }

        return aux;
    }

    @Override
    public Set<Reservation> listAllReservations(Comparator c) {
        Set<Reservation> reservations = listReservations;
        List<Reservation> aux = new ArrayList<>();
        for (Reservation r : reservations) {
            aux.add(r);
        }
        aux.sort(c);
        Set<Reservation> aux1 = new TreeSet<>(aux);
        return aux1;
    }

    @Override
    public Set<Reservation> listAllReservations(Reservation.StatusReserve status) {
        Set<Reservation> reservations = listReservations;
        Set<Reservation> aux = new TreeSet<>();
        for (Reservation r : reservations) {
            if (r.status == status) {
                aux.add(r);
            }

        }

        return aux;
    }

    @Override
    public double getIncommings(LocalDate from) {
        Set<Reservation> reservations = listReservations;
        double result = 0;
        for (Reservation lista : reservations) {

            if (lista.status == Reservation.StatusReserve.FINISHED && (lista.finished.isAfter(from) || lista.finished.isEqual(from))) {
                result = result + lista.pro.getPrize();
            }
        }
        return result;

    }

    @Override
    public double getIncommings(LocalDate from, LocalDate to) {
        Set<Reservation> reservations = listReservations;
        double result = 0;
        for (Reservation lista : reservations) {

            if (lista.status == Reservation.StatusReserve.FINISHED
                    && (lista.finished.isAfter(from) || lista.finished.isEqual(from))
                    && (lista.finished.isBefore(to) || lista.finished.isEqual(to))) {
                result = result + lista.pro.getPrize();
            }
        }
        return result;
    }

    @Override
    public Map<IClient, Double> resumeAllIncomingsByClient() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  @Override
    public boolean createProduct(String name, String description, double prize) {
        Other o = new Other(name, description, prize, Product.Status.AVAILABLE);

        return listOthers.add(o);

    }

    @Override
    public boolean createMovie(ProductsTypes type, String name, String description, MovieCategory cat, int minAge, double prize) {
        Movie m = new Movie(cat, minAge, name, description, prize, Product.Status.AVAILABLE, type);

        return listMovies.add(m);
    }

    @Override
    public boolean createGame(ProductsTypes type, String name, String description, GameCategory cat, int minAge, double prize) {
        Game g = new Game(cat, minAge, name, description, prize, Product.Status.AVAILABLE, type);

        return listGames.add(g);
    }

    @Override
    public boolean createClient(String name, String phone, LocalDateTime time) {

        Client aux = new Client(name, phone);
        aux.setTime(time);

        return listClients.add(aux);

    }

    @Override
    public boolean removeClient(String id) {
        boolean result = false;
        Set<Client> A = listClients;
        for (Client a : A) {
            boolean reservationnotFinished = false;
            for (Reservation r : listReservations) {
                if (r.cli.equals(a) && r.finished == null) {
                    reservationnotFinished = true;
                }
            }
            if (a.getID().equals(id) && !reservationnotFinished) {
                Client c = (Client) a;
                c.setRemoved();
                result = true;
                break;
            }

        }

        return result;
    }

    @Override
    public boolean editClient(IClient e, IClient newC) {
        boolean result = false;
        Set<Client> A = listClients;
        if (A.contains(e)) {
            e.setName(newC.getName());
            e.setPhone(newC.getPhone());
            e.setTime(newC.getTime());
            result = true;
        }
        return result;
    }

    public Client SearchClient(String id) {
        Set<Client> A = listClients;
        Client aux = new Client("", "");
        for (IClient a : A) {
            if (a.getID().equals(id)) {
                aux = (Client) a;
                break;

            }
        }
        return aux;

    }

    public Product SearchProduct(String id) {

        for (Product p : listProducts) {
            if (p.getKey().equals(id)) {
                return p;
            }
        }

        return null;
    }

    public Product SearchProductByName(String name) {

        for (Product p : listProducts) {
            if (p.getName().equals(name)) {
                return p;
            }
        }

        return null;
    }

    @Override
    public boolean addProduct(String name) {
        Product p = SearchProductByName(name);
        boolean added;

        if (p != null) {
            try {
                p = (Product) p.clone();
                added = listProducts.add(p);
            } catch (CloneNotSupportedException ex) {
                added = false;
            }
        } else {
            added = false;
        }

        return added;
    }

    @Override
    public boolean removeProduct(String name) {
        Product p;
        boolean removed = false;
        do {
            p = SearchProductByName(name);
            if (p != null) {
                boolean reservationnotFinished = false;
                boolean isinreservation = false;
                for (Reservation r : listReservations) {
                    if (r.pro.equals(p) && r.finished == null) {
                        reservationnotFinished = true;
                    }
                    if (r.pro.equals(p)) {
                        isinreservation = true;
                    }
                }
                if (!reservationnotFinished) {
                    if (!isinreservation) {
                        removed = removed || listProducts.remove(p);
                    } else {
                        removed = removed || p.setRemoved();
                    }
                }
            }
        } while (p != null);

        return removed;
    }

    public boolean removeUniqueProduct(String id) {
        Product p;
        boolean removed = false;

        p = SearchProduct(id);
        if (p != null) {
            boolean reservationnotFinished = false;
            boolean isinreservation = false;
            for (Reservation r : listReservations) {
                if (r.pro.equals(p) && r.finished == null) {
                    reservationnotFinished = true;
                }
                if (r.pro.equals(p)) {
                    isinreservation = true;
                }
            }
            if (!reservationnotFinished) {
                if (!isinreservation) { 
                    removed = removed || listProducts.remove(p);
                } else {
                    removed = removed || p.setRemoved();
                }
            }
        }

        return removed;
    }

    @Override
    public boolean editProduct(String key, Product newP) {
        boolean edited;
        Product p = SearchProduct(key);

        if (p != null && p.getClass() == newP.getClass()) {
            edited = removeUniqueProduct(key);

            if (edited == true) {
                newP.setKey(key);
                edited = listProducts.add(newP);
            }
        } else {
            edited = false;
        }
        return edited;
    }

    @Override
    public Product isAvailableProduct(String name, ProductsTypes type) {
        Product p = null;

        for (Product aux : listProducts) {
            if (aux.getName().equals(name) && aux.getType() == type && aux.getStatus() == Product.Status.AVAILABLE) {
                p = aux;
                break;
            }
        }
        return p;
    }

    @Override
    public boolean reserveProduct(Product prod, IClient client) {
        boolean result = false;
        boolean age;
        Product p = SearchProduct(prod.getKey());
        Client c = SearchClient(client.getID());

        if (p != null && c != null) {
            age = isAged(p, c);
            if (p.getStatus() == Product.Status.AVAILABLE && age) {
                p.setStatus(Product.Status.RESERVED);
                Reservation R = new Reservation(prod, client);
                result = listReservations.add(R);
            }
        }

        return result;
    }

    public boolean isAged(Product p, Client c) {
        boolean age = false;
        if (p != null && c != null) {
            if (p instanceof Movie) {
                Movie m = (Movie) p;
                age = (LocalDate.now().getDayOfYear() >= c.getTime().getDayOfYear())
                        ? (LocalDate.now().getYear() - c.getTime().getYear()) >= m.getMinAge()
                        : (LocalDate.now().getYear() - c.getTime().getYear()) - 1 >= m.getMinAge();
            }
            if (p instanceof Game) {
                Game g = (Game) p;
                age = (LocalDate.now().getDayOfYear() >= c.getTime().getDayOfYear())
                        ? (LocalDate.now().getYear() - c.getTime().getYear()) >= g.getMinAge()
                        : (LocalDate.now().getYear() - c.getTime().getYear()) - 1 >= g.getMinAge();
            }
            if (p instanceof Other) {
                age = true;
            }
        }
        return age;
    }

    public boolean addDaysToReservation(Reservation r, int days) {
        boolean added = false;
        if (r != null) {
            r.addDays(days);
            added = true;
        }
        return added;
    }

    public Reservation searchReservationNotFinished(String productId, String clientId) {
        boolean find = false;
        Iterator<Reservation> it = listReservations.iterator();
        Reservation r = null;
        while (it.hasNext() && !find) {
            r = it.next();
            if (r.finished == null && r.cli.getID().equals(clientId) && r.pro.getKey().equals(productId)) {
                find = true;
            }
        }
        return find ? r : null;
    }

    public boolean removeReservation(Reservation r) {
        if (r != null) {
            return listReservations.remove(r);
        }else{
            return false;
        }
    }

    public Reservation searchReservation(LocalDate ini, String productId, String clientId) {
        boolean find = false;
        Iterator<Reservation> it = listReservations.iterator();
        Reservation r = null;
        while (it.hasNext() && !find) {
            r = it.next();
            if (r.ini.equals(ini) && r.cli.getID().equals(clientId) && r.pro.getKey().equals(productId)) {
                find = true;
            }
        }
        return find ? r : null;
    }

    @Override
    public double closeReservation(Reservation r) {
        double result = 0;
        if (r != null) {
            r.finish();
            result = r.getIncome();
        }

        return result;
    }

    @Override
    public boolean loadCatalogFromDDBB() {
        boolean result = true;
        try{
            listProducts.loadProduct();
            listMovies.loadMovie();
            listGames.loadGame();
            listOthers.loadOther();
        }catch(Exception e){
            System.out.println("Error al cargar BBDD");
            result = false;
        }
        
        return result;
    }

    @Override
    public boolean loadClientsFromDDBB() {
        boolean result = true;
        try{
            listClients.loadClient();
        }catch(Exception e){
            System.out.println("Error al cargar BBDD");
            result = false;
        }
        
        return result;
    }

    @Override
    public boolean loadReservationsFromDDBB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean loadAllDDBB() {
        boolean result = true;
        try{
            listProducts.loadProduct();
            listMovies.loadMovie();
            listGames.loadGame();
            listOthers.loadOther();
            listClients.loadClient();
        }catch(Exception e){
            System.out.println("Error al cargar BBDD");
            result = false;
        }
        
        return result;
    }

    @Override
    public boolean saveCatalogFromDDBB() {
        boolean result = true;
        try{
            listProducts.saveProduct();
            listMovies.saveMovie();
            listGames.saveGame();
            listOthers.saveOther();
        }catch(Exception e){
            System.out.println("Error al cargar BBDD");
            result = false;
        }
        
        return result;
    }

    @Override
    public boolean saveClientsFromDDBB() {
        boolean result = true;
        try{
            listClients.saveClient();
        }catch(Exception e){
            System.out.println("Error al cargar BBDD");
            result = false;
        }
        
        return result;
    }

    @Override
    public boolean saveReservationsFromDDBB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean saveAllDDBB() {
        boolean result = true;
        try{
            listProducts.saveProduct();
            listMovies.saveMovie();
            listGames.saveGame();
            listOthers.saveOther();
            listClients.saveClient();
        }catch(Exception e){
            System.out.println("Error al cargar BBDD");
            result = false;
        }
        
        return result;
    }

     @Override
    public double getIncommings() {
        Set<Reservation> reservations = listReservations;
        double totalIncommings = 0;
        for (Reservation r : reservations) {
            totalIncommings += r.getIncome();
        }
        return totalIncommings;
    }
    
}
