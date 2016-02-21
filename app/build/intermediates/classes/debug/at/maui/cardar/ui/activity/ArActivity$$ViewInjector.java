// Generated code from Butter Knife. Do not modify!
package at.maui.cardar.ui.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ArActivity$$ViewInjector {
  public static void inject(Finder finder, final at.maui.cardar.ui.activity.ArActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099652, "field 'overlayView'");
    target.overlayView = (at.maui.cardar.ui.widget.CardboardOverlayView) view;
    view = finder.findRequiredView(source, 2131099651, "field 'cardboardView'");
    target.cardboardView = (com.google.vrtoolkit.cardboard.CardboardView) view;
  }

  public static void reset(at.maui.cardar.ui.activity.ArActivity target) {
    target.overlayView = null;
    target.cardboardView = null;
  }
}
