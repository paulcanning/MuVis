package com.paulcanning.muvis;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.paulcanning.muvis.visualizer.VisualizerView;
import com.paulcanning.muvis.visualizer.renderer.AnimationRenderer;
import com.paulcanning.muvis.visualizer.renderer.BarGraphRenderer;
import com.paulcanning.muvis.visualizer.renderer.CircleBarRenderer;
import com.paulcanning.muvis.visualizer.renderer.CircleRenderer;
import com.paulcanning.muvis.visualizer.renderer.ImageRenderer;
import com.paulcanning.muvis.visualizer.renderer.LineRenderer;

/**
 * Demo to show how to use VisualizerView
 */
public class MainActivity extends Activity {

  private VisualizerView mVisualizerView;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    init();
  }

  @Override
  protected void onPause()
  {
    cleanUp();
    super.onPause();
  }

  @Override
  protected void onDestroy()
  {
    cleanUp();
    super.onDestroy();
  }

  private void init()
  {
    // We need to link the visualizer view to the media player so that
    // it displays something
    mVisualizerView = (VisualizerView) findViewById(R.id.visualizerView);
    mVisualizerView.link();

    // Start with just line renderer
    //addLineRenderer();
    //addImageRenderer();
    addAnimationRenderer();
  }

  private void cleanUp()
  {
      mVisualizerView.release();
  }

  // Methods for adding renderers to visualizer
  private void addBarGraphRenderers()
  {
    Paint paint = new Paint();
    paint.setStrokeWidth(50f);
    paint.setAntiAlias(true);
    paint.setColor(Color.argb(200, 56, 138, 252));
    BarGraphRenderer barGraphRendererBottom = new BarGraphRenderer(16, paint, false);
    mVisualizerView.addRenderer(barGraphRendererBottom);

    Paint paint2 = new Paint();
    paint2.setStrokeWidth(12f);
    paint2.setAntiAlias(true);
    paint2.setColor(Color.argb(200, 181, 111, 233));
    BarGraphRenderer barGraphRendererTop = new BarGraphRenderer(4, paint2, true);
    mVisualizerView.addRenderer(barGraphRendererTop);
  }

  private void addCircleBarRenderer()
  {
    Paint paint = new Paint();
    paint.setStrokeWidth(8f);
    paint.setAntiAlias(true);
    paint.setXfermode(new PorterDuffXfermode(Mode.LIGHTEN));
    paint.setColor(Color.argb(255, 222, 92, 143));
    CircleBarRenderer circleBarRenderer = new CircleBarRenderer(paint, 32, true);
    mVisualizerView.addRenderer(circleBarRenderer);
  }

  private void addCircleRenderer()
  {
    Paint paint = new Paint();
    paint.setStrokeWidth(3f);
    paint.setAntiAlias(true);
    paint.setColor(Color.argb(255, 222, 92, 143));
    CircleRenderer circleRenderer = new CircleRenderer(paint, true);
    mVisualizerView.addRenderer(circleRenderer);
  }

  private void addLineRenderer()
  {
    Paint linePaint = new Paint();
    linePaint.setStrokeWidth(1f);
    linePaint.setAntiAlias(true);
    linePaint.setColor(Color.argb(88, 0, 128, 255));

    Paint lineFlashPaint = new Paint();
    lineFlashPaint.setStrokeWidth(5f);
    lineFlashPaint.setAntiAlias(true);
    lineFlashPaint.setColor(Color.argb(255, 255, 255, 255));
    LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint, false);
    mVisualizerView.addRenderer(lineRenderer);
  }
  
  private void addImageRenderer() {
	  Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.anim10022);
	  ImageRenderer imageRenderer = new ImageRenderer(mBitmap);
	  mVisualizerView.addRenderer(imageRenderer);
  }
  
  private void addAnimationRenderer() {
	  AnimationDrawable anim = (AnimationDrawable) getResources().getDrawable(R.drawable.png1);

	  //BitmapDrawable anim = (BitmapDrawable) getResources().getDrawable(R.drawable.anim10022);
	  
	  AnimationRenderer animRenderer = new AnimationRenderer(anim);
	  mVisualizerView.addRenderer(animRenderer);
  }

  public void barPressed(View view)
  {
    addBarGraphRenderers();
  }

  public void circlePressed(View view)
  {
    addCircleRenderer();
  }

  public void circleBarPressed(View view)
  {
    addCircleBarRenderer();
  }

  public void linePressed(View view)
  {
    addLineRenderer();
  }

  public void clearPressed(View view)
  {
    mVisualizerView.clearRenderers();
  }
}