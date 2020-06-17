package pl.wwsi.przepis;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.wwsi.przepis.model.Comment;
import pl.wwsi.przepis.model.Recipe;
import pl.wwsi.przepis.repository.CommentRepository;
import pl.wwsi.przepis.repository.RecipeRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class PrzepisApplication {

	private final RecipeRepository recipeRepository;
	private final CommentRepository commentRepository;

	@PostConstruct
	public void  initApplication(){
		List<Recipe> recipes = new ArrayList<>();
		recipes.add(Recipe.builder().title("Jajka w sosie własnym").ingredients("jajka, woda, keczup").cookTime("10 minut").description("Przepis na budżetowe jajka. Wkładamy do gara na 5 minut, gotujemy, obieramy, keczupujemy i jemy").owner("Pan kucharz").build());
		recipes.add(Recipe.builder().title("Schabowy z frytkami").ingredients("ziemniaki, schab, pieprz, sól, jajka").cookTime("50 minut").description("Ubijamy kotleta. Wsadzamy na patelnie. Obieramy ziemniaki, kroimy na fryteczki i do frytkownicy.").owner("Użytkonik iksiński").build());
		recipes.add(Recipe.builder().title("Kanapka z wędliną").ingredients("chleb, wędlina, masł").cookTime("25 minut").description("Kroimy chleb. Jeśli mamy krojony to smarujemy masłem i pokrywamy wędliną.").owner("Kucharz ze wschodu").build());
		recipes.add(Recipe.builder().title("Misz Masz").ingredients("jajka, cebula, sól, pieprz").cookTime("30 minut").description("Rozgrzewamy patelnie i wrzucamy jajka. Dodajemy wszystko co mamy w lodówce.").owner("Tester jedzenia").build());

		List<Comment> comments = new ArrayList<>();
		comments.add(Comment.builder().userName("user").commentText("testuje sobie komentarze nana 1").recipeId(1L).build());
		comments.add(Comment.builder().userName("user").commentText("testuje sobie komentarze nana 2").recipeId(2L).build());
		comments.add(Comment.builder().userName("user").commentText("testuje sobie komentarze nana 3").recipeId(3L).build());
		comments.add(Comment.builder().userName("user").commentText("testuje sobie komentarze nana 4").recipeId(4L).build());

		recipeRepository.saveAll(recipes);
		commentRepository.saveAll(comments);
	}

	public static void main(String[] args) {
		SpringApplication.run(PrzepisApplication.class, args);
	}

}
