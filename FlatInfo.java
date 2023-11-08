package org.example;

public class FlatInfo {
    private String linkDebug; // +
    private String flatName; // +
    private int timeToMetro; // +
    private String flatPrice; // +
    private float commonArea; // +
    private float commonKitchen; // +
    private String floor; // +
    private int houseAge; // +
    private float roomHeight; // +
    private String oneSquareMeterPrice; // +
    private String flatType; // + (new or used)
    private int bathroomsQuantity; // +
    private String houseType; // + (brick or monolith or panel)
    private String parkingType; // +
    private int balconyQuantity; // +
    private String typeOfRepair; // + (euro or design or without)

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public void setBathroomsQuantity(int bathroomsQuantity) {
        this.bathroomsQuantity = bathroomsQuantity;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }

    public void setBalconyQuantity(int balconyQuantity) {
        this.balconyQuantity = balconyQuantity;
    }

    public void setTypeOfRepair(String typeOfRepair) {
        this.typeOfRepair = typeOfRepair;
    }

    public void setLinkDebug(String linkDebug) {
        System.out.println(linkDebug);
        this.linkDebug = linkDebug;
    }

    public void setHouseAge(int houseAge) {
        this.houseAge = houseAge;
    }

    public void setFlatName(String flatName) {
        this.flatName = flatName;
    }

    public void setTimeToMetro(int timeToMetro) {
        this.timeToMetro = timeToMetro;
    }

    public void setFlatPrice(String flatPrice) {
        this.flatPrice = flatPrice;
    }

    public void setCommonArea(float commonArea) {
        this.commonArea = commonArea;
    }

    public void setCommonKitchen(float commonKitchen) {
        this.commonKitchen = commonKitchen;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setRoomHeight(float roomHeight) {
        this.roomHeight = roomHeight;
    }

    public void setOneSquareMeterPrice(String oneSquareMeterPrice) {
        this.oneSquareMeterPrice = oneSquareMeterPrice;
    }

    public String getLinkDebug() {
        return linkDebug;
    }

    public int getHouseAge() {
        return houseAge;
    }

    public String getFlatName() {
        return flatName;
    }

    public int getTimeToMetro() {
        return timeToMetro;
    }

    public String getFlatPrice() {
        return flatPrice;
    }

    public float getCommonArea() {
        return commonArea;
    }

    public float getCommonKitchen() {
        return commonKitchen;
    }

    public String getFloor() {
        return floor;
    }


    public float getRoomHeight() {
        return roomHeight;
    }


    public String getOneSquareMeterPrice() {
        return oneSquareMeterPrice;
    }

    public String getFlatType() {
        return flatType;
    }

    public int getBathroomsQuantity() {
        return bathroomsQuantity;
    }

    public String getHouseType() {
        return houseType;
    }

    public String getParkingType() {
        return parkingType;
    }

    public int getBalconyQuantity() {
        return balconyQuantity;
    }

    public String getTypeOfRepair() {
        return typeOfRepair;
    }
}
