package com.udacity.jwdnd.course1.cloudstorage.model;

//Notes Pojo class
public class Notes {
    private Integer userid;
    public Integer noteId;
    private String notetitle;
    private String notedescription;

    public Notes(Integer noteId, String notetitle, String notedescription, Integer userid) {
        this.noteId = noteId;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.userid = userid;
    }

    public Integer getnoteId()
    {
        return noteId;
    }

    public void setnoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    public Integer getUserId() {
        return userid;
    }

    public void setUserId(Integer userId) {
        this.userid = userId;
    }

}
