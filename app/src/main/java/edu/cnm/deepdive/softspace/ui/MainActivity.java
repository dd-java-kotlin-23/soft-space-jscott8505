package edu.cnm.deepdive.softspace.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.softspace.MainNavGraphDirections;
import edu.cnm.deepdive.softspace.R;
import edu.cnm.deepdive.softspace.databinding.ActivityLoginBinding;
import edu.cnm.deepdive.softspace.databinding.ActivityMainBinding;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
     super.onCreateOptionsMenu(menu);
     getMenuInflater().inflate(R.menu.main_app_options, menu);
     return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    if (item.getItemId() == R.id.settings) {
      navController.navigate(MainNavGraphDirections.openSettings());
    }else if(item.getItemId() == R.id.sign_out) {
      // TODO: 8/4/26 invoke sign out method in auth viewmodel.
    }else {
    handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  private void setUpLayout() {
    EdgeToEdge.enable(this);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });
    setContentView(binding.getRoot());
  }

  private void setUpNavigation() {
    appBarConfig = new AppBarConfiguration.Builder(R.id.feedFragment, R.id.messagesFragment,
        R.id.calendarFragment, R.id.profilePageFragment).build();
    NavHostFragment host = binding.navHost.getFragment();
    navController = host.getNavController();
    NavigationUI.setupWithNavController(binding.bottomNav, navController);
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
  }

  private void setupViewModels() {
    // TODO: 8/4/26 Connect to auth viewmodel and navigate to login activity, if theres no logged in user. 
  }
  
}
