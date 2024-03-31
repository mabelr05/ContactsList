package ec.edu.tecnologicoloja.listapplication;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ec.edu.tecnologicoloja.listapplication.adapter.ListAdapter;
import ec.edu.tecnologicoloja.listapplication.database.Persona;
import ec.edu.tecnologicoloja.listapplication.database.PersonaLab;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListAdapter listItemAdapter;
    private ArrayList<Persona> listaNombres = new ArrayList<>();
    private ListView listView;
    private PersonaLab mPersonaLab;
    private Persona mPersona;

    private Button baddContact;
    private ImageView profilePhoto;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPersonaLab = new PersonaLab(this);


        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        baddContact= findViewById(R.id.btn_addContact);
        getSupportActionBar().hide();
        baddContact.setOnClickListener(this);


        getAllPersonas();
        listItemAdapter = new ListAdapter(this, listaNombres);
        listView.setAdapter(listItemAdapter);


    }

    /**
     * GUARDA EN LA BASE DE DATOS
     */


    // CONSULTA A LA BASE DE DATOS
    public void getAllPersonas() {
        listaNombres.clear();
        listaNombres.addAll(mPersonaLab.getPersonas());

        Log.i(TAG, "getAllPersonas");

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ContactActivity.class);
        String idd = "" + listaNombres.get(position).getId();
        intent.putExtra("id", idd);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        if (v == baddContact){
            Intent intent = new Intent(MainActivity.this, addContactActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    // Agrega el método para obtener y actualizar la lista de contactos en MainActivity
    private void updateContactList() {
        getAllPersonas();
        listItemAdapter.notifyDataSetChanged();
    }

    // Resto de tu código

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: requestCode = " + requestCode + ", resultCode = " + resultCode);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Actualizar la lista de contactos después de agregar uno nuevo
            getAllPersonas();
            listItemAdapter.notifyDataSetChanged();
        } else {
            Log.d(TAG, "onActivityResult: Unexpected requestCode or resultCode");
        }
    }

// Resto de tu código




}