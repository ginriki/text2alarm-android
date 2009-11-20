package text2alarm.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

//OneShotAlarmは単純にメッセージを表示します。
public class OneShotAlarm extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, R.string.alarm_received, Toast.LENGTH_SHORT).show();
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
    }
}
