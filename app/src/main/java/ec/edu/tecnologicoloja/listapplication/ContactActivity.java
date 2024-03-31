package ec.edu.tecnologicoloja.listapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import ec.edu.tecnologicoloja.listapplication.database.Persona;
import ec.edu.tecnologicoloja.listapplication.database.PersonaLab;

public class ContactActivity  extends AppCompatActivity {

    private TextView nombre,correo,ciudad,telefono,descripción;
    private ImageView image;

    private Persona conta;
    private Context context;
    private PersonaLab mPersonaLab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mPersonaLab=new PersonaLab(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String id= intent.getStringExtra("id");
        conta=mPersonaLab.getPersona(id);

        nombre=(TextView) findViewById(R.id.textnombre);
        correo=(TextView) findViewById(R.id.textcorreo);
        ciudad=(TextView) findViewById(R.id.textciudad);
        telefono=(TextView) findViewById(R.id.textelefono);
        descripción=(TextView) findViewById(R.id.textDescripcion);
        image = (ImageView) findViewById(R.id.imageView);

        if(conta==null){
            System.out.println("objeto vacio"+id);
        }else {
            nombre.setText(conta.getNombre()+" " +conta.getApellido());
            correo.setText(conta.getEmail());
            ciudad.setText(conta.getCiudad());
            telefono.setText(conta.getTelefono());
            descripción.setText(conta.getDescripcion());
            if(conta.getFoto()==null){
                image.setImageResource(R.drawable.nullimage);
            }else {
                image.setImageBitmap(conta.getFoto());
            }
            getSupportActionBar().setTitle(conta.getNombre()+" " +conta.getApellido());
            getSupportActionBar().setSubtitle(conta.getTelefono());
            Glide.with(this)
                    .load("http://i.imgur.com/DvpvklR.png")
                    .centerCrop()
                    .into(image);

            // Picasso.get().load(conta.getImageUrl()).into(image);
        }


    }
}
