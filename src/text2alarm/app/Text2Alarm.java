package text2alarm.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.*;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

public class Text2Alarm extends Activity {
	private static final String ACTION_INTERCEPT = "com.adamrocker.android.simeji.ACTION_INTERCEPT";
	private static final String REPLACE_KEY = "replace_key";

//	public static Vibrator vibrator;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Intent it = getIntent();
		String action = it.getAction();
        
		if (action != null && ACTION_INTERCEPT.equals(action)) {
			/* Simejiから呼出された時 */
			String str = it.getStringExtra(REPLACE_KEY);// 置換元の文字を取得
			Log.i("Mushroom-REPLACE_KEY", str);
			setContentView(R.layout.mushroom);
			if (str.length() == 0){
				ClipboardManager cm = 
				      (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 
				str = (String) cm.getText();
			}
//			TextView view = (TextView)findViewById(R.id.debug_view);
//			view.setText(str);
			parse(str);
		} else {
			// Simeji以外から呼出された時
	        setContentView(R.layout.main);
	        Button button = (Button)findViewById(R.id.Button01);
	        
	        button.setOnClickListener(new View.OnClickListener(){

				public void onClick(View v) {
					Date d = Calendar.getInstance().getTime();
					setAlarm(d, d.toString(), Text2Alarm.this);
				}

	        });
		}
        


    }
    
    public static void setAlarm(Date d, String text, android.content.Context context)
    {
        Intent intent = new Intent(context, OneShotAlarm.class);
//        intent.setAction("hogehoge");
        PendingIntent sender = PendingIntent.getBroadcast(context,
                0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.SECOND, -180);

        // Schedule the alarm!
        AlarmManager am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        Log.i("text2alarm", text);
        Toast.makeText(context, "アラームを追加:\n" + text,
                Toast.LENGTH_LONG).show();
    }

	/**
	 * 元の文字を置き換える
	 * @param result Replacing string
	 */
	private void parse(String text) {
		DateParser dp = new DateParser();
		Date d = dp.parse(text);
		if(d != null) {
			Text2Alarm.setAlarm(d, d.toString(), Text2Alarm.this);
		}else{
			Toast.makeText(Text2Alarm.this, "時刻解析不可",
	                Toast.LENGTH_LONG).show();			
		}

		Intent data = new Intent();
		data.putExtra(REPLACE_KEY, text);
		setResult(RESULT_OK, data);

		finish();
	}

}

