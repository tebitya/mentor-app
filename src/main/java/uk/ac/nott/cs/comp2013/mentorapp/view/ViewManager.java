package uk.ac.nott.cs.comp2013.mentorapp.view;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages the scenes available in the application and controls which scene is current presented in
 * the stage.
 */
public class ViewManager {

  public static final String LOGIN = "login_view";
  public static final String DUMMY = "dummy_view";

  private final Stage stage;
  private final Map<String, Scene> scenes = new HashMap<>();

  /**
   * Construct a new manager.
   *
   * @param stage the stage used to display this manager's scenes
   */
  public ViewManager(Stage stage) {
    this.stage = stage;
  }

  /**
   * Add a new view to the manager.
   *
   * @param key  unique key to identify the scene
   * @param view view to add
   */
  public <T extends Parent & ManagedView> void addView(String key, T view) {
    view.setOnViewChange(e -> setStageView(e.getView()));
    scenes.put(key, new Scene(view));
  }

  /**
   * Set which view should be displayed on the stage. Will do nothing if the given key does not
   * exist.
   *
   * @param key key of scene to display
   */
  public void setStageView(String key) {
    if (!scenes.containsKey(key)) {
      return;
    }

    Scene s = scenes.get(key);
    stage.setScene(s);
  }

}
