package com.example.a6_1;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Anytime multiple classes request for Singleton, they get the same instance of presidents (one and only one object)
 * Saves processing time as activity does not have to recreate e.g. when we from the phone
 */
class Singleton {
    private static final Singleton singleton = new Singleton();
    private ArrayList<Item> presidents;


    static Singleton getInstance() {
        return singleton;
    }

    private Singleton() {
        presidents = new ArrayList<Item>();
        presidents.add(new Item("Uudenvuodenpuhe", 1919, 15, "President of the Republic Sauli Niinistö’s New Year’s Speech 2020\n", "Tasavallan presidentin uudenvuodenpuhe 2020", "uudenvuodenpuhe"));
        presidents.add(new Item("Maamme", 1931, 5, "Maamme -  Finland's national anthem", "Maamme -   Suomen kansallislaulu", "maamme"));
        presidents.add(new Item("Kalevala 1", 1925, 300, "Kalevala Rune 1 Birth of Väinämöinen -  national epic of Karelia and Finland (Elias Lönnrotin)", "Kalevaala Runo 1 Väinämöinen -  Suomen ja Karjalan tasavallan kansalliseepos (Elias Lönnrotin)", "kalevala_rune_1"));
        presidents.add(new Item("Kalevala 18 ", 1925, 300, "Kalevala Rune 18 Väinämöinen ja Ilmarinen - national epic of Karelia and Finland (Elias Lönnrotin)", "Kalevaala Runo 18 Väinämöinen and Ilmarinen -  Suomen ja Karjalan tasavallan kansalliseepos (Elias Lönnrotin)", "kalevala_rune_18"));
        presidents.add(new Item("Kalevala kilpalaulanta ", 1925, 300, "Kalevala Kilpalaulanta - national epic of Karelia and Finland", "Kalevaala Kilpalaulanta -  Suomen ja Karjalan tasavallan kansalliseepos", "kalevala_kilpalaulanta"));
        //presidents.add(new Item("Talvisota", 1937, 120, "Talvisota elokuva (1989) - perustuu Antti Tuurin romaaniin Talvisota", "Winter War film (1989) - based on The Winter War, a novel by Antti Tuuri", "uudenvuodenpuhe"));
    }

    public ArrayList<Item> getPresidents() {
        return presidents;
    }

    public Item getPresident(int i) {
        return presidents.get(i);
    }
}
