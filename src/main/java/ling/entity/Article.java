package ling.entity;

public class Article {
    private String title;
    private String content;
    private String url;

    public Article(String title, String content, String url) {
        this.title = title;
        this.content = content;
        this.url = url;
    }

    public Article(String title, String url) {
        this.title = title;
        this.url = url;
    }


    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
