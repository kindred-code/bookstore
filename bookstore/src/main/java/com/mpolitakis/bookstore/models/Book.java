package com.mpolitakis.bookstore.models;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "books")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 128)
    private String title;

    @Column(nullable = false, length = 128)
    private String author;
    
    @Column(nullable = false, length = 128,unique = true)
    private String ISBN;
     

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date publishedDate;

    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user ;


    public Book() {
    }


    public Book(Long id, String title, String author, String iSBN, Date publishedDate, User user) {
        this.id = id;
        this.title = title;
        this.author = author;
        ISBN = iSBN;
        this.publishedDate = publishedDate;
        this.user = user;
    }

   

}
