package net.za.dyndns.gerd.deutschlandfunk.favoriten;

import android.app.DialogFragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hanno on 24.08.14.
 */
public class BenutzerInfo extends DialogFragment {
  private Context context;
  private String suchbegriff;
  private int debug;

  public BenutzerInfo(Context context, int debug, String suchbegriff) {
    this.suchbegriff = suchbegriff;
    this.context = context;
    this.debug = debug;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                           Bundle savedInstanceState) {
    //final Context context = this.context;
    View rootView = inflater.inflate(R.layout.anzeigefragment, container,
        false);
    getDialog().setTitle("Benutzer-Info");

    ConnectivityManager connMgr =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    String verbindung = "Keine Verbindung";
    if (networkInfo != null) {
      if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
        verbindung = "Mit WIFI verbunden";
      } else {
        verbindung = "Keine WIFI-Verbindung";
      }
      verbindung += " - " + networkInfo.getTypeName();
      //verbindung += " - " + networkInfo.getSubtypeName();
      verbindung += " - " + networkInfo.getExtraInfo();
      //verbindung += "\n" + networkInfo;
    }
    // Fülle "textView" mit Informationen
    final TextView textView;
    if (rootView != null) {
      textView = (TextView) rootView.findViewById(R.id.anzeigefragment_textview);
      if (textView != null) {
        textView.setText("");
        textView.append("");
        textView.append("Verbindung: " + verbindung + "\n");
        textView.append("Debug-Niveau " + debug + "\n");
        textView.append("suchbegriff=\"" + suchbegriff + "\"\n");
        textView.append("Verzeichnis=\"" + context.getFilesDir().toString() + "\"\n");
        textView.append("Dateien darin:\n");
        for (String dateiname : context.fileList()) {
          textView.append(dateiname + "\n");
        }
        // nun ist "textView" mit Informationen gefüllt

        textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Toast.makeText(context, context.getString(R.string.berühren), Toast.LENGTH_SHORT).show();
          }
        });
      }

      Button speichern = (Button) rootView.findViewById(R.id.speichern);
      speichern.setText(R.string.speichern);
      speichern.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Toast.makeText(context, R.string.speichern, Toast.LENGTH_SHORT).show();
          if (((WahlActivity) getActivity()) != null) {
            ((WahlActivity) getActivity()).doPositiverKlick();
          }
        }
      });

      Button löschen = (Button) rootView.findViewById(R.id.löschen);
      löschen.setText(R.string.löschen);
      löschen.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Toast.makeText(context, R.string.löschen, Toast.LENGTH_SHORT).show();
          if (((WahlActivity) getActivity()) != null) {
            ((WahlActivity) getActivity()).doNegativerKlick();
          }
        }
      });
    }
    return rootView;
  }

}
/*
WahlActivity.java definiert:  public void doNegativerKlick
WahlActivity.java definiert:  public void doPositiverKlick
 */