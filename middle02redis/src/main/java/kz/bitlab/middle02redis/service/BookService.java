package kz.bitlab.middle02redis.service;

import kz.bitlab.middle02redis.models.Book;
import kz.bitlab.middle02redis.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CacheService cacheService;

    public Book addBook(Book book){
        return bookRepository.save(book);
    }

    public Book getBook(Long id) {
        final String cacheKey = "book:" + id;
        Book cachedBook = (Book) cacheService.getCachedObject(cacheKey);
        if (cachedBook != null) {
            return cachedBook;
        }
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(
                b -> cacheService.cacheObject(cacheKey, b, 1, TimeUnit.MINUTES)
        );

        return book.orElse(null);
//        return bookRepository.findById(id).orElse(null);
    }
}
