package com.recipx.recipx.PX_API;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.recipx.recipx.BuildConfig;
import com.recipx.recipx.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Top_PX_Product extends AppCompatActivity {
    RecyclerView px_product_recyclerview;
    PX_Product_Adapter adapter;

    Button standard_btn;
    TextView info;
    PX_Product product;
    ArrayList<ArrayList<PX_Product>> productlist;
    int year = 0;
    int month = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_px_product);
        px_product_recyclerview = findViewById(R.id.px_product_recyclerview);
        standard_btn = findViewById(R.id.standard_btn);
        info = findViewById(R.id.tv_Year_Month);

        new PXProductAsyncTask().execute();

        standard_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (standard_btn.getText().equals("금액")) {
                    standard_btn.setText("수량");
                    adapter = new PX_Product_Adapter(getApplicationContext(), productlist.get(1));
                } else {
                    standard_btn.setText("금액");
                    adapter = new PX_Product_Adapter(getApplicationContext(), productlist.get(0));
                }
                px_product_recyclerview.setAdapter(adapter);
            }
        });
    }

    private class PXProductAsyncTask extends AsyncTask<Void, Void, ArrayList<ArrayList<PX_Product>>> {
        @Override
        protected ArrayList<ArrayList<PX_Product>> doInBackground(Void... voids) {
            if (productlist == null) {
                productlist = getPX_Product();
                for (int i = 0; i <= 1; i++) {
                    for (PX_Product P : productlist.get(i)) {
                        if (P.getTitle().contains("테라") || P.getTitle().contains("참이슬") || P.getTitle().contains("카스")) {
                            P.setSrc(P.getTitle());
                        } else {
                            String imageURL = getImageURLFromWeb(P.getTitle());
                            P.setSrc(imageURL);
                            Log.d("minseok", P.getTitle() + " " + imageURL);
                        }
                    }
                }
            }
            return productlist;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<PX_Product>> result) {
            info.setText(year + "년 " + month + "월");
            adapter = new PX_Product_Adapter(getApplicationContext(), result.get(0));
            px_product_recyclerview.setAdapter(adapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
            px_product_recyclerview.setLayoutManager(gridLayoutManager);
        }
    }

    ArrayList<ArrayList<PX_Product>> getPX_Product() {
        int maxyear = 0, maxmonth = 0;
        ArrayList<ArrayList<PX_Product>> productlist = new ArrayList<>();
        ArrayList<PX_Product> productlist_cost = new ArrayList<>();
        ArrayList<PX_Product> productlist_cnt = new ArrayList<>();
        String key = BuildConfig.PX_API_KEY;
        String type = "xml";
        String service = "DS_MND_PX_PARD_PRDT_INFO";
        String startpoint = "0";
        String endpoint = "2500";

        String queryUrl = "https://openapi.mnd.go.kr/" + key + "/" + type + "/" + service + "/" + startpoint + "/" + endpoint;

        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String tag;
            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();
                        if (tag.equals("list_total_count")) {
                        } else if (tag.equals("row")) {
                            product = new PX_Product();
                        } else if (tag.equals("rowno")) {
                            xpp.next();
                        } else if (tag.equals("sellyear")) {
                            xpp.next();
                            product.setYear(xpp.getText());
                        } else if (tag.equals("sellmonth")) {
                            xpp.next();
                            product.setMonth(xpp.getText());
                        } else if (tag.equals("seltnstd")) {
                            xpp.next();
                            product.setStandard(xpp.getText());
                        } else if (tag.equals("prdtnm")) {
                            xpp.next();
                            product.setTitle(xpp.getText());
                            int nowyear = Integer.parseInt(product.getYear());
                            int nowmonth = Integer.parseInt(product.getMonth());
                            String standard = product.getStandard();
                            if (maxyear == 0) {
                                maxyear = nowyear;
                                maxmonth = nowmonth;
                            }
                            if (maxyear == nowyear) {
                                if (maxmonth == nowmonth) {
                                    if (standard.equals("수량"))
                                        productlist_cnt.add(product);
                                    else if (standard.equals("금액"))
                                        productlist_cost.add(product);
                                } else if (maxmonth < nowmonth) {
                                    maxyear = nowyear;
                                    maxmonth = nowmonth;
                                    productlist_cnt.clear();
                                    productlist_cost.clear();
                                    if (standard.equals("수량"))
                                        productlist_cnt.add(product);
                                    else if (standard.equals("금액"))
                                        productlist_cost.add(product);
                                }
                            } else if (maxyear < nowyear) {
                                maxyear = nowyear;
                                maxmonth = nowmonth;
                                productlist_cnt.clear();
                                productlist_cost.clear();
                                if (standard.equals("수량"))
                                    productlist_cnt.add(product);
                                else if (standard.equals("금액"))
                                    productlist_cost.add(product);
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        productlist.add(productlist_cnt);
        productlist.add(productlist_cost);
        year = maxyear;
        month = maxmonth;
        return productlist;
    }

    private String getImageURLFromWeb(String title){
        try{

            String targetURL ="https://search.naver.com/search.naver?where=nexearch&sm=tab_jum&query="+title;
            //"https://search.naver.com/search.naver?where=image&sm=tab_jum&query="+title;

            Document doc = Jsoup.connect(targetURL).get();
            Element imageElement = doc.selectFirst("img");
            String imageURL = imageElement.attr("src");

            return imageURL;
        }catch(IOException e){
            e.printStackTrace();
        }
        return "";
    }
}