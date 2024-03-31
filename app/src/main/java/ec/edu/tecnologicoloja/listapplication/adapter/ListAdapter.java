package ec.edu.tecnologicoloja.listapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ec.edu.tecnologicoloja.listapplication.R;
import ec.edu.tecnologicoloja.listapplication.database.Persona;

public class ListAdapter extends ArrayAdapter<Persona> {

    private Context context;
    private ArrayList<Persona> list;


    public ListAdapter(Context context, ArrayList<Persona> list) {
        super(context, R.layout.content_list);
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View view;

        final ViewHolder viewHolder;

        if (convertView == null || convertView.getTag() == null) {

            viewHolder=new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.content_list,parent,false);
            viewHolder.vItemName=(TextView) view.findViewById(R.id.textList);
            viewHolder.vItemApe=(TextView) view.findViewById(R.id.textape);
            viewHolder.vItemImage=(ImageView) view.findViewById(R.id.img) ;
            viewHolder.vItemPhone=(TextView) view.findViewById(R.id.textphone);
            view.setTag(viewHolder);
        }else{

            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

            viewHolder.vItemName.setText(list.get(position).getNombre());
            viewHolder.vItemApe.setText(list.get(position).getApellido());
            viewHolder.vItemPhone.setText(list.get(position).getTelefono());
            if(list.get(position).getFoto() == null){
                viewHolder.vItemImage.setImageResource(R.drawable.nullimage);
            }else{
                viewHolder.vItemImage.setImageBitmap(list.get(position).getFoto());
            }

          //Glide.with(context).load("http://i.imgur.com/DvpvklR.png")
                  //.into(viewHolder.vItemImage);
            // Picasso.get().load("http://i.imgur.com/DvpvklR.png").resize(50, 50)
              //  .centerCrop().into(viewHolder.vItemImage);
        return view;
    }

    static class ViewHolder{
        protected TextView vItemName;
        protected TextView vItemApe;
        protected ImageView vItemImage;
        protected TextView vItemPhone;
    }


}
