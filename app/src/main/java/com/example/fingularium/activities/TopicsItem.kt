package com.example.fingularium.activities

/**
 * Class has the constructor and getters to get info about a topic
 */
class TopicsItem//this.engText = engtext; //private int audioStartPosition;
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