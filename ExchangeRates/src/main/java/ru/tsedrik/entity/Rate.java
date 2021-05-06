package ru.tsedrik.entity;

public class Rate {
    private Currency name;
    private Double rate;

    public Rate(){}

    public Rate(Currency name, Double rate) {
        this.name = name;
        this.rate = rate;
    }

    public Currency getName() {
        return name;
    }

    public void setName(Currency name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
