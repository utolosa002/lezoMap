package utolotu.lezo.eraikinmapa;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class IrudiaActivity extends Activity{
	private ImageView im;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setIm((ImageView) findViewById(R.id.im));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.irudia);
		}
	public ImageView getIm() {
		return im;
	}
	public void setIm(ImageView im) {
		this.im = im;
	}
}
