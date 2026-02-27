package com.example;

public class Owner {
    private String name;
    private String address;

    public Owner(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public int hashCode() {
        // name + address -> hash code
        return name.hashCode() + address.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check if they are the same object
        if (obj == null || getClass() != obj.getClass()) return false; // Check if the object is null or of different class
        Owner other = (Owner) obj;
        return name.equals(other.name) && address.equals(other.address); // Check if name and address are equal
    }

    @Override
    public String toString() {
        return "Owner{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
