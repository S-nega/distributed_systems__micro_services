package kz.bitlab.middle02redis.service;

import kz.bitlab.middle02redis.models.Book;
import kz.bitlab.middle02redis.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Book> gelAllBooks(){
        return bookRepository.findAll();
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
    }

    public Book updateBook(Book updatedBook, Long id) {
        final String cacheKey = "book:" + id;
        final Long is = updatedBook.getId();

        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {

            Book bookToUpdate = existingBook.get();
            bookToUpdate.setTitle(updatedBook.getTitle());
            bookToUpdate.setAuthor(updatedBook.getAuthor());
            bookToUpdate.setDescription(updatedBook.getDescription());
            bookToUpdate.setGenre(updatedBook.getGenre());
            bookToUpdate.setPrice(updatedBook.getPrice());

            Book savedBook = bookRepository.save(bookToUpdate);

            cacheService.cacheObject(cacheKey, savedBook, 1, TimeUnit.MINUTES);
            return savedBook;
        }
        return null;
    }

    public String deleteBook(Long id) {
        final String cacheKey = "book:" + id;

        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            bookRepository.deleteById(id);
            cacheService.deleteCachedObject(cacheKey);
            return "Deleted: " + id;
        }
        return "Error";
    }

}
