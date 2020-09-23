package com.example.a6_1

/**
 * Class has the constructor and getters to get info about a president
 */
class Item//this.engText = engtext; //private int audioStartPosition;
(private val title: String, private val lenght: Int, private val chapters: Int, val enName: String, val fiName: String, //public String getEngText() { return engText; }
        //private String engText;
 val audio: String) {

    fun getTitle(): String {
        return title.toUpperCase()
    }

    fun getLenght(): String {
        return Integer.toString(lenght)
    }

    fun getChapters(): String {
        return Integer.toString(chapters)
    }

    override fun toString(): String {
        return title
    }

}