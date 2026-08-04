package edu.cnm.deepdive.softspace.ui;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.softspace.R;
import edu.cnm.deepdive.softspace.databinding.ActivityLoginBinding;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding binding;
  private NavController navController;
  private AppBarConfiguration appBarConfig;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  setUpLayout();
  setUpNavigation();
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, appBarConfig) || super.onSupportNavigateUp();
  }

  private void setUpLayout() {
    EdgeToEdge.enable(this);
    binding = ActivityLoginBinding.inflate(getLayoutInflater());
    ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });
    setContentView(binding.getRoot());
  }

  private void setUpNavigation() {
    appBarConfig = new AppBarConfiguration.Builder(R.id.signInFragment).build();
    NavHostFragment host = binding.navHost.getFragment();
    navController = host.getNavController();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
  }

}
