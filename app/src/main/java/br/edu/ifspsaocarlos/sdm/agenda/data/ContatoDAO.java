package br.edu.ifspsaocarlos.sdm.agenda.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.agenda.model.Contato;
import br.edu.ifspsaocarlos.sdm.agenda.provider.AgendaProviderContract;
import br.edu.ifspsaocarlos.sdm.agenda.provider.SQLiteHelper;

public class ContatoDAO {
    private ContentResolver contentResolver;

    public ContatoDAO(ContentResolver resolver) {
        this.contentResolver = resolver;

    }

    public List<Contato> buscaTodosContatos() {

        Uri agendaURI = AgendaProviderContract.Agenda.CONTENT_URI;

        Cursor cursor =  contentResolver.query(agendaURI, null, null, null, null);

        ArrayList<Contato> contacts = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Contato contato = new Contato();
                contato.setId(cursor.getInt(0));
                contato.setNome(cursor.getString(1));
                contato.setFone(cursor.getString(2));
                contato.setEmail(cursor.getString(3));
                contato.setCelular(cursor.getString(4));
                contato.setAniversario(cursor.getString(5));
                contacts.add(contato);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return contacts;
    }

    public List<Contato> buscaContato(String nameOrEmail) {

        Uri searchUri = AgendaProviderContract.Agenda.CONTENT_URI_QUERY_(nameOrEmail);

        Cursor cursor = contentResolver.query(searchUri, null, null, null, null);

        ArrayList<Contato> contacts = new ArrayList<>();
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Contato contato = new Contato();
                contato.setId(cursor.getInt(0));
                contato.setNome(cursor.getString(1));
                contato.setFone(cursor.getString(2));
                contato.setEmail(cursor.getString(3));
                contato.setCelular(cursor.getString(4));
                contato.setAniversario(cursor.getString(5));
                contacts.add(contato);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return contacts;
    }

    public void updateContact(Contato c) {

        Long id = c.getId();

        ContentValues updateValues = new ContentValues();
        updateValues.put(SQLiteHelper.KEY_NAME, c.getNome());
        updateValues.put(SQLiteHelper.KEY_FONE, c.getFone());
        updateValues.put(SQLiteHelper.KEY_EMAIL, c.getEmail());
        updateValues.put(SQLiteHelper.KEY_CELULAR, c.getCelular());
        updateValues.put(SQLiteHelper.KEY_ANIVERSARIO, c.getAniversario());

        contentResolver.update(AgendaProviderContract.Agenda.CONTENT_URI_BY_(id), updateValues, null, null);
    }


    public void createContact(Contato c) {

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NAME, c.getNome());
        values.put(SQLiteHelper.KEY_FONE, c.getFone());
        values.put(SQLiteHelper.KEY_EMAIL, c.getEmail());
        values.put(SQLiteHelper.KEY_CELULAR, c.getCelular());
        values.put(SQLiteHelper.KEY_ANIVERSARIO, c.getAniversario());

        contentResolver.insert(AgendaProviderContract.Agenda.CONTENT_URI, values);
    }

    public void deleteContact(Contato c) {

        Long id = c.getId();

       contentResolver.delete(AgendaProviderContract.Agenda.CONTENT_URI_BY_(id), null, null);
    }
}