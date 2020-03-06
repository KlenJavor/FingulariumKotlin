package com.example.a6_1;

/**
 * Class has the constructor and getters to get info about a president
 */

public class Item {
    private String title;
    private int lenght;
    private int chapters;
    private String enName;
    private String fiName;
    //private String engText;
    private String audio;
    //private int audioStartPosition;

    public Item(String name, int start, int end, String nickname, String fintext, String audio) {
        this.title = name;
        this.lenght = start;
        this.chapters = end;
        this.enName = nickname;
        this.fiName = fintext;
        //this.engText = engtext;
        this.audio = audio;
    }

    public String getTitle() {
        return String.valueOf(title).toUpperCase();
    }

    public String getLenght() {
        return Integer.toString(lenght);
    }

    public String getChapters() {
        return Integer.toString(chapters);
    }

    public String getEnName() {
        return enName;
    }

    public String getFiName() {
        return fiName;
    }

    //public String getEngText() { return engText; }

    public String getAudio() { return audio; }


    @Override
    public String toString() {
        return title;
    }
}
