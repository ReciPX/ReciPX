package com.recipx.recipx.PX_API;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.recipx.recipx.BuildConfig;
import com.recipx.recipx.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class GetInfo extends AppCompatActivity {
    TextView home_text;
    String home_data;
    //git

    PX_Product product;
    ArrayList<ArrayList<PX_Product>> productlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        home_text = (TextView) findViewById(R.id.test_api);


        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                // 아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기

                // 건축물 API
                productlist = getPX_Product();

                for (PX_Product i : productlist.get(1)) { // for문을 통한 전체출력
                    home_data+=i.getTitle();
                    home_data+=i.getYear();
                    home_data+=i.getMonth();
                    home_data+=i.getStandard();

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        home_text.setText(home_data);
                    }
                });
            }
        }).start();
    }

    // xml parsing part
    ArrayList<ArrayList<PX_Product>> getPX_Product(){
        int maxyear=-1, maxmonth=-1;
        ArrayList<ArrayList<PX_Product>> productlist=new ArrayList<>();
        ArrayList<PX_Product> productlist_cost = new ArrayList<>();
        ArrayList<PX_Product> productlist_cnt = new ArrayList<>();
        String key = BuildConfig.PX_API_KEY;
        String type = "xml";
        String service = "DS_MND_PX_PARD_PRDT_INFO";
        String startpoint = "0";
        String endpoint = "2500";

        String queryUrl = "https://openapi.mnd.go.kr/"+key+"/"+type+"/"+service+"/"+startpoint+"/"+endpoint;

        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;
            xpp.next();
            int eventType= xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기
                        if(tag.equals("list_total_count")){

                        }
                        else if(tag.equals("row")) {
                            product = new PX_Product();
                        }
                        else if(tag.equals("rowno")) {
                            xpp.next();
                        }
                        else if(tag.equals("sellyear")) {
                            xpp.next();
                            product.setYear(xpp.getText());
                        }
                        else if(tag.equals("sellmonth")) {
                            xpp.next();
                            product.setMonth(xpp.getText());
                        }
                        else if(tag.equals("seltnstd")) {
                            xpp.next();
                            product.setStandard(xpp.getText());
                        }
                        else if(tag.equals("prdtnm")) {
                            xpp.next();
                            product.setTitle(xpp.getText());
                            int nowyear = Integer.parseInt(product.getYear());
                            int nowmonth = Integer.parseInt(product.getMonth());
                            String standard = product.getStandard();
                            if (maxyear == -1) {
                                maxyear = nowyear;
                                maxmonth = nowmonth;
                            }
                            if(maxyear==nowyear){
                                if(maxmonth==nowmonth){
                                    if(standard.equals("수량"))productlist_cnt.add(product);
                                    else if(standard.equals("금액"))productlist_cost.add(product);
                                }
                                else if(maxmonth<nowmonth){
                                    maxyear = nowyear;
                                    maxmonth = nowmonth;
                                    productlist_cnt.clear();
                                    productlist_cost.clear();
                                    if(standard.equals("수량"))productlist_cnt.add(product);
                                    else if(standard.equals("금액"))productlist_cost.add(product);
                                }
                            }
                            else if(maxyear<nowyear) {
                                maxyear = nowyear;
                                maxmonth = nowmonth;
                                productlist_cnt.clear();
                                productlist_cost.clear();
                                if(standard.equals("수량"))productlist_cnt.add(product);
                                else if(standard.equals("금액"))productlist_cost.add(product);
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        productlist.add(productlist_cnt);
        productlist.add(productlist_cost);
        return productlist;
    }
}