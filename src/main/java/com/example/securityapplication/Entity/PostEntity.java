package com.example.securityapplication.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "postData")
//@Audited //this annotation means inside this class all the field are audited now(we can see who ever change and which time change)
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    //@NotAudited // this is not auditing means what ever make change in descr it is not reflect or trigger in database (who and when)
    private String descr;


}
