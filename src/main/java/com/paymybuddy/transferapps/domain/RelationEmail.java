package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "RelationEmail")
@Table(name = "relationEmail")
public class RelationEmail {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    String email;
    @Column
    String relativeEmail;
}
