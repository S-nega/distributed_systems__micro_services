package kz.bitlab.middle02redis.api;

import kz.bitlab.middle02redis.models.Book;
import kz.bitlab.middle02redis.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.gelAllBooks();
    }

    @GetMapping(value = "/popular")
    public Set<Object> getPopularBooks(){
        return bookService.getPopularBooks();
    }

    @GetMapping(value = "{id}")
    public Book getBook(@PathVariable(name = "id") Long id){
        return bookService.getBook(id);
    }

    @PutMapping(value = "{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable(name = "id") Long id){
        return bookService.updateBook(book, id);
    }

    @DeleteMapping(value = "{id}")
    public String deleteBook(@PathVariable(name = "id") Long id){
        return bookService.deleteBook(id);
    }

}
