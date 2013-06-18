package com.paulcanning.muvis.visualizer.renderer;

import java.io.InputStream;

import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.paulcanning.muvis.visualizer.AudioData;
import com.paulcanning.muvis.visualizer.FFTData;

public class AnimationRenderer extends Renderer
{
	private static final String TAG = "com.paulcanning.muvis";
	//private AnimationDrawable mBitmap;
	private AnimationDrawable mBitmap;
	private float amplitude = 0;

	/**
	 * Renders the audio data onto a line. The line flashes on prominent beats
	 * @param canvas
	 * @param paint - Paint to draw lines with
	 * @param paint - Paint to draw flash with
	 * @param cycleColor - If true the color will change on each frame
	 */
	public AnimationRenderer(AnimationDrawable anim)
	{
		super();
		mBitmap = anim;
	}

  @Override
  public void onRender(Canvas canvas, AudioData data, Rect rect)
  {

    // Calculate points for line
    /*for (int i = 0; i < data.bytes.length - 1; i++) {
      mPoints[i * 4] = rect.width() * i / (data.bytes.length - 1);
      mPoints[i * 4 + 1] =  rect.height() / 2
          + ((byte) (data.bytes[i] + 128)) * (rect.height() / 3) / 128;
      mPoints[i * 4 + 2] = rect.width() * (i + 1) / (data.bytes.length - 1);
      mPoints[i * 4 + 3] = rect.height() / 2
          + ((byte) (data.bytes[i + 1] + 128)) * (rect.height() / 3) / 128;
    }
*/
	  
    // Calc amplitude for this waveform
    float accumulator = 0;
    for (int i = 0; i < data.bytes.length - 1; i++) {
      accumulator += Math.abs(data.bytes[i]);
    }
     
    float amp = accumulator/(128 * data.bytes.length);
     
    if(amp > amplitude)
    {
    	// Amplitude is bigger than normal, make a prominent line
    	amplitude = amp;
    	
    } else {
    	// Amplitude is nothing special, reduce the amplitude
    	amplitude *= 0.99;
    }

    Log.d(TAG, "rect: " + rect);

    mBitmap.setBounds(rect);
    mBitmap.setCallback(null);
    mBitmap.setVisible(true, false);
    mBitmap.draw(canvas);
    mBitmap.start();

	  /*InputStream is = getApplicationContext().getResources().openRawResource(R.drawable.png1);
	  Movie myMovie = Movie.decodeStream(is);
	  myMovie.draw(canvas, 0f, 0f);*/
  }

  @Override
  public void onRender(Canvas canvas, FFTData data, Rect rect)
  {
    // Do nothing, we only display audio data
  }
}
