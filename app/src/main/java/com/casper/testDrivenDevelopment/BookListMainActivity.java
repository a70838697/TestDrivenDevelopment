package com.casper.testDrivenDevelopment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int CONTEXT_MENU_NEW = CONTEXT_MENU_DELETE + 1;
    public static final int CONTEXT_MENU_NEW_EDIT = CONTEXT_MENU_NEW + 1;
    public static final int CONTEXT_MENU_UPDATE = CONTEXT_MENU_NEW_EDIT + 1;
    public static final int CONTEXT_MENU_ABOUT = CONTEXT_MENU_UPDATE + 1;
    private List<Book> listBooks = new ArrayList<>();
    ListView listViewBooks;
    BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);

        init();
        listViewBooks = this.findViewById(R.id.list_view_books);

        bookAdapter = new BookAdapter(BookListMainActivity.this, R.layout.list_view_item_book, getListBooks());
        listViewBooks.setAdapter(bookAdapter);

        this.registerForContextMenu(listViewBooks);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == listViewBooks) {
            //获取适配器
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //设置标题
            menu.setHeaderTitle(listBooks.get(info.position).getTitle());
            //设置内容 参数1为分组，参数2对应条目的id，参数3是指排列顺序，默认排列即可
            menu.add(0, CONTEXT_MENU_DELETE, 0, "删除");
            menu.add(0, CONTEXT_MENU_NEW, 0, "添加");
            menu.add(0, CONTEXT_MENU_UPDATE, 0, "编辑");
            menu.add(0, CONTEXT_MENU_NEW_EDIT, 0, "新编");
            menu.add(0, CONTEXT_MENU_ABOUT, 0, "关于...");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 901:
                if (resultCode == RESULT_OK) {
                    String title = data.getStringExtra("book_name");
                    int insertPosition = data.getIntExtra("position", 0);
                    getListBooks().add(insertPosition, new Book(title, R.drawable.book_no_name));
                    bookAdapter.notifyDataSetChanged();
                }
                break;
            case 902:
                if (resultCode == RESULT_OK) {
                    int insertPosition = data.getIntExtra("position", 0);
                    Book bookAtPosition = getListBooks().get(insertPosition);
                    bookAtPosition.setTitle(data.getStringExtra("book_name"));
                    bookAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case CONTEXT_MENU_DELETE:
                final int removePosition = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                Dialog dialog = new AlertDialog.Builder(this)
                        .setTitle("询问")
                        .setMessage("您确定要删除这条吗？")
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                listBooks.remove(removePosition);
                                bookAdapter.notifyDataSetChanged();
                                Toast.makeText(BookListMainActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
                dialog.show();
                break;
            case CONTEXT_MENU_NEW:
                final int insertPosition = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                getListBooks().add(insertPosition, new Book("无名书籍", R.drawable.book_no_name));
                bookAdapter.notifyDataSetChanged();
                break;
            case CONTEXT_MENU_NEW_EDIT:
                Intent intent=new Intent(BookListMainActivity.this, EditBookActivity.class);
                intent.putExtra("position",((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
                BookListMainActivity.this.startActivityForResult(intent,901);
                break;
            case CONTEXT_MENU_UPDATE:
                int updatePosition = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                Intent intent2=new Intent(BookListMainActivity.this, EditBookActivity.class);
                intent2.putExtra("position",updatePosition);
                intent2.putExtra("book_name",getListBooks().get(updatePosition).getTitle());
                BookListMainActivity.this.startActivityForResult(intent2,902);
                break;
            case CONTEXT_MENU_ABOUT:
                Toast.makeText(BookListMainActivity.this, "图书列表v1.0,coded by casper", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void init() {
        getListBooks().add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        getListBooks().add(new Book("创新工程实践", R.drawable.book_no_name));
        getListBooks().add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
    }

    public List<Book> getListBooks() {
        return listBooks;
    }

    class BookAdapter extends ArrayAdapter<Book> {

        private int resourceId;

        BookAdapter(Context context, int resource, List<Book> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Book book = getItem(position);//获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((ImageView) view.findViewById(R.id.image_view_book_cover)).setImageResource(book.getCoverResourceId());
            ((TextView) view.findViewById(R.id.text_view_book_title)).setText(book.getTitle());
            return view;
        }
    }
}
