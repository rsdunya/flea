package com.flea.search;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by bilgi on 5/21/2015.
 */
public class SearchResult {

    long docId;
    double score;
    String id;
    String type;
    String url;
    long version;
    String preview;
    String title;

    public SearchResult(Map<String, Object> asMap){
        if(asMap != null && asMap.size() > 0){
            try {
                for (Field field : getClass().getDeclaredFields()) {
                    if(asMap.containsKey(field.getName())){
                        field.set(this, asMap.get(field.getName()));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
