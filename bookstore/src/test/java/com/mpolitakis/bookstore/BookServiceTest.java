package com.mpolitakis.bookstore;


import com.mpolitakis.bookstore.models.Book;
import com.mpolitakis.bookstore.models.User;
import com.mpolitakis.bookstore.repositories.BookRepository;
import com.mpolitakis.bookstore.services.BookService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import java.util.Date;

@SpringBootTest(classes = BookServiceTest.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private List<Book> mockBooks;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User(1L,"testUser","polit.michalis@gmail.com", "12345");

        mockBooks = new ArrayList<>();
        mockBooks.add(new Book(1L, "Title 1", "Author 1", "ISBN 1", new Date(), mockUser));
        mockBooks.add(new Book(2L, "Title 2", "Author 2", "ISBN 2", new Date(), mockUser));
    }

    

    @Test
    void testAddBook() {
        Book newBook = new Book(3L, "New Title", "New Author", "New ISBN", new Date(), mockUser);

        bookService.addBook(newBook);

        verify(bookRepository, times(1)).save(newBook);
    }

    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        Book updatedBook = new Book(bookId, "Updated Title", "Updated Author", "Updated ISBN", new Date(), mockUser);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBooks.get(0)));

        bookService.updateBook(bookId, updatedBook);

        verify(bookRepository, times(1)).save(updatedBook);
    }

   
}

