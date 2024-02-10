package com.example.bookservice.services;

import com.example.bookservice.dtos.BookDto;
import com.example.bookservice.entities.Category;
import com.example.bookservice.exceptions.NotBookWithSuchIdException;
import com.example.bookservice.exceptions.NotBookWithSuchTitleOrAuthorException;
import com.example.bookservice.exceptions.NotSuchCategoryException;
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
                .orElseThrow(() -> new NotSuchCategoryException(category));
    }

    @Cacheable(value = "book", key = "#title + #author")
    @Override
    public BookDto getBookByTitleAndAuthor(String title, String author) {
        log.info("Get books by title '{}' and author '{}'", title, author);
        return bookRepository.findByTitleAndAuthor(title, author)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotBookWithSuchTitleOrAuthorException(title, author));
    }

    @Caching(evict = {
            @CacheEvict(value = "books", allEntries = true),
            @CacheEvict(value = "book", allEntries = true)
    })
    @Override
    public BookDto createOrUpdateBook(BookDto book) {
        if (book.getId() == null) {
            log.info("Create new book '{}'", book);
        } else if (bookRepository.existsById(book.getId())) {
            log.info("Update book '{}'", book);
        } else {
            log.info("Book with id '{}' doesn't exists", book.getId());
            throw new NotBookWithSuchIdException(book.getId());
        }
        return categoryRepository.findByTitle(book.getCategory())
                .map(mapper.toEntity(book)::setCategory)
                .map(bookRepository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotSuchCategoryException(book.getCategory()));
    }

    @Caching(evict = {
            @CacheEvict(value = "books", allEntries = true),
            @CacheEvict(value = "book", allEntries = true)
    })
    @Override
    public Integer deleteById(Integer id) {

        log.info("Delete book with id '{}'", id);

        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return id;
        }
        log.info("Book with id '{}' doesn't exists", id);
        throw new NotBookWithSuchIdException(id);
    }
}
