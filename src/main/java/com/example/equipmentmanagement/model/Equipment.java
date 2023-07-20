package com.example.equipmentmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Equipment {


    @Id
    private String serialNo;

    private String name;

    private String equipmentGroup;

    private Double cost;

    private String date;

    private String location;

    private String company;

    private String model;

    private String status;

    public Equipment() {
    }

    public Equipment(String serialNo, String name, String equipmentGroup, Double cost, String date, String location, String company, String model, String status) {
        this.serialNo = serialNo;
        this.name = name;
        this.equipmentGroup = equipmentGroup;
        this.cost = cost;
        this.date = date;
        this.location = location;
        this.company = company;
        this.model = model;
        this.status = status;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEquipmentGroup() {
        return equipmentGroup;
    }

    public void setEquipmentGroup(String group) {
        this.equipmentGroup = group;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany(){
        return company;
    }

    public void setCompany(String company){
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
