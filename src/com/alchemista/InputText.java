package com.alchemista;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.text.InputType;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/*
 * 		TextureRegion pole_input = new stb("Other/input",256,64).T;
		kod = new InputText(0,0,"Code Menu","Enter Code, only for chosen :)",pole_input,act.mFont,11,32,act.getVertexBufferObjectManager(),act)
		{
			@Override
			public void onOKClick()
			{
				kod_check();
			}
		};
 */
public class InputText extends ButtonSprite implements OnClickListener {

	private final String	mTitle;
	private final String	mMessage;
	private final Text		mText;
	private boolean			mIsPassword;
	private String			mValue;
	private BaseGameActivity mContext;
	private TextureRegion textura;
	
	public InputText(float pX, float pY, final String title, final String message, TextureRegion texture,
			Font font, int textOffsetX, int textOffsetY, VertexBufferObjectManager vbo, BaseGameActivity context) {
		super(pX, pY, texture, vbo, null);

		this.textura = texture;
		this.mMessage = message;
		this.mTitle = title;
		this.mContext = context;
		this.mText = new Text(textOffsetX, textOffsetY, font, "", 256, vbo);
		mText.setTextOptions(new TextOptions());
		attachChild(this.mText);
		pozycjonuj();
		setOnClickListener(this);
	}

	public void pozycjonuj()
	{
		mText.setScaleCenter(0, 0);
		mText.setScale(0.01f);

		for(float i=0.01f; i<4f;i+=0.01f)
		{
			mText.setScale(i);
			if(mText.getWidthScaled() >= textura.getWidth() || mText.getHeightScaled() >= textura.getHeight())
			{
				mText.setScale(i-0.01f);
				mText.setPosition(textura.getWidth()/2 - mText.getWidthScaled()/2, textura.getHeight()/2 - mText.getHeightScaled()/2);
				break;
			}
		}
	
		mText.setPosition(textura.getWidth()/2 - mText.getWidthScaled()/2, textura.getHeight()/2 - mText.getHeightScaled()/2);
		
	}
	
	
	public String getText() {
		return this.mValue;
	}

	public boolean isPassword() {
		return this.mIsPassword;
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		showTextInput();
		pozycjonuj();
	
	}

	public void setPassword(final boolean isPassword) {
		this.mIsPassword = isPassword;
	}

	public void setText(String text) {
		this.mValue = text;

		if (isPassword() && text.length() > 0)
			text = String.format("%0" + text.length() + "d", 0).replace("0", "*");
		this.mText.setText(text);
		pozycjonuj();
	}

	public void showTextInput() {
		mContext.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

				alert.setTitle(InputText.this.mTitle);
				alert.setMessage(InputText.this.mMessage);

				final EditText editText = new EditText(mContext);
				editText.setTextSize(20f);
				editText.setText(" ");
				editText.setGravity(Gravity.CENTER_HORIZONTAL);
				if (isPassword())
					editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

				alert.setView(editText);

				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
					setText(editText.getText().toString());
					onOKClick();
					}
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

				final AlertDialog dialog = alert.create();
				dialog.setOnShowListener(new OnShowListener() {
					@Override
					public void onShow(DialogInterface dialog) {
						editText.requestFocus();
						final InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
					}
				});
				dialog.show();
			}
		});
			

		
	}
	public void onOKClick()
	{
		
	}
}


