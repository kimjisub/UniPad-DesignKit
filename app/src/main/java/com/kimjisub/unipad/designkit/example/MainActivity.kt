package com.kimjisub.unipad.designkit.example

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.kimjisub.unipad.designkit.FileExplorer
import com.kimjisub.unipad.designkit.PackView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		firstExample()
		explorer()
	}

	private fun firstExample(){
		val firstExample = PackView(this@MainActivity)
			.setFlagColor(resources.getColor(R.color.red))
			.setTitle("this is title")
			.setSubTitle("this is subtitle")
			.addInfo("info1", "test1")
			.addInfo("info2", "test2")
			.addInfo("info3", "test3")
			.addBtn("btn1", resources.getColor(R.color.red))
			.addBtn("btn2", resources.getColor(R.color.orange))
			.setOption1("option1", true)
			.setOption2("option2", false)
			.setOnEventListener(object : PackView.OnEventListener {
				override fun onViewClick(v: PackView) {
					v.togglePlay()
					showToast("onViewClick on first exmaple")
				}

				override fun onViewLongClick(v: PackView) {
					v.toggleDetail()
					showToast("onViewLongClick on first exmaple")
				}

				override fun onPlayClick(v: PackView) {
					showToast("onPlayClick on first exmaple")
				}

				override fun onFunctionBtnClick(v: PackView, index: Int) {
					when (index) {
						0 -> showToast("onFunctionBtnClick(0) on first example")
						1 -> showToast("onFunctionBtnClick(1) on first example")
						2 -> showToast("onFunctionBtnClick(2) on first example")
					}
				}
			})

		val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
		val left = dpToPx(16f)
		val top = 0
		val right = dpToPx(16f)
		val bottom = dpToPx(10f)
		lp.setMargins(left, top, right, bottom)
		LL_list.addView(firstExample, lp)

	}

	private fun explorer(){
		var url = System.getenv("SECONDARY_STORAGE") + "/Download"
		if (!java.io.File(url).isDirectory)
			url = Environment.getExternalStorageDirectory().path
		if (!java.io.File(url).isDirectory)
			url = "/"

		BTN_fileExplorer.setOnClickListener {
			TedPermission.with(this)
				.setPermissionListener(object : PermissionListener {
					override fun onPermissionGranted() {
						FileExplorer(this@MainActivity, url)
							.setOnEventListener(object : FileExplorer.OnEventListener {
								override fun onFileSelected(fileURL: String) {
									showToast("onFileSelected: $fileURL")
								}

								override fun onURLChanged(folderURL: String) {
									showToast("onURLChanged: $folderURL")
								}
							})
							.show()
					}

					override fun onPermissionDenied(deniedPermissions: List<String>) {
					}
				})
				.setRationaleMessage(R.string.permissionRequire)
				.setDeniedMessage(R.string.permissionDenied)
				.setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				.check()
		}
	}

	fun showToast(msg: String) {
		Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
	}

	fun dpToPx(dp: Float): Int {
		val metrics = resources.displayMetrics
		val px = dp * (metrics.densityDpi / 160f)
		return Math.round(px)
	}
}
