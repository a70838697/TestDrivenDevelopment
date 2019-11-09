package com.casper.testDrivenDevelopment.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

import com.casper.testDrivenDevelopment.BookListMainActivity;
import com.casper.testDrivenDevelopment.EditBookActivity;
import com.casper.testDrivenDevelopment.R;
import com.casper.testDrivenDevelopment.data.Book;
import com.casper.testDrivenDevelopment.data.BookSaver;
import com.casper.testDrivenDevelopment.data.ShopLoader;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONObject;

import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    public static final int REQUEST_CODE_SCAN = 333;
    public static final int CONTEXT_MENU_DELETE = 1;
    public static final int CONTEXT_MENU_NEW = CONTEXT_MENU_DELETE + 1;
    public static final int CONTEXT_MENU_NEW_EDIT = CONTEXT_MENU_NEW + 1;
    public static final int CONTEXT_MENU_UPDATE = CONTEXT_MENU_NEW_EDIT + 1;
    public static final int CONTEXT_MENU_ABOUT = CONTEXT_MENU_UPDATE + 1;
    BookAdapter bookAdapter;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v ==this.getView().findViewById(R.id.list_view_books)) {
            //获取适配器
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //设置标题
            menu.setHeaderTitle(getListBooks().get(info.position).getTitle());
            //设置内容 参数1为分组，参数2对应条目的id，参数3是指排列顺序，默认排列即可
            menu.add(0, CONTEXT_MENU_DELETE, 0, "删除");
            menu.add(0, CONTEXT_MENU_NEW, 0, "添加");
            menu.add(0, CONTEXT_MENU_UPDATE, 0, "编辑");
            menu.add(0, CONTEXT_MENU_NEW_EDIT, 0, "新编");
            menu.add(0, CONTEXT_MENU_ABOUT, 0, "关于...");
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
            // 扫描二维码/条码回传
            case REQUEST_CODE_SCAN: {
                if (resultCode == RESULT_OK&&data != null) {
                    final String content = data.getStringExtra(Constant.CODED_CONTENT);
                    Log.v("test", "扫描结果为：" + content);
                    /*判定是图书的isbn*/
                    if (content.startsWith("978") && content.length() == 13) {
                        final Handler handler = new Handler() {
                            @Override
                            public void handleMessage(@NonNull Message msg) {
                                super.handleMessage(msg);
                                bookAdapter.notifyDataSetChanged();
                            }
                        };
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ShopLoader shopLoader = new ShopLoader();
                                String jsonText = shopLoader.download("https://douban.uieee.com/v2/book/isbn/" + content);
                                try {
                                    //这里的text就是上边获取到的数据，一个String.
                                    JSONObject jsonObject = new JSONObject(jsonText);
                                    String title = jsonObject.getString("title");
                                    double price = jsonObject.getDouble("price");
                                    Book book=new Book(title,R.drawable.book_no_name);
                                    book.setPrice(price);
                                    bookAdapter.add(book);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                handler.sendEmptyMessage(1);
                            }
                        }).start();
                    }
                }
            }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case CONTEXT_MENU_DELETE:
                final int removePosition = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                Dialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle("询问")
                        .setMessage("您确定要删除这条吗？")
                        .setIcon(R.drawable.ic_launcher_foreground)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                getListBooks().remove(removePosition);
                                bookAdapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_LONG).show();
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
                Intent intent=new Intent(getActivity(), EditBookActivity.class);
                intent.putExtra("position",((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
                this.startActivityForResult(intent,901);
                break;
            case CONTEXT_MENU_UPDATE:
                int updatePosition = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                Intent intent2=new Intent(getActivity(), EditBookActivity.class);
                intent2.putExtra("position",updatePosition);
                intent2.putExtra("book_name",getListBooks().get(updatePosition).getTitle());
                this.startActivityForResult(intent2,902);
                break;
            case CONTEXT_MENU_ABOUT:
                Toast.makeText(getActivity(), "图书列表v1.0,coded by casper", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        init();
        bookAdapter = new BookAdapter(getContext(), R.layout.list_view_item_book, getListBooks());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listViewBooks = view.findViewById(R.id.list_view_books);
        listViewBooks.setAdapter(bookAdapter);

        this.registerForContextMenu(listViewBooks);
        view.findViewById(R.id.floating_action_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        bookSaver.save();
    }

    BookSaver bookSaver;
    private void init() {
        bookSaver=new BookSaver(getContext());
        bookSaver.load();
        if(getListBooks().size()==0) {
            getListBooks().add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
            getListBooks().add(new Book("创新工程实践", R.drawable.book_no_name));
            getListBooks().add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
        }
    }

    public List<Book> getListBooks() {
        return bookSaver.getBooks();
    }

    public class BookAdapter extends ArrayAdapter<Book> {

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
