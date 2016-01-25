package com.example.infolife.listviewitemslidedeletebtnshow;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

public class QQListView extends ListView
{

	private static final String TAG = "QQlistView";

	// private static final int VELOCITY_SANP = 200;
	// private VelocityTracker mVelocityTracker;
	/**
	 *
	 */
	private int touchSlop;

	/**
	 *
	 */
	private boolean isSliding;

	/**
	 *
	 */
	private int xDown;
	/**
	 *
	 */
	private int yDown;
	/**
	 *
	 */
	private int xMove;
	/**
	 *
	 */
	private int yMove;

	private LayoutInflater mInflater;

	private PopupWindow mPopupWindow;
	private int mPopupWindowHeight;
	private int mPopupWindowWidth;

	private Button mDelBtn;
	/**
	 *
	 */
	private DelButtonClickListener mListener;

	/**
	 *
	 */
	private View mCurrentView;

	/**
	 *
	 */
	private int mCurrentViewPos;

	/**
	 *
	 * @param context
	 * @param attrs
	 */
	public QQListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		mInflater = LayoutInflater.from(context);
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		View view = mInflater.inflate(R.layout.delete_btn, null);
		mDelBtn = (Button) view.findViewById(R.id.id_item_btn);
		mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		/**
		 *
		 */
		mPopupWindow.getContentView().measure(0, 0);
		mPopupWindowHeight = mPopupWindow.getContentView().getMeasuredHeight();
		mPopupWindowWidth = mPopupWindow.getContentView().getMeasuredWidth();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		int action = ev.getAction();
		int x = (int) ev.getX();
		int y = (int) ev.getY();
		switch (action)
		{

			case MotionEvent.ACTION_DOWN:
				xDown = x;
				yDown = y;
				/**
				 *   */
				if (mPopupWindow.isShowing())
				{
					dismissPopWindow();
					return false;
				}
				mCurrentViewPos = pointToPosition(xDown, yDown);
				View view = getChildAt(mCurrentViewPos - getFirstVisiblePosition());
				mCurrentView = view;
				break;
			case MotionEvent.ACTION_MOVE:
				xMove = x;
				yMove = y;
				int dx = xMove - xDown;
				int dy = yMove - yDown;
				/**
				 */
				if (xMove < xDown && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop)
				{
					// Log.e(TAG, "touchslop = " + touchSlop + " , dx = " + dx +
					// " , dy = " + dy);
					isSliding = true;
				}
				break;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		int action = ev.getAction();
		/**
		 */
		if (isSliding)
		{
			switch (action)
			{
				case MotionEvent.ACTION_MOVE:

					int[] location = new int[2];
					mCurrentView.getLocationOnScreen(location);
					mPopupWindow.setAnimationStyle(R.style.popwindow_delete_btn_anim_style);
					mPopupWindow.update();
					mPopupWindow.showAtLocation(mCurrentView, Gravity.LEFT | Gravity.TOP,
							location[0] + mCurrentView.getWidth(), location[1] + mCurrentView.getHeight() / 2
									- mPopupWindowHeight / 2);
					mDelBtn.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							if (mListener != null)
							{
								mListener.clickHappend(mCurrentViewPos);
								mPopupWindow.dismiss();
							}
						}
					});
					// Log.e(TAG, "mPopupWindow.getHeight()=" + mPopupWindowHeight);

					break;
				case MotionEvent.ACTION_UP:
					isSliding = false;

			}
			return true;
		}

		return super.onTouchEvent(ev);
	}

	/**
	 */
	private void dismissPopWindow()
	{
		if (mPopupWindow != null && mPopupWindow.isShowing())
		{
			mPopupWindow.dismiss();
		}
	}

	public void setDelButtonClickListener(DelButtonClickListener listener)
	{
		mListener = listener;
	}

	interface DelButtonClickListener
	{
		public void clickHappend(int position);
	}

}
