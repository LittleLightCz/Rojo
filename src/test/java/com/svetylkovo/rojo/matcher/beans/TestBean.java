package com.svetylkovo.rojo.matcher.beans;

import com.svetylkovo.rojo.annotations.DateFormat;
import com.svetylkovo.rojo.annotations.Group;
import com.svetylkovo.rojo.annotations.Regex;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Regex("(\\w+):(\\d+):(\\d+)s:(\\d+)l:(.+?)f:(.+?)d:(\\d+)big:(.+?)big:(.+)")
public class TestBean {

    @Group(1)
    private String str;
    @Group(2)
    private int intNum;
    @Group(3)
    private short shortNum;
    @Group(4)
    private long longNum;
    @Group(5)
    private float floatNum;
    @Group(6)
    private double doubleNum;
    @Group(7)
    private BigInteger bigInt;
    @Group(8)
    private  BigDecimal bigDecimal;
    @Group(9)
    @DateFormat("dd/MM/yyyy")
    private Date date;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getIntNum() {
        return intNum;
    }

    public void setIntNum(int intNum) {
        this.intNum = intNum;
    }

    public short getShortNum() {
        return shortNum;
    }

    public void setShortNum(short shortNum) {
        this.shortNum = shortNum;
    }

    public long getLongNum() {
        return longNum;
    }

    public void setLongNum(long longNum) {
        this.longNum = longNum;
    }

    public float getFloatNum() {
        return floatNum;
    }

    public void setFloatNum(float floatNum) {
        this.floatNum = floatNum;
    }

    public double getDoubleNum() {
        return doubleNum;
    }

    public void setDoubleNum(double doubleNum) {
        this.doubleNum = doubleNum;
    }

    public BigInteger getBigInt() {
        return bigInt;
    }

    public void setBigInt(BigInteger bigInt) {
        this.bigInt = bigInt;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
