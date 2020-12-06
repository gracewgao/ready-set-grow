package com.example.readysetgrow;

public class User {

    // if names differ: @SerializedName("username")
    private String username;
    private String password;
    private int streak;
    private int temperature;
    private int soilMoisture;
    private int humidity;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.streak = 0;
        this.temperature = 0;
        this.soilMoisture = 0;
        this.humidity = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getSoilMoisture() {
        return soilMoisture;
    }

    public void setSoilMoisture(int soilMoisture) {
        this.soilMoisture = soilMoisture;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
