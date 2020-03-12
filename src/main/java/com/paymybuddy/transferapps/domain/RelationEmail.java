package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "relationEmail")
public class RelationEmail {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    String relativeEmail;
}
