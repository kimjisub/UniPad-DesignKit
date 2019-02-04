package com.kimjisub.unipad.designkit;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kimjisub.unipad.designkit.manage.UIManager;

public class PackViewSimple extends RelativeLayout {

	Context context;

	RelativeLayout RL_root;
	RelativeLayout RL_touchView;
	LinearLayout LL_leftView;
	RelativeLayout RL_playBtn;
	ImageView IV_playImg;
	TextView TV_playText;

	RelativeLayout RL_flag;
	int RL_flag_color;
	TextView TV_title;
	TextView TV_subTitle;
	TextView TV_option1;
	TextView TV_option2;

	boolean status = false;

	int PX_flag_default;
	int PX_flag_enable;
	int PX_info_default;
	int PX_info_enable;
	int PX_info_extend;

	private void initView(Context context) {
		this.context = context;

		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
		View v = li.inflate(R.layout.packviewsimple, this, false);
		addView(v);


		// set view
		RL_root = findViewById(R.id.root);
		RL_touchView = findViewById(R.id.touchView);
		LL_leftView = findViewById(R.id.leftView);
		RL_playBtn = findViewById(R.id.playBtn);
		IV_playImg = findViewById(R.id.playImg);
		TV_playText = findViewById(R.id.playText);

		RL_flag = findViewById(R.id.flag);
		TV_title = findViewById(R.id.title);
		TV_subTitle = findViewById(R.id.subTitle);
		TV_option1 = findViewById(R.id.option1);
		TV_option2 = findViewById(R.id.option2);

		// set vars
		PX_flag_default = UIManager.dpToPx(context, 10);
		PX_flag_enable = UIManager.dpToPx(context, 100);
		PX_info_default = UIManager.dpToPx(context, 40);
		PX_info_enable = UIManager.dpToPx(context, 75);
		PX_info_extend = UIManager.dpToPx(context, 300);


		// set preset
		LL_leftView.setX(PX_flag_default);


		// set listener
		RL_touchView.setOnClickListener(v1 -> onViewClick());
		RL_touchView.setOnLongClickListener(v2 -> {
			onViewLongClick();
			return true;
		});
	}

	public PackViewSimple(Context context) {
		super(context);
		initView(context);
	}

	public PackViewSimple(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		getAttrs(attrs);
	}

	public PackViewSimple(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		initView(context);
		getAttrs(attrs, defStyle);
	}

	public static PackViewSimple errItem(Context context, String title, String subTitle, OnEventListener listener) {
		return new PackViewSimple(context)
			.setFlagColor(context.getResources().getColor(R.color.red))
			.setTitle(title)
			.setSubTitle(subTitle)
			//.setOptionVisibility(false)
			.setOnEventListener(listener);
	}

	private void getAttrs(AttributeSet attrs) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PackView);
		setTypeArray(typedArray);
	}

	private void getAttrs(AttributeSet attrs, int defStyle) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PackView, defStyle, 0);
		setTypeArray(typedArray);
	}

	private void setTypeArray(TypedArray typedArray) {

		/*int color = typedArray.getResourceId(R.styleable.PackView_flagColor, R.drawable.border_play_blue);
		//setFlagColor(color);

		String title = typedArray.getString(R.styleable.PackView_title);
		//setTitle(title);

		String subTitle = typedArray.getString(R.styleable.PackView_subTitle);
		//setSubTitle(subTitle);

		Boolean LED = typedArray.getBoolean(R.styleable.PackView_LED, false);
		//setLED(LED);

		Boolean autoPlay = typedArray.getBoolean(R.styleable.PackView_AutoPlay, false);
		//setAutoPlay(autoPlay);

		String size = typedArray.getString(R.styleable.PackView_size);
		//setSize(size);

		String chain = typedArray.getString(R.styleable.PackView_chain);
		//setChain(chain);

		int capacity = typedArray.getInteger(R.styleable.PackView_capacity, 0);
		//setCapacity(capacity);

		Boolean optionVisibility = typedArray.getBoolean(R.styleable.PackView_optionVisibility, true);
		//setOptionVisibility(optionVisibility);*/

		typedArray.recycle();
	}
	//========================================================================================= set / update / etc..

	public PackViewSimple setStatus(boolean bool) {
		status = bool;

		return this;
	}

	public boolean getStatus() {
		return status;
	}

	public PackViewSimple setFlagColor(int color) {
		GradientDrawable flagBackground = (GradientDrawable) getResources().getDrawable(R.drawable.border_all_round);
		flagBackground.setColor(color);
		RL_flag.setBackground(flagBackground);
		RL_flag_color = color;

		return this;
	}

	public PackViewSimple updateFlagColor(int colorNext) {

		int colorPrev = RL_flag_color;
		ValueAnimator animator = ObjectAnimator.ofObject(new ArgbEvaluator(), colorPrev, colorNext);
		animator.setDuration(500);
		animator.addUpdateListener(valueAnimator -> {
			int color3 = (int) valueAnimator.getAnimatedValue();

			GradientDrawable flagBackground = (GradientDrawable) getResources().getDrawable(R.drawable.border_all_round);
			flagBackground.setColor(color3);
			RL_flag.setBackground(flagBackground);
		});
		animator.start();
		RL_flag_color = colorNext;

		return this;
	}

	public PackViewSimple setTitle(String str) {
		TV_title.setText(str);
		return this;
	}

	public PackViewSimple setSubTitle(String str) {
		TV_subTitle.setText(str);
		return this;
	}

	public PackViewSimple setOption1(String msg, boolean bool) {
		int green = getResources().getColor(R.color.green);
		int pink = getResources().getColor(R.color.pink);

		TV_option1.setText(msg);
		TV_option1.setTextColor(bool ? green : pink);

		return this;
	}

	public PackViewSimple setOption2(String msg, boolean bool) {
		int green = getResources().getColor(R.color.green);
		int pink = getResources().getColor(R.color.pink);

		TV_option2.setText(msg);
		TV_option2.setTextColor(bool ? green : pink);

		return this;
	}

	public PackViewSimple setPlayImageShow(boolean bool) {
		IV_playImg.setVisibility(bool ? VISIBLE : GONE);

		return this;
	}

	public PackViewSimple setPlayText(String str) {
		TV_playText.setText(str);

		return this;
	}


	//========================================================================================= Play

	private boolean isPlay = false;

	public void togglePlay(boolean bool) {
		if (isPlay != bool) {
			if (bool) {
				//animation
				LL_leftView.animate().x(PX_flag_enable).setDuration(500).start();

				//clickEvent
				RL_playBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						onPlayClick();
					}
				});
			} else {
				//animation
				LL_leftView.animate().x(PX_flag_default).setDuration(500).start();

				//clickEvent
				RL_playBtn.setOnClickListener(null);
				RL_playBtn.setClickable(false);
			}
			isPlay = bool;
		}
	}

	public void togglePlay() {
		togglePlay(!isPlay);
	}

	public void togglePlay(boolean bool, int onColor, int offColor) {
		togglePlay(bool);
		if (isPlay)
			updateFlagColor(onColor);
		else
			updateFlagColor(offColor);

	}

	public void togglePlay(int onColor, int offColor) {
		togglePlay(!isPlay);
		if (isPlay)
			updateFlagColor(onColor);
		else
			updateFlagColor(offColor);

	}

	public boolean isPlay() {
		return isPlay;
	}
	
	//========================================================================================= Listener
	
	
	private OnEventListener onEventListener = null;
	
	public interface OnEventListener {
		
		void onViewClick(PackViewSimple v);
		
		void onViewLongClick(PackViewSimple v);
		
		void onPlayClick(PackViewSimple v);
	}
	
	public PackViewSimple setOnEventListener(OnEventListener listener) {
		this.onEventListener = listener;
		return this;
	}
	
	public void onViewClick() {
		if (onEventListener != null) onEventListener.onViewClick(this);
	}
	
	public void onViewLongClick() {
		if (onEventListener != null) onEventListener.onViewLongClick(this);
	}
	
	public void onPlayClick() {
		if (onEventListener != null && isPlay()) onEventListener.onPlayClick(this);
	}
}