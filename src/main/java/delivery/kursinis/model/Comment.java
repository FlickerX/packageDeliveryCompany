package delivery.kursinis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String commentText;

    @ManyToOne
    private Forum forum;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;
    @ManyToOne
    private Forum parentForum;

    public Comment(String title, String commentText, Forum parentForum) {
        this.title = title;
        this.commentText = commentText;
        this.parentForum = parentForum;
    }
    public Comment(String title, String commentText, Comment parentComment) {
        this.title = title;
        this.commentText = commentText;
        this.parentComment = parentComment;
    }

    @Override
    public String toString() {
        return "Title= " + title + ", Text ='" + commentText;
    }
}
