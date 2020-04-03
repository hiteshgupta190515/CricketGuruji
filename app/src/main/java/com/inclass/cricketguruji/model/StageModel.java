package com.inclass.cricketguruji.model;

import java.util.ArrayList;

public class StageModel {
    public String stageid,stageName;
    private ArrayList<Constants> allItemsInConstants;

    public ArrayList<Constants> getAllItemsInSection() {
        return allItemsInConstants;
    }

    public void setAllItemsInSection(ArrayList<Constants> allItemsInConstants) {
        this.allItemsInConstants = allItemsInConstants;
    }
}
