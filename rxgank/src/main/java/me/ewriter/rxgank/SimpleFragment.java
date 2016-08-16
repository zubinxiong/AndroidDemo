package me.ewriter.rxgank;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import me.ewriter.rxgank.adapter.SimpeFragmentAdapter;
import me.ewriter.rxgank.api.ApiManager;
import me.ewriter.rxgank.api.entity.GankData;
import me.ewriter.rxgank.api.entity.GankItem;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SimpleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimpleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private SimpeFragmentAdapter adapter;
    private List<GankItem> mTitleList;

    int page = 1;

    public SimpleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SimpleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimpleFragment newInstance(String param1, String param2) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_simple, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mTitleList = new ArrayList<>();
        adapter = new SimpeFragmentAdapter(getActivity(), mTitleList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        loadData();
    }

    private void loadData() {
        String type = "";
        if (mParam1.equals("0")) {
            type = ApiManager.CATEGORY_ANDROID;
        } else if (mParam1.equals("1")) {
            type = ApiManager.CATEGORY_IOS;
        }

//        ApiManager.getsGankApi().getGankData(type, page)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<GankData, List<GankItem>>() {
//                    @Override
//                    public List<GankItem> call(GankData gankData) {
//                        return gankData.getResults();
//                    }
//                })
//                .subscribe(new Subscriber<List<GankItem>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<GankItem> gankItems) {
//                        mTitleList.addAll(gankItems);
//                        adapter.notifyDataSetChanged();
//                    }
//                });

        // 这种请求和上面是相同的，只是一个直接处理成 List，然后addAll， 下面是拆分成 item 再添加
        ApiManager.getsGankApi().getGankData(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<GankData, List<GankItem>>() {
                    @Override
                    public List<GankItem> call(GankData gankData) {
                        return gankData.getResults();
                    }
                })
                .flatMap(new Func1<List<GankItem>, rx.Observable<GankItem>>() {
                    @Override
                    public rx.Observable<GankItem> call(List<GankItem> gankItems) {
                        return rx.Observable.from(gankItems);
                    }
                })
                .subscribe(new Subscriber<GankItem>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GankItem gankItem) {
                        mTitleList.add(gankItem);
                        adapter.notifyItemInserted(mTitleList.size() - 1);
                    }
                });
    }
}
