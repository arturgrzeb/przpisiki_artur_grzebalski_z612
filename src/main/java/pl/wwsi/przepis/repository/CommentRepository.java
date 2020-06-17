package pl.wwsi.przepis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wwsi.przepis.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    Optional<List<Comment>> findCommentsByRecipeId(Long recipeId);
    Optional<List<Comment>> findTop5CommentsByRecipeIdOrderByIdDesc(Long recipeId);
}
