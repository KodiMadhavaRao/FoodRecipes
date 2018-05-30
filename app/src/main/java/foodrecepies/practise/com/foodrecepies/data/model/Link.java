package foodrecepies.practise.com.foodrecepies.data.model;

/**
 * Created by madhav on 2/28/2018.
 */

public class Link {
    private String title;
    private String link;
    private int id;

    public Link(String title, String link, int id) {
        this.title = title;
        this.link = link;
        this.id = id;
    }

    public Link(String title, String link) {

        this.title = title;
        this.link = link;
    }

    public Link() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
