package br.edu.ifspsaocarlos.sdm.agenda;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.agenda.R;
import br.edu.ifspsaocarlos.sdm.agenda.adapter.ContatoArrayAdapter;
import br.edu.ifspsaocarlos.sdm.agenda.data.ContatoDAO;
import br.edu.ifspsaocarlos.sdm.agenda.model.Contato;

public class MainActivity extends AppCompatActivity {

    protected ContatoDAO cDAO;
    public ListView list;
    public ContatoArrayAdapter adapter;
    protected SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cDAO = new ContatoDAO(getContentResolver());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DetalheActivity.class);
                startActivityForResult(i, 0);
            }
        });
        list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2,
                                    long arg3) {
                Contato contact = (Contato) adapterView.getAdapter().getItem(arg2);
                Intent inte = new Intent(getApplicationContext(), DetalheActivity.class);
                inte.putExtra("contact", contact);
                startActivityForResult(inte, 0);
            }
        });

        buildListView();
        registerForContextMenu(list);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        buildListView();
        searchView.clearFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.pesqContato).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

        //Executado quando digitado no texto.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query = query.trim();
                if(query.isEmpty()) {
                    buildListView();
                    return true;
                } else if(query.length() > 3) {
                    buildListViewQueryByNameOrEmail(query);
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return onQueryTextSubmit(newText);
            }
        });

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ContatoArrayAdapter adapter = (ContatoArrayAdapter) list.getAdapter();
        Contato contact = adapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.delete_item:
                cDAO.deleteContact(contact);
                Toast.makeText(getApplicationContext(), "Removido com sucesso", Toast.LENGTH_SHORT).show();
                buildListView();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    protected void buildListView() {
        List<Contato> values = cDAO.buscaTodosContatos();
        adapter = new ContatoArrayAdapter(this, values);
        list.setAdapter(adapter);
    }

    public void buildListViewQueryByNameOrEmail(String value) {
        List<Contato> values = cDAO.buscaContato(value);
        if(value.isEmpty()) {
            values = cDAO.buscaTodosContatos();
        }
        adapter = new ContatoArrayAdapter(this, values);
        list.setAdapter(adapter);
    }

}