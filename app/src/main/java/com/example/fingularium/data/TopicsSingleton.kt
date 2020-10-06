package com.example.fingularium.data

import com.example.fingularium.model.Topic
import java.util.*

/**
 * Anytime multiple classes request for Singleton, they get the same instance of topics (one and only one object)
 * Saves processing time as activity does not have to recreate e.g. when we turn the phone
 */

object TopicsSingleton  {
    val topics: ArrayList<Topic> = ArrayList()

    fun getTopic(i: Int): Topic {
        return topics[i]
    }

    init {
        topics.add(Topic("Uudenvuodenpuhe", 1919, 15, "President of the Republic Sauli Niinistö’s New Year’s Speech 2020\n", "Tasavallan presidentin uudenvuodenpuhe 2020", "uudenvuodenpuhe"))
        topics.add(Topic("Maamme", 1931, 5, "Maamme -  Finland's national anthem", "Maamme -   Suomen kansallislaulu", "maamme"))
        topics.add(Topic("Kalevala 1", 1925, 300, "Kalevala Rune 1 Birth of Väinämöinen -  national epic of Karelia and Finland (Elias Lönnrotin)", "Kalevaala Runo 1 Väinämöinen -  Suomen ja Karjalan tasavallan kansalliseepos (Elias Lönnrotin)", "kalevala_rune_1"))
        topics.add(Topic("Kalevala 18 ", 1925, 300, "Kalevala Rune 18 Väinämöinen ja Ilmarinen - national epic of Karelia and Finland (Elias Lönnrotin)", "Kalevaala Runo 18 Väinämöinen and Ilmarinen -  Suomen ja Karjalan tasavallan kansalliseepos (Elias Lönnrotin)", "kalevala_rune_18"))
        topics.add(Topic("Kalevala kilpalaulanta ", 1925, 300, "Kalevala Kilpalaulanta - national epic of Karelia and Finland", "Kalevaala Kilpalaulanta -  Suomen ja Karjalan tasavallan kansalliseepos", "kalevala_kilpalaulanta"))
        //topics.add(new Item("Talvisota", 1937, 120, "Talvisota elokuva (1989) - perustuu Antti Tuurin romaaniin Talvisota", "Winter War film (1989) - based on The Winter War, a novel by Antti Tuuri", "uudenvuodenpuhe"));
    }
}