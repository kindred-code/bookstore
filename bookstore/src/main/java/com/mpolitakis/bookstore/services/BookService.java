package com.mpolitakis.bookstore.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpolitakis.bookstore.models.Book;
import com.mpolitakis.bookstore.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<>();
		bookRepository.findAll().forEach(books::add);
		return books;
	}

	public Optional<Book> findBookById(Long id) {
		return bookRepository.findById(id);
	}

	public Optional<Book> findBookByTitle(String title) {
		return bookRepository.findByTitle(title);
	}

	public void addBook(Book book) {
		bookRepository.save(book);
	}

	public void updateBook(Long id, Book book) {
		var bookOld = findBookById(id);
		bookOld.get().setTitle(book.getTitle());
		bookOld.get().setAuthor(book.getAuthor());
		bookOld.get().setISBN(book.getISBN());
		bookOld.get().setPublishedDate(book.getPublishedDate());

		bookRepository.save(bookOld.get());
	}

	public void deleteBook(Long id) {

		bookRepository.deleteById(id);
	}

}