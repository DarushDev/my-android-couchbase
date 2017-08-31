package com.example.myandroidcouchbase.model;

import com.couchbase.lite.Database;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.View;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

public class Question {

    private String _id;
    private String _rev;
    private String text;
    private String tag;
    private String type;
    private List<String> options;

    @JsonIgnore
    private String _attachments;

    public Question() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String get_attachments() {
        return _attachments;
    }

    public void set_attachments(String _attachments) {
        this._attachments = _attachments;
    }


    public static Query getQuestions(Database database) {
        // 1
        View view = database.getView("app/questions");
        if (view.getMap() == null) {
            // 2
            view.setMap(new Mapper() {
                @Override
                // 3
                public void map(Map<String, Object> document, Emitter emitter) {
                    // 4
                    if (document.get("type").equals("question")) {
                        emitter.emit(document.get("_id"), null);
                    }
                }
            }, "1");
        }
        Query query = view.createQuery();
        return query;
    }

}
