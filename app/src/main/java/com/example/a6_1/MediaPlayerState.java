package com.example.a6_1;

public class MediaPlayerState {

    private int position;

    public MediaPlayerState(int position) {
        this.position = position;
    }

    public void setPosition(int message) {
        this.position = message;
    }

    public int getPosition() {
        return this.position;
    }
}