package edu.cnm.deepdive.softspace.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import edu.cnm.deepdive.softspace.model.AuthenticatedUser;
import edu.cnm.deepdive.softspace.model.entity.Post;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class PostRepository {

  private final FirebaseFirestore firestore;
  private final CollectionReference postCollection;
  private final MutableLiveData<List<Post>> posts;
  private final MutableLiveData<FirebaseFirestoreException> error;

  @Inject
  PostRepository() {
    firestore = FirebaseFirestore.getInstance();
    postCollection = firestore.collection("posts");
    posts = new MutableLiveData<>();
    error = new MutableLiveData<>();
    postCollection.addSnapshotListener((QuerySnapshot result, FirebaseFirestoreException error) -> {
      if (error == null) {
        //noinspection DataFlowIssue
        posts.postValue(result.toObjects(Post.class));
      } else {
        this.error.postValue(error);
      }
    });
  }

  public LiveData<List<Post>> allPosts() {
    return posts;
  }

  /** Returns a live list of posts written by one Firebase-authenticated user. */
  public LiveData<List<Post>> postsByAuthor(String authorId) {
    MutableLiveData<List<Post>> authorPosts = new MutableLiveData<>();
    postCollection
        .whereEqualTo("authorId", authorId)
        .addSnapshotListener((result, error) -> {
          if (error == null && result != null) {
            authorPosts.setValue(result.toObjects(Post.class));
          } else if (error != null) {
            this.error.postValue(error);
          }
        });
    return authorPosts;
  }

  /** Creates a post with the currently authenticated user's immutable author identity. */
  public Task<DocumentReference> create(Post post, AuthenticatedUser author) {
    Log.i("Post", "create: "+post.toString());
    Map<String, Object> document = new HashMap<>();
    document.put("userProfileId", post.getUserProfileId());
    document.put("authorId", author.getId());
    document.put("authorName", author.getDisplayName() == null ? "" : author.getDisplayName());
    document.put("content", post.getContent());
    document.put("imageUrl", post.getImageUrl());
    document.put("commentCount", post.getCommentCount());
    document.put("createdDate", post.getCreatedDate());
    return postCollection.add(document);
  }

}
