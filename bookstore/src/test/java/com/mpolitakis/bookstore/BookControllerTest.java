package com.mpolitakis.bookstore;

import com.mpolitakis.bookstore.configuration.JwtConfiguration;
import com.mpolitakis.bookstore.controllers.BookController;
import com.mpolitakis.bookstore.exceptions.BookException;
import com.mpolitakis.bookstore.models.Book;
import com.mpolitakis.bookstore.repositories.UserRepository;
import com.mpolitakis.bookstore.services.BookService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = {BookControllerTest.class, JwtConfiguration.class })
class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JwtConfiguration jwtConfiguration;

    @InjectMocks
    private BookController bookController;

  
    @Test
    void testGetAllBooks() {
       
        List<Book> mockBooks = Arrays.asList(new Book(), new Book());
        when(bookService.getAllBooks()).thenReturn(mockBooks);

       
        ResponseEntity<List<Book>> responseEntity = bookController.getAllBooks();

       
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockBooks, responseEntity.getBody());
    }

   

    @Test
    void testAddBook_BookExists() {
        // Given
        Book mockBook = createMockBook();
        when(bookService.findBookByTitle(anyString())).thenReturn(Optional.of(mockBook));

        // When and Then
        assertThrows(BookException.class, () -> bookController.addBook(mockBook));
    }

    @Test
    void testFindBookById() throws BookException {
        
        Long bookId = 1L;
        Book mockBook = createMockBook();
        when(bookService.findBookById(bookId)).thenReturn(Optional.of(mockBook));

        
        ResponseEntity<Optional<Book>> responseEntity = bookController.findBookById(bookId);

        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Optional.of(mockBook), responseEntity.getBody());
    }

    @Test
    void testFindBookById_BookNotFound() {
        
        Long bookId = 1L;
        when(bookService.findBookById(bookId)).thenReturn(Optional.empty());

        
        assertThrows(BookException.class, () -> bookController.findBookById(bookId));
    }

    @Test
    void testUpdateBook() throws BookException {
       
        Long bookId = 1L;
        Book mockBook = createMockBook();
        when(bookService.findBookById(bookId)).thenReturn(Optional.of(mockBook));

        
        ResponseEntity<String> responseEntity = (ResponseEntity<String>) bookController.updateBook(bookId, mockBook);

        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("The book has been updated", responseEntity.getBody());
        verify(bookService).updateBook(eq(bookId), any(Book.class));
    }

    @Test
    void testUpdateBook_BookNotFound() {
        // Given
        Long bookId = 1L;
        when(bookService.findBookById(bookId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(BookException.class, () -> bookController.updateBook(bookId, createMockBook()));
    }

    

    @Test
    void testDeleteBook() throws BookException {
        // Given
        Long bookId = 1L;
        when(bookService.findBookById(bookId)).thenReturn(Optional.of(createMockBook()));

        // When
        ResponseEntity<?> responseEntity = bookController.deleteBook(bookId);

        // Then
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        Mockito.verify(bookService).deleteBook(eq(bookId));
    }

    @Test
    void testDeleteBook_BookNotFound() {
        // Given
        Long bookId = 1L;
        when(bookService.findBookById(bookId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(BookException.class, () -> bookController.deleteBook(bookId));
    }

    private Book createMockBook() {
        // Create and return a mock Book instance as needed for tests
        return new Book();
    }
}
