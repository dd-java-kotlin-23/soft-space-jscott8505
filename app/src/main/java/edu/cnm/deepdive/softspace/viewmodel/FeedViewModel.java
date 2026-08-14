package edu.cnm.deepdive.softspace.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.softspace.model.entity.Post;
import edu.cnm.deepdive.softspace.repository.PostRepository;
import edu.cnm.deepdive.softspace.service.AuthService;
import jakarta.inject.Inject;
import java.util.List;

@HiltViewModel
public class FeedViewModel extends ViewModel {

  private final PostRepository postRepository;
  private final AuthService authService;

  @Inject
  public FeedViewModel(PostRepository postRepository, AuthService authService) {
    this.postRepository = postRepository;
    this.authService = authService;
  }

  public LiveData<List<Post>> allPosts() {
    return postRepository.allPosts();
  }

  public Task<DocumentReference> create(Post post) {
    var author = authService.getUser().getValue();
    return author == null
        ? Tasks.forException(new IllegalStateException("Sign in before creating a post."))
        : postRepository.create(post, author);
  }

}
