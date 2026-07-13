package edu.cnm.deepdive.softspace.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.softspace.viewmodel.UserProfileViewModel;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {

 private UserProfileViewModel viewModel;
 private TextInputEditText bioEditText;
 private Button saveButton;

 @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
  }

}
