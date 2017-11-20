package com.example.teerasaksathu.productionjongtalad.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.teerasaksathu.productionjongtalad.R;
import com.example.teerasaksathu.productionjongtalad.activity.LockReservationActivity;
import com.example.teerasaksathu.productionjongtalad.activity.MainActivity;
import com.example.teerasaksathu.productionjongtalad.adapter.MarketListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MarketListFragment extends Fragment {

    ListView marketList;
    String[] nameMarket, marketAddress;
    String[] imageUrl;
    String username;

    public MarketListFragment() {
        super();
    }

    public static MarketListFragment newInstance() {
        MarketListFragment fragment = new MarketListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maketlist, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here



        username = MainActivity.intentUsername.getStringExtra("username");

        LoadMerketList loadMarketList = new LoadMerketList();
        loadMarketList.execute(username);

        marketList = rootView.findViewById(R.id.marketList);
        marketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), LockReservationActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    private class LoadMerketList extends AsyncTask<String, Void, String> {
        public static final String URL = "http://www.jongtalad.com/doc/phpNew/load_market_list.php";

        @Override
        protected String doInBackground(String... values) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", values[0])
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Not Success - code : " + response.code();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error - " + e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                nameMarket = new String[jsonArray.length()];
                imageUrl = new String[jsonArray.length()];
                marketAddress = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    nameMarket[i] = (jsonObject.getString("name"));
                    imageUrl[i] = (jsonObject.getString("picture_url"));
                    marketAddress[i] = (jsonObject.getString("address"));
                }



            } catch (JSONException e) {
                nameMarket = new String[1];
                imageUrl = new String[1];
                marketAddress = new String[1];

                nameMarket[0] = "none";
                imageUrl[0] = "none";
                marketAddress[0] = "none";

                e.printStackTrace();
            }

            MarketListAdapter marketListAdapter = new MarketListAdapter(getActivity(), nameMarket, imageUrl, marketAddress);
            marketList.setAdapter(marketListAdapter);
        }
    }
}
