package com.engeto.projektAPI;

import java.math.BigDecimal;

public class Product {
    private int id;
    private Long partNo;
    private String name;
    private String description;
    private Boolean isForSale;
    private BigDecimal price;

    public Product (){
    }

    public Product(Long partNo, String name, String description, Boolean isForSale, BigDecimal price) {
        this.partNo = partNo;
        this.name = name;
        this.description = description;
        this.isForSale = isForSale;
        this.price = price;
    }

    public Product(Long partNo, String name, Boolean isForSale, BigDecimal price) {
        this.partNo = partNo;
        this.name = name;
        this.isForSale = isForSale;
        this.price = price;
    }

    public Product(int id, Long partNo, String name, String description, Boolean isForSale, BigDecimal price) {
        this.id = id;
        this.partNo = partNo;
        this.name = name;
        this.description = description;
        this.isForSale = isForSale;
        this. price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getPartNo() {
        return partNo;
    }

    public void setPartNo(Long partNo) {
        this.partNo = partNo;
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

    public Boolean getIsForSale() {
        return isForSale;
    }

    public void setForSale(Boolean isForSale) {
        this.isForSale = isForSale;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
