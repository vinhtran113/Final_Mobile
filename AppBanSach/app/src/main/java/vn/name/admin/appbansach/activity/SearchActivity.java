package vn.name.admin.appbansach.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.name.admin.appbansach.R;
import vn.name.admin.appbansach.adapter.SachCuAdapter;
import vn.name.admin.appbansach.model.SanPhamMoi;
import vn.name.admin.appbansach.retrofit.ApiBanSach;
import vn.name.admin.appbansach.retrofit.RetrofitClient;
import vn.name.admin.appbansach.utils.Utils;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText edtsearch;
    SachCuAdapter adaptersc;
    List<SanPhamMoi> sanPhamMoiList;
    ApiBanSach apiBanSach;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        ActionToolBar();
    }

    private void initView() {
        sanPhamMoiList = new ArrayList<>();
        apiBanSach = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanSach.class);

        edtsearch = findViewById(R.id.edtsearch);
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recycleview_search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0){
                    sanPhamMoiList.clear();
                    adaptersc = new SachCuAdapter(getApplicationContext(), sanPhamMoiList);
                    recyclerView.setAdapter(adaptersc);

                }else {
                    getDataSearch(charSequence.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void getDataSearch(String s) {
        sanPhamMoiList.clear();
        compositeDisposable.add(apiBanSach.search(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()){
                                sanPhamMoiList = sanPhamMoiModel.getResult();
                                adaptersc = new SachCuAdapter(getApplicationContext(), sanPhamMoiList);
                                recyclerView.setAdapter(adaptersc);
                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Bạn chưa nhập Username", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}