package com.example.bookservice.services;

import com.example.bookservice.dtos.BookDto;
import com.example.bookservice.entities.Book;
import com.example.bookservice.entities.Category;
import com.example.bookservice.exceptions.BookWithSuchIdNotExistsException;
import com.example.bookservice.exceptions.BookWithSuchTitleOrAuthorNotExistsException;
import com.example.bookservice.exceptions.SuchCategoryNotExistsException;
import com.example.bookservice.mappers.BookMapper;
import com.example.bookservice.repositories.BookRepository;
import com.example.bookservice.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@CacheConfig(cacheManager = "redisCacheManager")
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper mapper;

    @Cacheable(value = "books", key = "#category")
    @Override
    public List<BookDto> getBooksByCategory(String category) {
        log.info("Get books by category '{}'", category);
        return categoryRepository.findByTitle(category)
                .map(Category::getBooks)
                .map(mapper::toDtoList)
                .orElseThrow(() -> new SuchCategoryNotExistsException(category));
    }

    @Cacheable(value = "book", key = "#title + #author")
    @Override
    public BookDto getBookByTitleAndAuthor(String title, String author) {
        log.info("Get books by title '{}' and author '{}'", title, author);
        return bookRepository.findByTitleAndAuthor(title, author)
                .map(mapper::toDto)
                .orElseThrow(() -> new BookWithSuchTitleOrAuthorNotExistsException(title, author));
    }

    @Caching(evict = {
            @CacheEvict(value = "books", key = "#book.category"),
    })
    @Override
    public BookDto createBook(BookDto book) {
        if (book.getId() == null) {
            log.info("Create new book '{}'", book);
        } else if (bookRepository.existsById(book.getId())) {
            log.info("Update book '{}'", book);
        } else {
            log.info("Book with id '{}' doesn't exists", book.getId());
            throw new BookWithSuchIdNotExistsException(book.getId());
        }
        return categoryRepository.findByTitle(book.getCategory())
                .map(mapper.toEntity(book)::setCategory)
                .map(bookRepository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new SuchCategoryNotExistsException(book.getCategory()));
    }

    @Caching(evict = {
            @CacheEvict(value = "books", key = "#book.category"),
            @CacheEvict(value = "book", key = "#book.title + #book.author")
    })
    @Override
    public BookDto updateBook(BookDto book) {
        if (bookRepository.existsById(book.getId())) {
            log.info("Update book '{}'", book);
        } else {
            log.info("Book with id '{}' doesn't exists", book.getId());
            throw new BookWithSuchIdNotExistsException(book.getId());
        }
        return categoryRepository.findByTitle(book.getCategory())
                .map(mapper.toEntity(book)::setCategory)
                .map(bookRepository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new SuchCategoryNotExistsException(book.getCategory()));
    }

    @Caching(evict = {
            @CacheEvict(value = "books", key = "#book.category.title"),
            @CacheEvict(value = "book", key = "#book.title + #book.author")
    })
    @Override
    public Integer deleteBook(Book book) {
        log.info("Delete book with id '{}'", book.getId());
        bookRepository.delete(book);
        return book.getId();
    }

    @Override
    public Book getBookById(Integer id) {
        return bookRepository.findById(id).orElseGet(() -> {
            log.info("Book with id '{}' doesn't exists", id);
            throw new BookWithSuchIdNotExistsException(id);
        });
    }
}