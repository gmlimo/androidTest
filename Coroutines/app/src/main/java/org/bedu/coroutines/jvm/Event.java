package org.bedu.coroutines.jvm;

public class Event {

    //Event variables
    private double mTemperature;
    private double mHumidity;

    //Class construction with two parameters
    public Event (double Temperature, double Humidity) {
        mTemperature = Temperature;
        mHumidity = Humidity;
    }

    //Methods to get the values
    public double getTemperature() {
        return mTemperature;
    }
    public double getHumidity() {
        return mHumidity;
    }
}

