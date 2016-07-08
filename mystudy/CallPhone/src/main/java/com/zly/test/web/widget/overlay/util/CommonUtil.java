package com.zly.test.web.widget.overlay.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

public class CommonUtil {

	public static ComponentName getTopActivity(Context context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

		if (runningTaskInfos != null && runningTaskInfos.size() > 0) {
			return runningTaskInfos.get(0).topActivity;
		}

		return null;

	}
}
