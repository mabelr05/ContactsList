package ec.edu.tecnologicoloja.listapplication;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import ec.edu.tecnologicoloja.listapplication.R;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ec.edu.tecnologicoloja.listapplication.adapter.ListAdapter;
import ec.edu.tecnologicoloja.listapplication.database.Persona;
import ec.edu.tecnologicoloja.listapplication.database.PersonaLab;

public class addContactActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE =1;
    private TextView et_nombre, et_apellido, et_telefono, et_email, et_descripcion;
    private ListAdapter listItemAdapter;
    private Button bguardar, blimpiar;
    private ImageView imgProfilePhoto;
    private NumberPicker pk_ciudad;
    String[] cities = {"Quito", "Guayaquil", "Cuenca", "Manta", "Ambato", "Loja","Machala", "Macara"};
    //Permisos Camara
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;
    private String currentPhotoPath;
    private Bitmap currentBitmap;
    private PersonaLab mPersonaLab;
    private Persona mPersona;
    private ArrayList<Persona> listaNombres = new ArrayList<>();
    private ListView listView;
    private static final int RESULT_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        mPersonaLab = new PersonaLab(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Agregar Contacto");

        imgProfilePhoto =findViewById(R.id.imgUserPhoto);
        et_nombre = (TextView) findViewById(R.id.editTextTextPersonName);
        et_apellido = findViewById(R.id.etPersonApe);
        et_telefono = findViewById(R.id.etPhone);
        et_email = findViewById(R.id.etEmailAddress);
        et_descripcion = findViewById(R.id.etDescripcion);
        bguardar = (Button) findViewById(R.id.buttonGuardar);
        blimpiar = (Button) findViewById(R.id.buttonLimpiar);
        imgProfilePhoto.setOnClickListener(this);
        bguardar.setOnClickListener(this);
        blimpiar.setOnClickListener(this);
        pk_ciudad = findViewById(R.id.pkCiudad);
        pk_ciudad.setMinValue(0);
        pk_ciudad.setMaxValue(cities.length - 1);
        pk_ciudad.setDisplayedValues(cities);
    }

    @Override
    public void onClick(View v) {
        if (v == blimpiar) {
            mPersonaLab.deleteAllPersona();
            listaNombres.clear();
            //listItemAdapter.notifyDataSetChanged();
        }
        if (v == bguardar) {
            insertPersonas();
            //getAllPersonas();
        }
        if (v == imgProfilePhoto){
            checkCameraPermission();
        }
    }
    public void insertPersonas() {
        mPersona = new Persona();
        mPersona.setNombre(et_nombre.getText().toString());
        mPersona.setApellido(et_apellido.getText().toString());
        mPersona.setTelefono(et_telefono.getText().toString());
        mPersona.setEmail(et_email.getText().toString());
        mPersona.setDescripcion(et_descripcion.getText().toString());
        if(currentBitmap==null){

        }else{
            mPersona.setFoto(currentBitmap);
        }
        int selectedCityIndex = pk_ciudad.getValue();
        String selectedCity = cities[selectedCityIndex];
        mPersona.setCiudad(selectedCity);
        mPersonaLab.addPersona(mPersona);

        setResult(RESULT_OK);
        finish();

        et_nombre.setText("");
        et_apellido.setText("");
        et_telefono.setText("");
        et_descripcion.setText("");
        et_email.setText("");

    }
    public void getAllPersonas() {
        listaNombres.clear();
        listaNombres.addAll(mPersonaLab.getPersonas());

    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            dispatchTakePictureIntent();
            Log.d("TAG", "Camera permission");
        }
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("Error", ex.getMessage());
            }
            if (photoFile != null) {
                currentPhotoPath = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(this, "ec.edu.tecnologicoloja.listApplication.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                Log.i("TAG", "xxxxxxxxxxxxxxxxxxxx: "+photoFile);
                View context = null;


                /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // El permiso no se ha otorgado, solicitarlo
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                } else {
                    // El permiso ya se ha otorgado, continuar con la apertura del archivo
                    profilePhoto.setImageURI(photoURI);
                    Log.i("TAG","PhotoURI: "+photoURI);
                }*/

            }
        }

    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_QuickDev";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(storageDir, imageFileName+".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //profilePhoto.setImageURI(currentPhotoPath);
            Log.d("TAG", "URL:  "+currentPhotoPath);
            setPic();
            /*View context = null;
            Glide.with(context).load(currentPhotoPath)
                    .into(imgProfilePhoto);*/
        }
    }
    private void setPic() {
        int targetW = imgProfilePhoto.getWidth();
        int targetH = imgProfilePhoto.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        if (targetW == 0 || targetH == 0 || photoW == 0 || photoH == 0) {
            Log.e("MapsActivity", "Error: Dimensiones de la vista de imagen o de la foto son cero");
            return;
        }

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imgProfilePhoto.setImageBitmap(bitmap);
        currentBitmap = bitmap;
    }
    // Agrega el método para actualizar la lista de contactos en la actividad principal


// Resto del código en addContactActivity


}
