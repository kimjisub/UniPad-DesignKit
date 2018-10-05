package com.kimjisub.unipad.designkit.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.kimjisub.unipad.designkit.PackView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

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
						0 -> showToast("onFunctionBtnClick(0) on first exmaple")
						1 -> showToast("onFunctionBtnClick(1) on first exmaple")
						2 -> showToast("onFunctionBtnClick(2) on first exmaple")
					}
				}
			})

		LL_list.addView(firstExample)
	}

	fun showToast(msg: String) {
		Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
	}
}
