package kz.bitlab.middle02redis.api;

import kz.bitlab.middle02redis.models.Book;
import kz.bitlab.middle02redis.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @GetMapping(value = "{id}")
    public Book getBook(@PathVariable(name = "id") Long id){
        return bookService.getBook(id);
    }
}
