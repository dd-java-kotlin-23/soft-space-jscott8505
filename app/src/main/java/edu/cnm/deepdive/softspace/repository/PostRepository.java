package edu.cnm.deepdive.softspace.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import edu.cnm.deepdive.softspace.model.entity.Post;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;

@Singleton
public class PostRepository {

  private final FirebaseFirestore firestore;
  private final CollectionReference postCollection;

  @Inject
  PostRepository() {
    firestore = FirebaseFirestore.getInstance();
    postCollection = firestore.collection("posts");
  }

  public LiveData<List<Post>> allPosts() {
    MutableLiveData<List<Post>> liveData = new MutableLiveData<>();
    // TODO: This will only fetch posts once, when the repository is created,
    //  we probably want a mechanism to refresh the data periodically.
    postCollection.get().addOnSuccessListener( result -> {
      liveData.postValue(result.toObjects(Post.class));
    });
    return liveData;
  }

  public Task<DocumentReference> create(Post post) {
    Log.i("Post", "create: "+post.toString());
    return postCollection.add(post);
  }

}
