package com.driver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    public BookController(List<Book> bookList, int id) {
        this.bookList = bookList;
        this.id = id;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        // Your code goes here.
        book.setId(id);
        id++;
        bookList.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") String id) {
        int newId = Integer.parseInt(id);
        for(Book book : bookList) {
            if(book.getId()==newId)
                return new ResponseEntity(book, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(null, HttpStatus.ACCEPTED);
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") String id) {
        int newId = Integer.parseInt(id);
        for(Book book : bookList)
            if(book.getId()==newId) {
                bookList.remove(book);
                break;
            }

        return new ResponseEntity("Deleted", HttpStatus.ACCEPTED);
    }

    // get request /get-all-books
    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity(bookList, HttpStatus.ACCEPTED);
    }

    // delete request /delete-all-books
    @DeleteMapping("/delete-all-books")
    public ResponseEntity<String>deleteAllBooks() {
        bookList.clear();
        return new ResponseEntity("Deleted SuccessFully", HttpStatus.ACCEPTED);
    }

    // get request /get-books-by-author
    // pass author name as request param
    @GetMapping("/get-books-by-author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("author") String author) {
        List<Book> listBook = new ArrayList<>();
        for(Book book : bookList) {
            if(book.getAuthor().equals(author))
                listBook.add(book);
        }
        return new ResponseEntity(listBook, HttpStatus.ACCEPTED);
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    @GetMapping("/get-books-by-genre")
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam("genre") String genre) {
        List<Book> listGenre = new ArrayList<>();
        for(Book book : bookList) {
            if(book.getGenre().equals(genre))
                listGenre.add(book);
        }
        return new ResponseEntity(listGenre, HttpStatus.ACCEPTED);
    }
}
