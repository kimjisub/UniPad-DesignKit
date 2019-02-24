package com.kimjisub.unipad.designkit.example;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.kimjisub.unipad.designkit.FileExplorer;
import com.kimjisub.unipad.designkit.PackView;
import com.kimjisub.unipad.designkit.PackViewSimple;
import com.kimjisub.unipad.designkit.SyncCheckBox;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	LinearLayout LL_list;
	Button BTN_fileExplorer;
	CheckBox CB_syncCheckBox1;
	CheckBox CB_syncCheckBox2;
	CheckBox CB_syncCheckBox3;
	Button BTN_syncToggleButton1;
	CheckBox CB_lock;

	void initVar() {
		LL_list = findViewById(R.id.LL_list);
		BTN_fileExplorer = findViewById(R.id.BTN_fileExplorer);
		CB_syncCheckBox1 = findViewById(R.id.CB_syncCheckBox1);
		CB_syncCheckBox2 = findViewById(R.id.CB_syncCheckBox2);
		CB_syncCheckBox3 = findViewById(R.id.CB_syncCheckBox3);
		BTN_syncToggleButton1 = findViewById(R.id.BTN_syncToggleButton1);
		CB_lock = findViewById(R.id.CB_lock);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initVar();

		packView();
		packViewSimple();
		explorer();
		syncCheckBox();
	}


	private void packView() {
		PackView packView = new PackView(MainActivity.this)
				.setFlagColor(getResources().getColor(R.color.red))
				.setTitle("this is title")
				.setSubTitle("this is subtitle")
				.addInfo("info1", "test1")
				.addInfo("info2", "test2")
				.addInfo("info3", "test3")
				.addBtn("btn1", getResources().getColor(R.color.red))
				.addBtn("btn2", getResources().getColor(R.color.orange))
				.setOption1("option1", true)
				.setOption2("option2", false)
				.setOnEventListener(new PackView.OnEventListener() {
					@Override
					public void onViewClick(PackView v) {
						v.togglePlay();
						showToast("onViewClick on first exmaple");
					}

					@Override
					public void onViewLongClick(PackView v) {
						v.toggleDetail();
						showToast("onViewLongClick on first exmaple");
					}

					@Override
					public void onPlayClick(PackView v) {
						showToast("onPlayClick on first exmaple");
					}

					@Override
					public void onFunctionBtnClick(PackView v, int index) {
						showToast("onFunctionBtnClick(" + index + ") on first example");
					}
				});

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		int left = dpToPx(16f);
		int top = 0;
		int right = dpToPx(16f);
		int bottom = dpToPx(10f);
		lp.setMargins(left, top, right, bottom);
		LL_list.addView(packView, lp);

	}

	private void packViewSimple() {
		PackViewSimple packViewSimple = new PackViewSimple(MainActivity.this)
				.setFlagColor(getResources().getColor(R.color.red))
				.setTitle("this is title this is title this is title this is title this is title this is title")
				.setSubTitle("this is subtitle")
				.setOption1("option1", true)
				.setOption2("option2", false)
				.setOnEventListener(new PackViewSimple.OnEventListener() {
					@Override
					public void onViewClick(PackViewSimple v) {
						v.toggle();
						showToast("onViewClick on first exmaple");
					}

					@Override
					public void onViewLongClick(PackViewSimple v) {
						showToast("onViewLongClick on first exmaple");
					}

					@Override
					public void onPlayClick(PackViewSimple v) {
						showToast("onPlayClick on first exmaple");
					}
				});

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		int left = dpToPx(16f);
		int top = 0;
		int right = dpToPx(16f);
		int bottom = dpToPx(10f);
		lp.setMargins(left, top, right, bottom);
		LL_list.addView(packViewSimple, lp);

	}

	private void explorer() {
		String url = System.getenv("SECONDARY_STORAGE") + "/Download";
		if (!new File(url).isDirectory())
			url = Environment.getExternalStorageDirectory().getPath();
		if (!new File(url).isDirectory())
			url = "/";

		String finalUrl = url;
		BTN_fileExplorer.setOnClickListener(v -> TedPermission.with(MainActivity.this)
				.setPermissionListener(new PermissionListener() {
					@Override
					public void onPermissionGranted() {
						new FileExplorer(MainActivity.this, finalUrl)
								.setOnEventListener(new FileExplorer.OnEventListener() {
									@Override
									public void onFileSelected(String filePath) {
										showToast("onFileSelected: " + filePath);
									}

									@Override
									public void onPathChanged(String folderPath) {
										showToast("onPathChanged: " + folderPath);
									}
								}).show();
					}

					@Override
					public void onPermissionDenied(List<String> deniedPermissions) {

					}
				})
				.setRationaleMessage(R.string.permissionRequire)
				.setDeniedMessage(R.string.permissionDenied)
				.setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				.check());

	}

	private void syncCheckBox() {
		SyncCheckBox syncCheckBox = new SyncCheckBox();
		syncCheckBox.addCheckBox(CB_syncCheckBox1);
		syncCheckBox.addCheckBox(CB_syncCheckBox2);
		syncCheckBox.addCheckBox(CB_syncCheckBox3);
		BTN_syncToggleButton1.setOnClickListener(v -> syncCheckBox.toggleChecked());

		CB_lock.setOnCheckedChangeListener((buttonView, isChecked) -> syncCheckBox.setLocked(isChecked));
	}

	void showToast(String msg) {
		Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	int dpToPx(float dp) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return Math.round(px);
	}
}
