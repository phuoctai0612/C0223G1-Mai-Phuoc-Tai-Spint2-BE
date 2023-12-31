package com.example.movie.controller;

import com.example.movie.model.Actor;
import com.example.movie.model.Author;
import com.example.movie.model.Category;
import com.example.movie.model.Movie;
import com.example.movie.repository.ICategoryRepository;
import com.example.movie.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/movies")

public class MovieController {
    @Autowired
    private IMovieService movieService;
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private IAuthorService authorService;
    @Autowired
    private IActorService actorService;

    @GetMapping("/top10news")
    public ResponseEntity<?> getTop10MovieNew() {
        List<Movie> movieList = movieService.getAllByTimePostOrderByTimePostDescLimit10();
        List<Author> authorList = movieList.get(0).getAuthors();

        if (movieList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(movieList, HttpStatus.OK);
        }
    }

    @GetMapping("/top6cartoon/{name}")
    public ResponseEntity<?> getTop6Cartoon(@PathVariable("name") String name) {

        List<Movie> movieList = movieService.findFirst6ByCategoriesContaining(name);
        List<Author> authorList = movieList.get(0).getAuthors();

        if (movieList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(movieList, HttpStatus.OK);
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetailMovie(@PathVariable("id") Long id) {
        Movie movie = movieService.findMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @GetMapping("/sidebar-topview")
    public ResponseEntity<?> getTopViewSidebar() {
        List<Movie> movie = movieService.getListTopView();
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @GetMapping("/sidebar-propose")
    public ResponseEntity<?> getMovie() {
        List<Movie> movie = movieService.getListPropose();
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<?> getListSearch(@PageableDefault(size = 16) Pageable pageable, @RequestParam("page") String page, @RequestParam("typeMovie") String typeMovie, @RequestParam("category") String category,
                                           @RequestParam("nation") String nation, @RequestParam("yearStart") String yearStart) {
        if (!category.equals("")) {
            List<Category> category1 = new ArrayList<>();
            Long cate = Long.valueOf(category);
            category1 = categoryRepository.findAllById(Collections.singleton(cate));
            Page<Movie> moviePage = movieService.getListSearch(pageable, typeMovie, nation, category1, yearStart);
            return new ResponseEntity<>(moviePage, HttpStatus.OK);
        } else {
            List<Category> category1 = new ArrayList<>();
            Page<Movie> moviePage = movieService.getListSearch(pageable, typeMovie, nation, category1, yearStart);
            return new ResponseEntity<>(moviePage, HttpStatus.OK);
        }
    }

    @GetMapping("/search-movie-name")
    public ResponseEntity<?> getListSearchMovieName(@PageableDefault(size = 16) Pageable pageable, @RequestParam("page") String page, @RequestParam("typeMovie") String typeMovie, @RequestParam("category") String category,
                                                    @RequestParam("nation") String nation, @RequestParam("yearStart") String yearStart, @RequestParam("name") String name) {
        if (!category.equals("")) {
            List<Category> category1 = new ArrayList<>();
            Long cate = Long.valueOf(category);
            category1 = categoryRepository.findAllById(Collections.singleton(cate));
            Page<Movie> moviePage = movieService.getListSearchNameMovie(pageable, typeMovie, category1, nation, yearStart, name);
            return new ResponseEntity<>(moviePage, HttpStatus.OK);
        } else {
            List<Category> category1 = new ArrayList<>();
            Page<Movie> moviePage = movieService.getListSearchNameMovie(pageable, typeMovie, category1, nation, yearStart, name);
            return new ResponseEntity<>(moviePage, HttpStatus.OK);
        }
    }

    @GetMapping("/search/author")
    public ResponseEntity<?> getListSearchAuthor(@PageableDefault(size = 16) Pageable pageable, @RequestParam("page") String page, @RequestParam("typeMovie") String typeMovie, @RequestParam("author") Long author, @RequestParam("category") String category,
                                                 @RequestParam("nation") String nation, @RequestParam("yearStart") String yearStart) {

        List<Author> authorList = authorService.getAuthorById(author);
        if (!category.equals("")) {
            List<Category> category1 = new ArrayList<>();
            Long cate = Long.valueOf(category);
            category1 = categoryRepository.findAllById(Collections.singleton(cate));
            Page<Movie> moviePage = movieService.getListSearchAuthor(pageable, typeMovie, authorList, category1, nation, yearStart);
            return new ResponseEntity<>(moviePage, HttpStatus.OK);
        } else {
            List<Category> category1 = new ArrayList<>();
            Page<Movie> moviePage = movieService.getListSearchAuthor(pageable, typeMovie, authorList, category1, nation, yearStart);
            return new ResponseEntity<>(moviePage, HttpStatus.OK);
        }
    }

    @GetMapping("/search/actor")
    public ResponseEntity<?> getListSearchActors(@PageableDefault(size = 16) Pageable
                                                         pageable, @RequestParam("page") String page, @RequestParam("typeMovie") String
                                                         typeMovie, @RequestParam("actor") Long actor, @RequestParam("category") String category,
                                                 @RequestParam("nation") String nation, @RequestParam("yearStart") String yearStart) {

        List<Actor> actorList = actorService.getActorById(actor);
        if (!category.equals("")) {
            List<Category> category1 = new ArrayList<>();
            Long cate = Long.valueOf(category);
            category1 = categoryRepository.findAllById(Collections.singleton(cate));
            Page<Movie> moviePage = movieService.getListSearchActor(pageable, typeMovie, actorList, category1, nation, yearStart);
            return new ResponseEntity<>(moviePage, HttpStatus.OK);
        } else {
            List<Category> category1 = new ArrayList<>();
            Page<Movie> moviePage = movieService.getListSearchActor(pageable, typeMovie, actorList, category1, nation, yearStart);
            return new ResponseEntity<>(moviePage, HttpStatus.OK);
        }
    }

    @GetMapping("/search/category")
    public ResponseEntity<?> getListSearchCategory(@PageableDefault(size = 16) Pageable pageable, @RequestParam("page") String page, @RequestParam("typeMovie") String typeMovie, @RequestParam("category") Long category, @RequestParam("category1") Long category1,
                                                   @RequestParam("nation") String nation, @RequestParam("yearStart") String yearStart) {
        if (category != 0) {
            List<Category> categoryList = new ArrayList<>();
            List<Category> categoryList1 = new ArrayList<>();

            categoryList1.add(categoryRepository.findById(category).get());
//            categoryList1.add(categoryRepository.findById(category1).get());
            Page<Movie> moviePage = movieService.getListSearchCategory(pageable, typeMovie, nation, categoryList, categoryList1, yearStart);
            return new ResponseEntity<>(moviePage, HttpStatus.OK);
        } else {
            List<Category> categoryList = new ArrayList<>();
            List<Category> categoryList1 = new ArrayList<>();
            categoryList1 = categoryRepository.findAllById(Collections.singleton(category1));
            Page<Movie> moviePage = movieService.getListSearchCategory(pageable, typeMovie, nation, categoryList, categoryList1, yearStart);
            return new ResponseEntity<>(moviePage, HttpStatus.OK);
        }
    }

    @PostMapping("/increase/{id}")
    public ResponseEntity<?> increaseViewMovie(@PathVariable("id") Long id) {
        Movie movie = movieService.findMovieById(id);
        movie.setView(movie.getView() + 1);
        movieService.increaseViewMovie(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
