package com.slygon.mytodoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.*;

import android.app.*;
import android.net.*;
import android.os.*;
import android.util.*;

import com.loopj.android.http.*;

import java.io.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.json.*;


public class MyActivity extends Activity
{

    //    EditText etResponse;
    EditText etResponse;
    TextView tvIsConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // get reference to the views
        etResponse = (EditText) findViewById(R.id.etResponse);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);

        // check if you are connected or not
        if (isConnected())
		{
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are conncted");
        }
		else
		{
            tvIsConnected.setText("You are NOT conncted");
        }

		try
		{
			new TodoClientUsage().getTodos(this);
		}
		catch (JSONException e)
		{
			Log.d("error", e.getLocalizedMessage());
		}

        // call AsynTask to perform network operation on separate thread
//		new HttpAsyncTask().execute("http://hmkcode.appspot.com/rest/controller/get.json");
//      new HttpAsyncTask().execute("http://elad-site.herokuapp.com/api/todos");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
		{
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public boolean isConnected()
	{
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}

class TodoClientUsage
{
    public void getTodos(MyActivity ac) throws JSONException
	{

		final MyActivity a = ac;
		TodoClient.get("api/todos", null, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response)
				{
					// If the response is JSONObject instead of expected JSONArray
					//Toast.makeText(a, "Got obj", Toast.LENGTH_LONG).show();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONArray timeline)
				{
					// Pull out the first event on the public timeline
					try
					{
						//Toast.makeText(a, "Got arr", Toast.LENGTH_LONG).show();
						JSONObject firstEvent = (JSONObject) timeline.get(0);
						//String tweetText = firstEvent.getString("text");

						a.etResponse.setText(firstEvent.toString());

						// Do something with the response
						//System.out.println(tweetText);
					}
					catch (JSONException e)
					{
					}

				}

				@Override
				public void onFailure(int statusCode, Header[] headers, JSONArray timeline)
				{

					Toast.makeText(a, "Error getting json", Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onStart()
				{
					Toast.makeText(a, "Retrieving todos", Toast.LENGTH_SHORT).show();
				}

			});


    }
}
