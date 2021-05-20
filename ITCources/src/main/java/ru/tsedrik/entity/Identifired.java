package ru.tsedrik.entity;

import java.io.Serializable;

public interface Identifired <T> extends Serializable {

    T getId();
}
