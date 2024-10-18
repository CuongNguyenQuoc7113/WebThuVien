package com.example.CuoiKy.controller;

import com.example.CuoiKy.entity.*;
import com.example.CuoiKy.repository.IBorrowDetailRepository;
import com.example.CuoiKy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private IBorrowDetailRepository borrowDetailRepository;

    @Autowired
    private BoService boService;
    @GetMapping
    public String home(Model model, RedirectAttributes redirectAttributes) {
        if (model.containsAttribute("loginSuccess")) {
            model.addAttribute("loginSuccessMessage", "Bạn đã đăng nhập thành công!");
            redirectAttributes.addFlashAttribute("loginSuccess", true); // Thêm thông báo thành công vào Flash Attribute
        }
        List<Book> books = bookService.getAllBook();
        model.addAttribute("books", books);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Book> mostBorrowedBooks = bookService.getTopBorrowedBooks(6); // Lấy danh sách top 6 sách được mượn nhiều nhất
        model.addAttribute("mostBorrowedBooks", mostBorrowedBooks); // Thêm vào model
        return "home/index";
    }

    @GetMapping("/library")
    public String getAllBook(Model model){
        List<Book> books = bookService.getAllBook();
        model.addAttribute("books", books);
        List<Author> authors = authorService.getAllAuthor();
        model.addAttribute("authors", authors);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        List<Book> mostBorrowedBooks = bookService.getTopBorrowedBooks(6); // Lấy danh sách top 6 sách được mượn nhiều nhất
        model.addAttribute("mostBorrowedBooks", mostBorrowedBooks);
        List<Bo> bos = boService.getAllBo();
        model.addAttribute("bos", bos);
        return "home/library";
    }



    @GetMapping("/detail/{bookId}")
    public String getBookDetail(Model model, @PathVariable Long bookId){
        Book book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        return "home/detail";
    }

    @GetMapping("/cate/{id}")
    public String getBookByCateId(Model model, @PathVariable Long id){
        List<Book> books = bookService.getByCateId(id);
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("cateName", category.getName());
        model.addAttribute("books", books);
        return "home/library";
    }

    @GetMapping("/set/{bookId}")
    public String getBookByeSetId(Model model, @PathVariable Long bookId){
        List<Book> books = bookService.getBySetId(bookService.getSetId(bookId));
        model.addAttribute("books", books);
        return "share/set";
    }

    @GetMapping("/filter")
    public String filterBooks(Model model,
                              @RequestParam(name = "authorId", required = false) Long authorId,
                              @RequestParam(name = "categoryId", required = false) Long categoryId,
                              @RequestParam(name = "boId", required = false) Long boId,
                              @RequestParam(name = "minPage", required = false) Integer minPage,
                              @RequestParam(name = "maxPage", required = false) Integer maxPage) {
        List<Book> filteredBooks = bookService.filterBooks(authorId, categoryId, boId, minPage, maxPage);
        model.addAttribute("books", filteredBooks);

        List<Author> authors = authorService.getAllAuthor();
        model.addAttribute("authors", authors);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        List<Bo> bos = boService.getAllBo();
        model.addAttribute("bos", bos);

        return "home/library";
    }

    @GetMapping("/search")
    public String searchBooks(Model model, @RequestParam("q") String keyword) {
        List<Book> books = bookService.searchBooksByKeyword(keyword);
        model.addAttribute("books", books);
        List<Author> authors = authorService.getAllAuthor();
        model.addAttribute("authors", authors);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        return "home/library";
    }

}
