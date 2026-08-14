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
import edu.cnm.deepdive.softspace.model.entity.Post;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;

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
    MutableLiveData<List<Post>> userPosts = new MutableLiveData<>();
    Object userId = null;
    postCollection
        .whereEqualTo("userId", null)
        .addSnapshotListener((result, error) -> {
          if (error == null && result != null) {
            userPosts.setValue(result.toObjects(Post.class));
          } else if (error != null) {
            this.error.setValue(error);
          }
        });

    return userPosts;
  }

  public Task<DocumentReference> create(Post post) {
    Log.i("Post", "create: "+post.toString());
    return postCollection.add(post);
  }

}
