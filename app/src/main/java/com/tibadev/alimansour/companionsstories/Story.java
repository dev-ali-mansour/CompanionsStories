package com.tibadev.alimansour.companionsstories;

/**
 * Created by Ali Mansour on 5/21/16.
 */
public class Story {
    private String companion;
    private String title;
    private String content;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCompanion() {
        return companion;
    }

    public void setCompanion(String companion) {
        this.companion = companion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return companion;
    }
}