/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.VideoClub.Model;

import io.VideoClub.Model.Enums.ProductsTypes;
import java.util.UUID;

public abstract class Product extends Item implements Cloneable {

    public int compareTo(Object o) {
        if (o == this) {
            return 0;
        }
        if (o instanceof Product) {
            return ((Product) o).getKey().compareTo(this.getKey());
        }
        return -1;
    }

    public enum Status {
        AVAILABLE,
        RESERVED,
        REMOVED
    }
    private String key;
    private Status status;
    private ProductsTypes type;

    public Product() {
    }

    public Product(String key, Status status, ProductsTypes type, String name, String description, double prize) {
        super(name, description, prize);
        this.key = key;
        this.status = status;
        this.type = type;
    }

    public Product(String name, String description, double prize, Status status, ProductsTypes type) {
        super(name, description, prize);
        this.key = generateRandom16Chars();
        this.status = status;
        this.type = type;
    }

    private String generateRandom16Chars() {
        return (String) UUID.randomUUID().toString().subSequence(0, 16);
    }

    public boolean equals(Object o) {
        boolean result = false;
        if (o != null) {
            if (o instanceof Product) {
                Product other = (Product) o;
                if (other.key.equals(this.key)) {
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Product clone = (Product) super.clone(); //To change body of generated methods, choose Tools | Templates.
        clone.key = generateRandom16Chars();
        return (Object) clone;
    }

    public ProductsTypes getType() {
        return type;
    }

    public void setType(ProductsTypes type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public boolean setRemoved() {
        boolean removed = false;
        if (this.status != Status.RESERVED) {
            this.status = Status.REMOVED;
            removed = true;
        }
        return removed;
    }

}
