package com.mpolitakis.bookstore.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mpolitakis.bookstore.models.Book;





@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
 
    Book findByTitle(String title);
}
