package com.example.abirshukla.smarttravel;


/**
 * Created by abirshukla on 7/24/16.
 */
public class TripInfo {
    public static String address = "";

    public static String getGasOption() {
        return gasOption;
    }


    public static void setGasOption(String gasOption) {
        TripInfo.gasOption = gasOption;
    }

    public static String gasOption = "Distance";
    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        TripInfo.address = address;
    }
    public static boolean change = true;

    public static String getFoodOption() {
        return foodOption;
    }

    public static void setFoodOption(String foodOption) {
        TripInfo.foodOption = foodOption;
    }

    public static String foodOption = "Distance";

}