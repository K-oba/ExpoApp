package com.kaoba.expocr.models;

import android.util.Log;

/**
 * Created by valeriaramirez on 8/1/17.
 */

public class QAPOJO {
    private String name;
    private Long id;

    @Override

    public String toString(){return name;};

    public String getName(){return this.name;};

    public void setName(String pname){this.name = pname;};

    public Long getId(){return this.id;};

    public void setId(Long pid){this.id = pid;};
}
