package delivery.kursinis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Forum {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String forumTitle;
    private String forumDescription;

    @OneToMany (mappedBy = "forum", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
