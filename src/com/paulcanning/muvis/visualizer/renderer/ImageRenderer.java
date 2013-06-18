package com.paulcanning.muvis.visualizer.renderer;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.paulcanning.muvis.visualizer.AudioData;
import com.paulcanning.muvis.visualizer.FFTData;

public class ImageRenderer extends Renderer
{
  private Bitmap mBitmap;
  private float amplitude = 0;

  /**
   * Renders the audio data onto a line. The line flashes on prominent beats
   * @param canvas
   * @param paint - Paint to draw lines with
   * @param paint - Paint to draw flash with
   * @param cycleColor - If true the color will change on each frame
   */
  public ImageRenderer(Bitmap bitmap)
  {
    super();
    mBitmap = bitmap;
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
    	
    	Random r = new Random();
    	int randWidth = r.nextInt(rect.width() - 0 + 1);
    	int randHeight = r.nextInt(rect.height() - 0 + 1);
    	
    	int randScale = r.nextInt(100 - 1 + 1) + 1;
    	//int randScaleHeight = r.nextInt(mBitmap.getHeight() - 50 + 1) + 50;
    	
    	Bitmap scaled = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * randScale / 100, mBitmap.getHeight() * randScale / 100, false);
    	
    	//Paint imagePaint = new Paint();
    	//ColorFilter imageFilter = new LightingColorFilter(Color.rgb(0, 0, 255), 1);
    	
    	//imagePaint.setColorFilter(imageFilter);
    	
    	canvas.drawBitmap(scaled, randWidth, randHeight, null);

    	
    } else {
    	// Amplitude is nothing special, reduce the amplitude
    	amplitude *= 0.99;
    	//canvas.drawLines(mPoints, mPaint);
    }
  }

  @Override
  public void onRender(Canvas canvas, FFTData data, Rect rect)
  {
    // Do nothing, we only display audio data
  }
}
