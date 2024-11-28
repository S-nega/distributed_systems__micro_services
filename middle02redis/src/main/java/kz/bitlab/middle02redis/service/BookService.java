package kz.bitlab.middle02redis.service;

import kz.bitlab.middle02redis.config.RedisConfig;
import kz.bitlab.middle02redis.models.Book;
import kz.bitlab.middle02redis.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CacheService cacheService;
    private final RedisTemplate redisTemplate;

    public Book addBook(Book book){
        cacheService.cacheObject("book:"+book.getId(), book, 1, TimeUnit.MINUTES);
        cacheService.deleteCachedObject("books");
        return bookRepository.save(book);
    }

    public List<Book> gelAllBooks(){
        // Проверка в кэше
        List<Book> books = (List<Book>) cacheService.getCachedObject("books");
        if (books != null) {
            System.out.println("Cache hit: Returning books from Redis");
            return books;
        }

        // Если кэш пустой, загрузить данные из базы
        books = bookRepository.findAll();
        System.out.println("Cache miss: Fetching books from database");

        // Сохранить данные в кэш
        cacheService.cacheObject("books", books, 1, TimeUnit.MINUTES);
//        redisTemplate.opsForValue().set(BOOKS_CACHE_KEY, books);

        return books;
    }

    public List<Book> getPopularBooks(){
        String popularBooksKey = "popularBooks";

        // Получаем список всех ключей популярных книг
        List<String> bookKeys = redisTemplate.opsForList().range(popularBooksKey, 0, -1);

        // Загружаем книги из кэша по ключам
        List<Book> books = new ArrayList<>();
        if (bookKeys != null) {
            for (String bookKey : bookKeys) {
                Book book = (Book) redisTemplate.opsForValue().get(bookKey);
                if (book != null) {
                    books.add(book);
                }
            }
        }

        return books;
//
//
//        List<Book> books = (List<Book>) cacheService.getCachedObject("popular");
//
////        List<String> popBooks = (List<String>) cacheService.getCachedObject("popular");
////        List<Book> books = null;
////        for (int i = 0; i < popBooks.size(); i++ ){
////            books.add(cacheService.getCachedObject(popBooks.));
////        }
//        System.out.println(books);
//        return books;
    }
//        return bookRepository.findAll();

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

        cacheService.cachePopularObject("popularBooks", cacheKey, 1, TimeUnit.MINUTES);
        return book.orElse(null);
    }

    public Book updateBook(Book updatedBook, Long id) {
        final String cacheKey = "book:" + id;
        final Long is = updatedBook.getId();
        cacheService.deleteCachedObject("books");
        cacheService.deleteCachedObject("popular");
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