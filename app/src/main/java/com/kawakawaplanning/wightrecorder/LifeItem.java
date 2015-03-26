package com.kawakawaplanning.wightrecorder;

/**
 * Created by KP on 2015/03/25.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "wight_recorder")
public class LifeItem extends Model {

    //dat,wight,fat,puu,pud

    @Column(name = "day")
    public String day;

    @Column(name = "wight")
    public int wight;

    @Column(name = "fat")
    public int fat;

    @Column(name = "pressureup")
    public int puu;

    @Column(name = "pressuredown")
    public int pud;

    @Column(name = "bmi")
    public int bmi;

    public LifeItem() {
        super();
    }
}