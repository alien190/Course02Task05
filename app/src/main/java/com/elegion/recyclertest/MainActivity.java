package com.elegion.recyclertest;

import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.OnItemClickListener,
                                                            LoaderManager.LoaderCallbacks<String>
{
    private static final int LOADER_ID = 698;
    private static final String KEY_LOADER = "com.elegion.recyclertest.KEY_LOADER";
    private Loader mLoader = null;

    // добавить фрагмент с recyclerView ---
    // добавить адаптер, холдер и генератор заглушечных данных ---
    // добавить обновление данных и состояние ошибки ---
    // добавить загрузку данных с телефонной книги ---
    // добавить обработку нажатий ---
    // добавить декораторы ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecyclerFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onItemClick(String id) {

        Bundle args = new Bundle();

        args.putString(KEY_LOADER, id);

        mLoader = getSupportLoaderManager().restartLoader(LOADER_ID, args, this);
        mLoader.onContentChanged();

    }


    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_ID && args.getString(KEY_LOADER) != null) {
            return new ContactsAsyncTaskLoader(this, args.getString(KEY_LOADER));
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (data != null) {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + data)));
        }
        else {
            Toast.makeText(this, R.string.num_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.mi_stop: {
                if (mLoader != null) {
                    mLoader.cancelLoad();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }



}