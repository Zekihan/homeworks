package tube;

public class Comment {

    private String username;
    private String comment;

    public Comment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "<html><body>" + username + "<br>" + comment + "<br>" + " " + "<br>" + "</span></body></html>}";
    }
}
