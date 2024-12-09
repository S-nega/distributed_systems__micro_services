package kz.bitlab.middle02redis.service;

import kz.bitlab.middle02redis.models.Book;
import kz.bitlab.middle02redis.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CacheService cacheService;

    public Book addBook(Book book){
        String cacheKey = "book:" +  book.getId();
        cacheService.cacheObject(cacheKey, book, 1, TimeUnit.MINUTES);
        return bookRepository.save(book);
    }

    public List<Book> gelAllBooks(){
//        List<Book> books = (List<Book>) cacheService.getCachedObject("books");
//        if (books != null) {
//            return books;
//        }
        List<Book> books = bookRepository.findAll();
        return books;
    }

    public Set<Object> getPopularBooks(){
        Set<Object> books = cacheService.getAllPopularBooks();
        return books;
    }

    public Book getBook(Long id) {
        final String cacheKey = "book:" + id;
        Book cachedBook = (Book) cacheService.getCachedObject(cacheKey);
        if (cachedBook != null) {
            return cachedBook;
        }
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(
            b -> {
                cacheService.cacheObject(cacheKey, b, 1, TimeUnit.MINUTES);
                cacheService.cachePopularObject(b, 1, TimeUnit.MINUTES);
            }
        );
        return book.orElse(null);
    }

    public Book updateBook(Book updatedBook, Long id) {
        final String cacheKey = "book:" + id;
        cacheService.deleteCachedObject(cacheKey);
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
            cacheService.cachePopularObject(savedBook, 1, TimeUnit.MINUTES);
            return savedBook;
        }
        return null;
    }

    public String deleteBook(Long id) {
        final String cacheKey = "book:" + id;
        cacheService.deleteCachedObject("books");
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            bookRepository.deleteById(id);
            cacheService.deleteCachedObject(cacheKey);
            return "Deleted: " + id;
        }
        return "Error";
    }

}
