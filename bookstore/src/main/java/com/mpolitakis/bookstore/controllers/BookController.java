package com.mpolitakis.bookstore.controllers;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpolitakis.bookstore.exceptions.BookException;
import com.mpolitakis.bookstore.models.Book;
import com.mpolitakis.bookstore.repositories.UserRepository;
import com.mpolitakis.bookstore.services.BookService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = {"${spring.application.cors.origin}"})
@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {

	public static final Logger logger = LoggerFactory.getLogger(BookController.class);

	
	private final BookService bookService;
    private final UserRepository userRepository;

   



    @PreAuthorize("hasAuthority('USER')")
	@GetMapping("")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> list = bookService.getAllBooks();
		return ResponseEntity.ok(list);

	}


    @PreAuthorize("hasAuthority('USER')")
	@PostMapping("")
	public ResponseEntity<Book> addBook(@Validated @RequestBody Book book) throws BookException {

		if(book != null){

		
		Optional<Book> bookByTitle = bookService.findBookByTitle(book.getTitle());
		if (!bookByTitle.isPresent() ) {
			logger.error("Missing book info");
			throw new BookException("Missing book info",
					HttpStatus.CONFLICT);

		}
		
	}
	else{
		throw new BookException("Book is null", HttpStatus.CONFLICT);
	}

		 JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authenticationToken.getCredentials();
        String username = (String) jwt.getClaims().get("Username");

        

        book.setUser(userRepository.findByUsername(username));

		bookService.addBook(book);
		
		return ResponseEntity.ok(book);
	}
   

    @PreAuthorize("hasAuthority('USER')")
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Book>> findBookById(@PathVariable Long id) throws BookException {
		Optional<Book> book = bookService.findBookById(id);
		if (!book.isPresent()) {
			logger.error("Book with id {} not found.", id);
			throw new BookException("Book with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(book);
	}

    @PreAuthorize("hasAuthority('USER')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBook(@PathVariable Long id, @Validated @RequestBody Book book)
			throws BookException {
		
		
		Optional<Book> currentBook = bookService.findBookById(id);
		if (!currentBook.isPresent()) {
			logger.error("Unable to update. Book with id {} not found.", id);
			throw new BookException("Unable to update. Book with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		bookService.updateBook(id, book);

		return ResponseEntity.ok("The book has been updated");
	}
    @PreAuthorize("hasAuthority('USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) throws BookException {
		Optional<Book> currentBook = bookService.findBookById(id);
		if (!currentBook.isPresent()) {
			logger.error("Unable to delete. Book with id {} not found.", id);
			throw new BookException("Unable to delete. Book with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		bookService.deleteBook(id);
		return new ResponseEntity<Book>(HttpStatus.ACCEPTED);
	}
}
