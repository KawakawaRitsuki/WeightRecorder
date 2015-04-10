package com.kawakawaplanning.weightrecorder;

/**
 * Created by KP on 2015/03/25.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "weight_recorder")
public class LifeItem extends Model {

    //day,weight,fat,puu,pud

    @Column(name = "day")
    public String day;

    @Column(name = "weight")
    public float weight;

    @Column(name = "fat")
    public int fat;

    @Column(name = "pressureup")
    public int puu;

    @Column(name = "pressuredown")
    public int pud;

    @Column(name = "bmi")
    public double bmi;

    public LifeItem() {
        super();
    }
}