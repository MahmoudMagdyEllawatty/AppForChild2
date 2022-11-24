package com.app.games.model;

import java.util.ArrayList;

public class User {
    private String key;
    private String name;
    private String password;
    private String phone;
    private String age;
    private String email;
    private String imageURL ;


    public User() {
    }


    public User(String key, String name, String password, String phone, String age, String email, String imageURL) {
        this.key = key;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.age = age;
        this.email = email;
        this.imageURL = imageURL;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public static class MyCar{
        private String carName;
        private String plateNo;
        private String manufacturingYear;
        private String color;
        private String shape;
        private String expiredAt;
        private String carType;

        public MyCar() {
        }

        public MyCar(String carName, String plateNo, String manufacturingYear, String color, String shape, String expiredAt, String carType) {
            this.carName = carName;
            this.plateNo = plateNo;
            this.manufacturingYear = manufacturingYear;
            this.color = color;
            this.shape = shape;
            this.expiredAt = expiredAt;
            this.carType = carType;
        }

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public String getManufacturingYear() {
            return manufacturingYear;
        }

        public void setManufacturingYear(String manufacturingYear) {
            this.manufacturingYear = manufacturingYear;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getShape() {
            return shape;
        }

        public void setShape(String shape) {
            this.shape = shape;
        }

        public String getExpiredAt() {
            return expiredAt;
        }

        public void setExpiredAt(String expiredAt) {
            this.expiredAt = expiredAt;
        }

        public String getCarType() {
            return carType;
        }

        public void setCarType(String carType) {
            this.carType = carType;
        }
    }
}
