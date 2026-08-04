package edu.cnm.deepdive.softspace.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.softspace.model.entity.Post;
import edu.cnm.deepdive.softspace.repository.PostRepository;
import jakarta.inject.Inject;
import java.util.List;

@HiltViewModel
public class FeedViewModel extends ViewModel {

  private final PostRepository postRepository;

  @Inject
  public FeedViewModel(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public LiveData<List<Post>> allPosts() {
    return postRepository.allPosts();
  }

  public Task<DocumentReference> create(Post post) {
    return postRepository.create(post);
  }

}
