package uk.ac.nott.cs.comp2013.mentorapp.view;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.nott.cs.comp2013.mentorapp.MentorApp;

/**
 * <p>
 * A {@code ViewManager} is responsible for changing the scene currently presented in an
 * application's main stage. It is designed to work with implementations of {@link ManagedView} so
 * that views can be changed without having to pass references to this class around.
 * </p>
 *
 * <p>
 * Views are added to the manager using the {@link #addView(String, Parent)} method. The first
 * argument is a string used to identify the view, and the second argument is the view itself.  Note
 * that the provided view must extend {@link javafx.scene.Parent} or one of its subclasses like
 * {@code VBox}. The provided string can later be used to set the view using
 * {@link #setStageView(String)}. It is best practice to use string constants for these view names,
 * such as the provided {@link #LOGIN} and {@link #DUMMY} constants.
 * </p>
 *
 * <p>
 * An example of registering and setting a view is available in {@link MentorApp#start(Stage)}. You
 * should not normally need to manually change view; instead, refer to {@code ManagedView} and the
 * explanation for triggering view change events. When a new view is added to a {@code ViewManager},
 * that view's view change handler is automatically configured to trigger a view change when a view
 * change event is raised.
 * </p>
 */
public class ViewManager {

  public static final String LOGIN = "login_view";
  public static final String DUMMY = "dummy_view";
  public static final String ACTORS_VIEW = "actors_view";
  public static final String MENTEE_VIEW = "mentee_view";
  public static final String MENTOR_VIEW = "mentor_view";
  public static final String ADMIN_VIEW = "admin_view";

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
