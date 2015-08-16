package afdal.shealt.com;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import afdal.shealt.com.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class About extends Activity implements OnClickListener {
	Button contactUs, review, share, otherApps;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

    setContentView(R.layout.about);   
    
    AdView mAdView = (AdView) findViewById(R.id.adView);
    AdRequest adRequest = new AdRequest.Builder().build();
    mAdView.loadAd(adRequest);
    

     contactUs = (Button) findViewById(R.id.btn_contactUs);
    
     share = (Button) findViewById(R.id.btn_share);
     
     
     contactUs.setOnClickListener(this);
   
     share.setOnClickListener(this);
     
}

public void onClick(View buttonPressed) {
	// TODO Auto-generated method stub
	switch (buttonPressed.getId()) {
//==============Share Clicked======================		
	case R.id.btn_share:
		Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "افضل الشيلات");
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://www.dropbox.com/sh/nka7lt7zwcho5jy/AAC5cX6jriI9UFhFHfZav9sla?dl=0");
		startActivity(shareIntent);
		break;
//==============Review Clicked======================		
	
//===============Contact Us Clicked=====================		
	case R.id.btn_contactUs:
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        String[] recipients = new String[]{"magood266@gmail.com"};
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, recipients);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "From Andalos User");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        emailIntent.setType("text/plain");
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Send mail to developer"));
        finish();
		break;
//================Other Apps Clicked====================	
	
	}
}
}
