package ru.connor.FirstSecurityApp.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
@Entity
@Table(name = "student")
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Column(name = "full_name")
    private String fullStudentName;
    @Column(name = "class_number")
    private int className;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;



}
