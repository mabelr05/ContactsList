package ec.edu.tecnologicoloja.listapplication.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Persona.class},version = 3)
public abstract class PersonaDatabase extends RoomDatabase {

    public abstract PersonaDao getPersonaDao();

}
