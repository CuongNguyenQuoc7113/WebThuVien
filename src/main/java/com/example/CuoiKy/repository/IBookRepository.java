package com.example.CuoiKy.repository;

import com.example.CuoiKy.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query("SELECT b FROM Book b WHERE " +
            "(:authorId IS NULL OR b.author.id = :authorId) AND " +
            "(:categoryId IS NULL OR b.category.id = :categoryId) AND " +
            "(:minPage IS NULL OR b.page >= :minPage) AND " +
            "(:maxPage IS NULL OR b.page <= :maxPage)")
    List<Book> findByAuthorAndCategoryAndPageRange(
            @Param("authorId") Long authorId,
            @Param("categoryId") Long categoryId,
            @Param("minPage") Integer minPage,
            @Param("maxPage") Integer maxPage);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> searchBook(@Param("query") String query);

    @Query("SELECT b FROM Book b WHERE b.category.id  = ?1")
    List<Book> getByCateId(Long cateId);

    @Query("SELECT b.bo.id FROM Book b WHERE b.id  = ?1")
    Long getSetId(Long id);

    @Query("SELECT b FROM Book b WHERE b.bo.id  = ?1")
    List<Book> getBySetId(Long boId);

    List<Book> findByAuthorIdAndCategoryIdAndPageBetween(Long authorId, Long categoryId, Integer minPage, Integer maxPage);
    List<Book> findByTitleContainingIgnoreCase(String keyword);

    @Query(value = "SELECT bd.book FROM BorrowDetail bd GROUP BY bd.book ORDER BY COUNT(bd.book) DESC")
    List<Book> findTopBorrowedBooksLimit(int limit);

}
