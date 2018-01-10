package info.dagrut.www.a03_events;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by dagrut on 10/01/18.
 */

public class ExternalClickHdl implements View.OnClickListener {
    final Context mCtx;

    public ExternalClickHdl(Context ctx) {
        mCtx = ctx;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(mCtx, "Méthode 4", Toast.LENGTH_LONG).show();
    }
}
