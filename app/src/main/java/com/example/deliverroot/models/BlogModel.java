package com.example.deliverroot.models;

public class BlogModel {

    String blogtitle;
    String blogimageUrl;
    String blogdesc;
    String blogusername;
    String blogdate;

    public BlogModel(String blogtitle, String blogimageUrl, String blogdesc, String blogusername, String blogdate) {
        this.blogtitle = blogtitle;
        this.blogimageUrl = blogimageUrl;
        this.blogdesc = blogdesc;
        this.blogusername = blogusername;
        this.blogdate = blogdate;
    }

    public BlogModel(){

    }

    public String getBlogtitle() {
        return blogtitle;
    }

    public String getBlogimageUrl() {
        return blogimageUrl;
    }

    public String getBlogdesc() {
        return blogdesc;
    }

    public String getBlogusername() {
        return blogusername;
    }

    public String getBlogdate() {
        return blogdate;
    }
}
