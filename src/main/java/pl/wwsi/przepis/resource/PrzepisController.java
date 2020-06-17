package pl.wwsi.przepis.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wwsi.przepis.model.Comment;
import pl.wwsi.przepis.model.Recipe;
import pl.wwsi.przepis.repository.CommentRepository;
import pl.wwsi.przepis.repository.RecipeRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@Controller
@RequiredArgsConstructor
public class PrzepisController {

    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/")
    public String root(Model model) {
        return "index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/recipe-list")
    public String recipeList(Model model) {

        Iterable<Recipe> recipes = recipeRepository.findAll();

        model.addAttribute("recipes", recipes);

        return "recipe-list";
    }

    @GetMapping("/new-recipe")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "new-recipe";
    }

    @PostMapping(value = "/save-new-recipe", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String saveNewRecipe(Recipe recipe, Principal principal) {
        recipe.setOwner(principal.getName());
        recipeRepository.save(recipe);

        return "redirect:/recipe-list";
    }

    @GetMapping("/new-comment")
    public String newComment(@RequestParam("recipeId") Long recipeId, Model model) {
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("comment", new Comment());
        return "new-comment";
    }

    @PostMapping(value = "/save-new-comment", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String saveNewComment(@RequestParam("recipeId") Long recipeId, Comment comment, Principal principal) {
        comment.setUserName(principal.getName());
        comment.setRecipeId(recipeId);

        commentRepository.save(comment);

        return "redirect:/recipe-details/" + recipeId;
    }

    @GetMapping("/recipe-details/{id}")
    public String recipeDetails(@PathVariable("id") Long id, @RequestParam(defaultValue = "false") Boolean editMode, Model model) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        Optional<List<Comment>> comments = commentRepository.findCommentsByRecipeId(id);
        Optional<List<Comment>> lastComments = commentRepository.findTop5CommentsByRecipeIdOrderByIdDesc(id);
        System.out.println("lastComments" + lastComments);

        model.addAttribute("recipe", recipe.orElseThrow(NoSuchElementException::new));
        model.addAttribute("comments", lastComments.orElse(new ArrayList<>()));

        model.addAttribute("recipeId", id);
        model.addAttribute("editMode", editMode);

        return "recipe-details";
    }
}
