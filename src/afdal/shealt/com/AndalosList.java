package afdal.shealt.com;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import afdal.shealt.com.R;
import afdal.shealt.com.Adapter.AndalosAdapter;
import afdal.shealt.com.Adapter.AndalosAdapterDL;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class AndalosList extends Activity {
	ListView AndalosListView, AndalosListViewDL;
	public static final String KEY_TITLE = "title";
	public static final String KEY_DURATION = "duration";
	public static String lessonName = "Andalos Lesson";
	public static String[] lessonURLs, lessonSizes;
	ArrayList<HashMap<String, String>> AndalosList;
	ArrayList<HashMap<String, String>> AndalosListDL;
	
	public static int notDownloadedYetColor, downloadedColor;
	
	AndalosAdapter andalosAdapter;
	AndalosAdapterDL andalosAdapterDL;

	TabHost th;
	int currentTab, sharedLessonNumber;
	static SharedPreferences downloadedLessons, listenedLessons;
	public static boolean[] andalosFinished;
	// =====For download
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private Button startBtn;
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_siralist);
		
		AdView mAdView = (AdView) findViewById(R.id.adView);
	     AdRequest adRequest = new AdRequest.Builder().build();
	     mAdView.loadAd(adRequest);
	     
	     AdView mAdView1 = (AdView) findViewById(R.id.adVier);
	     AdRequest adRequest1 = new AdRequest.Builder().build();
	     mAdView1.loadAd(adRequest1);
	     

		setupVariablsAndViews();
		prepareTabs();
		prepareAndalosLists();

		AndalosListViewDL.setOnItemClickListener(new OnItemClickListener() {

		
			public void onItemClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
				// TODO Auto-generated method stub
				playAudioFile(position);

			}
		});

		AndalosListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				HashMap<String, String> lessonHash = AndalosList.get(position);
				String lessonName = lessonHash.get(KEY_TITLE);

				Intent playAudioIntent = new Intent(AndalosList.this,
						PlayAudio.class);
				playAudioIntent.putExtra("URL", lessonURLs[position]);
				playAudioIntent.putExtra("LESSON_NAME", lessonName);
				playAudioIntent.putExtra("LIST_POSITION", position);
				playAudioIntent.putExtra("TAB", 2);

				if (andalosFinished[position] == true) {
					playAudioIntent.putExtra("VIDEOWATCHED", true);
				} else {
					playAudioIntent.putExtra("VIDEOWATCHED", false);
				}
				startActivity(playAudioIntent);
			}
		});
	}

	/*
	 * Create each listview row content, then adding it to it's ArrayList.
	 */
	public void createAndalosRow(String name, String duration) {
		// looping through all song nodes <song>
		HashMap<String, String> map = new HashMap<String, String>();
		// adding each child node to HashMap key => value
		map.put(KEY_TITLE, name);
		map.put(KEY_DURATION, duration);

		AndalosList.add(map);

	}

	public void createAndalosDLRow(String name, String duration) {
		// looping through all song nodes <song>
		HashMap<String, String> map = new HashMap<String, String>();
		// adding each child node to HashMap key => value
		map.put(KEY_TITLE, name);
		map.put(KEY_DURATION, duration);

		AndalosListDL.add(map);

	}

	/*
	 * Create listview content then setup it with the Adapter
	 */
	public void prepareAndalosLists() {

		// Andalos series
		
		createAndalosRow("بندر بن عوير -يا حسرتي يا وجودي", "3:25");
		createAndalosRow("عبدالعزيز العليوي-واتسبدي", "2:31");
		createAndalosRow("صالح اليامي-على شحم", "6:21");
		createAndalosRow("حاكم الشيباني-يا عاصفة الحزم", "5:04");
		createAndalosRow("مهنا العتيبي-الا يا هاجسي ", "5:44");
		createAndalosRow("محمد فهد-لبيه يا الغالي ", "4:42");
		createAndalosRow("خالد المري -حي من يقدم ", "6:07");
		createAndalosRow("ماجد العازمي -لكن علي الحرام", "4:08");
		createAndalosRow("خالد الساطوح-حان الوعد", "7:41");
		createAndalosRow("حاكم الشيباني-زيد يا الهاجوس", "4:03");
		createAndalosRow("مشاري بن نافل-حبيبي شرب شاهي بنعناع", "8:00");
		
		

		// Andalos series
		
		createAndalosDLRow("بندر بن عوير -يا حسرتي يا وجودي", "3:25");
		createAndalosDLRow("عبدالعزيز العليوي-واتسبدي", "2:31");
		createAndalosDLRow("صالح اليامي-على شحم", "6:21");
		createAndalosDLRow("حاكم الشيباني-يا عاصفة الحزم", "5:04");
		createAndalosDLRow("مهنا العتيبي-الا يا هاجسي ", "5:44");
		createAndalosDLRow("محمد فهد-لبيه يا الغالي ", "4:42");
		createAndalosDLRow("خالد المري -حي من يقدم ", "6:07");
		createAndalosDLRow("ماجد العازمي -لكن علي الحرام", "4:08");
		createAndalosDLRow("خالد الساطوح-حان الوعد", "7:41");
		createAndalosDLRow("حاكم الشيباني-زيد يا الهاجوس", "4:03");
		createAndalosDLRow("مشاري بن نافل-حبيبي شرب شاهي بنعناع", "8:00");
		
		
		
		

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SharedPreferences tabShared = getSharedPreferences("TAB", 0);
		SharedPreferences.Editor editor = tabShared.edit();
		editor.putInt("CURRENT_TAB", th.getCurrentTab());
		editor.commit();
	}

	/*
	 * method check if mp3 exist 1st then to play audio files directly from sd
	 * card if existed:
	 */
	public void playAudioFile(final int lessonNumber) {
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		File file = new File(
				android.os.Environment.getExternalStorageDirectory(),
				"Andalos/" + getLessonName(lessonNumber) + ".mp3");

		if (file.exists() && isLessonCompletelyDownloaded(lessonNumber) == true) {
			// Do action

			intent.setDataAndType(Uri.fromFile(file), "audio/*");
			startActivity(intent);
		} else {

			new AlertDialog.Builder(this)
					.setTitle("تحميل الشيلة")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setMessage(
							"يبدو أن الشيلة لم يتم تحميلها بعد، هل تريد تحميلها الآن؟")
					.setPositiveButton("نعم",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// continue with delete
									if (isOnline()) {
										startDownload(lessonNumber);
									} else {
										Toast.makeText(
												AndalosList.this,
												"من فضلك تأكد من الاتصال بالانترنت",
												Toast.LENGTH_LONG).show();
									}
								}
							})
					.setNegativeButton("ربما لاحقا",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// do nothing
								}
							}).show();
		}

	}

	// For download task:
	private void startDownload(int lessonNumber) {
		// save this lesson number to variable to check if it's completed
		sharedLessonNumber = lessonNumber;
		// get lesson name to save it on sd card
		lessonName = getLessonName(lessonNumber);
		// start download
		new DownloadFileAsync().execute(lessonURLs[lessonNumber]);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("جاري التحميل...");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			return mProgressDialog;
		default:
			return null;
		}
	}

	class DownloadFileAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(DIALOG_DOWNLOAD_PROGRESS);
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			try {

				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();
				int lenghtOfFile = conexion.getContentLength();


				File cacheDir = new File(
						android.os.Environment.getExternalStorageDirectory(),
						"Andalos");
				if (!cacheDir.exists()) {
					cacheDir.mkdir();

				}

				File f = new File(cacheDir, lessonName + ".mp3");
				InputStream input = new BufferedInputStream(url.openStream());
				FileOutputStream output = new FileOutputStream(f);

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
			}
			return null;

		}

		protected void onProgressUpdate(String... progress) {

			mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
			Toast.makeText(AndalosList.this, "تم التحميل بنجاح",
					Toast.LENGTH_LONG).show();
			// save in sharedpref that this lesson is succefuly been downloaded
			// completetly:
			downloadedLessons = getSharedPreferences("downloadedLessons", 0);
			SharedPreferences.Editor editor = downloadedLessons.edit();
			editor.putBoolean("" + sharedLessonNumber, true);
			editor.commit();
			finish();
			startActivity(getIntent());
		}
	}

	/*
	 * setup variables, making findviewbyids, all here..
	 */
	public void setupVariablsAndViews() {
		AndalosListView = (ListView) findViewById(R.id.lv_andalos);
		AndalosListViewDL = (ListView) findViewById(R.id.lv_andalos_dl);

		AndalosList = new ArrayList<HashMap<String, String>>();
		AndalosListDL = new ArrayList<HashMap<String, String>>();

		downloadedLessons = getSharedPreferences("downloadedLessons", 0);

		
		lessonURLs = new String[11];
		
		lessonURLs[0] =      "http://sheelat.com/uploads/songs/shylt_ya7srty_yawgwdy.mp3";
		lessonURLs[1] =      "http://sheelat.com/uploads/songs/shylt_watsbdy.mp3";
		lessonURLs[2] =      "http://sheelat.com/uploads/songs/shylt_ala_sh7m.mp3";
		lessonURLs[3] =      "http://sheelat.com/uploads/songs/shylt_ya_aasft_al7zm.mp3";
		lessonURLs[4] =      "http://sheelat.com/uploads/songs/shylt_ala_yahagsy.mp3";
		lessonURLs[5] =      "http://sheelat.com/uploads/songs/shylt_lbyh_yalghaly.mp3";
        lessonURLs[6] =      "http://sheelat.com/uploads/songs/shylt_7y_mn_ykdm.mp3";
    	lessonURLs[7] = "http://sheelat.com/uploads/songs/shylt_lkn_aly_al7ram.mp3";
    	lessonURLs[8] = "http://sheelat.com/uploads/songs/shylt_7an_alwad.mp3";
    	lessonURLs[9] = "http://sheelat.com/uploads/songs/shylt_zyd_ya_alhagws.mp3";
    	lessonURLs[10] = "http://sheelat.com/uploads/songs/shylt_7byby_shrb_shahy.mp3";
    	







    			

		/*http://sheelat.com/uploads/songs/shylt_ala_yahagsy.mp3 5:44 مهنا العتبي الا يا هاجسي
		 
		 http://sheelat.com/uploads/songs/shylt_lbyh_yalghaly.mp3 محمد فهد   
		 4:42
		 http://sheelat.com/uploads/songs/shylt_7y_mn_ykdm.mp3
		 خالد المري 
		 
		 6:07
		 
		 حي من يقدم
		
		 
		 
		 
		 //
		
		
		
		
/*		lessonURLs[0] = "http://oringz.com/ringtone/what-friends-are-for/sounds-942-what-friends-are-for/?download";
		lessonURLs[1] = "http://oringz.com/ringtone/what-friends-are-for/sounds-942-what-friends-are-for/?download";
		lessonURLs[2] = "http://oringz.com/ringtone/what-friends-are-for/sounds-942-what-friends-are-for/?download";
		lessonURLs[3] = "http://oringz.com/ringtone/what-friends-are-for/sounds-942-what-friends-are-for/?download";
		lessonURLs[4] = "http://oringz.com/ringtone/what-friends-are-for/sounds-942-what-friends-are-for/?download";
		lessonURLs[5] = "http://oringz.com/ringtone/what-friends-are-for/sounds-942-what-friends-are-for/?download";
		lessonURLs[6] = "http://oringz.com/ringtone/what-friends-are-for/sounds-942-what-friends-are-for/?download";
		lessonURLs[7] = "http://oringz.com/ringtone/what-friends-are-for/sounds-942-what-friends-are-for/?download";
		lessonURLs[8] = "http://oringz.com/ringtone/what-friends-are-for/sounds-942-what-friends-are-for/?download";
		lessonURLs[9] = "http://oringz.com/ringtone/what-friends-are-for/sounds-942-what-friends-are-for/?download";
		lessonURLs[10] = "http://oringz.com/ringtone/what-friends-are-for/sounds-942-what-friends-are-for/?download";
		lessonURLs[11] = "http://oringz.com/ringtone/what-friends-are-for/sounds-942-what-friends-are-for/?download";*/
//
		lessonSizes = new String[11];
		lessonSizes[0] = "4MB";
		lessonSizes[1] = "5MB";
		lessonSizes[2] = "6MB";
		lessonSizes[3] = "4MB";
		lessonSizes[4] = "5MB";
		lessonSizes[5] = "6MB";
		lessonSizes[6] = "6MB";
		lessonSizes[7] = "3MB";
		lessonSizes[8] = "7MB";
		lessonSizes[9] = "8MB";
		lessonSizes[10] = "6MB";
	

		

		andalosFinished = new boolean[11];
		listenedLessons = getSharedPreferences("listened_lessons", 0);
		fillFinishedVideosArray(listenedLessons, "listened_lessons", andalosFinished);
		
		andalosAdapter = new AndalosAdapter(this, AndalosList,
				R.drawable.andalos_lv);
		AndalosListView.setAdapter(andalosAdapter);

		andalosAdapterDL = new AndalosAdapterDL(this, AndalosListDL,
				R.drawable.andalos_lv);
		AndalosListViewDL.setAdapter(andalosAdapterDL);
		
		//setting colors:
		notDownloadedYetColor = getResources().getColor(R.color.notDownloaded);
		downloadedColor = getResources().getColor(R.color.defaultColor);

	}

	/*
	 * prepare tabs
	 */
	public void prepareTabs() {

		th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
		TabSpec specs = th.newTabSpec("tag1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("تحميل الشيلات",
				getResources().getDrawable(android.R.drawable.ic_menu_save));
		th.addTab(specs);

		specs = th.newTabSpec("tag2");
		specs.setContent(R.id.tab2);
		specs.setIndicator("السماع من الإنترنت", getResources()
				.getDrawable(android.R.drawable.ic_media_play));
		th.addTab(specs);

		SharedPreferences tabShared = getSharedPreferences("TAB", 0);
		th.setCurrentTab(tabShared.getInt("CURRENT_TAB", 1));
	}

	/*
	 * method that takes lessonNumber and returns the lesson name
	 */
	public static String getLessonName(int lessonNumber) {
		lessonName = "";
		switch (lessonNumber) {
		case 0:
			lessonName = "يا حسرتي";
			break;
		case 1:
			lessonName = "واتسبدي";
			break;
		case 2:
			lessonName = "على شحم";
			break;
		case 3:
			lessonName = "ايا عاصفة الحزم";
			break;
		case 4:
			lessonName = "الا يا هاجسي";
			break;
		case 5:
			lessonName = "لبيه يا الغالي";
			break;
		case 6:
			lessonName = "حي من يقدم";
			break;
		case 7:
			lessonName = "لكن علي الحرام";
			break;
		case 8:
			lessonName = "حان الوعد";
			break;
		case 9:
			lessonName = "زيد يا الهاجوس";
			break;
		case 10:
			lessonName = "حبيبي شرب شاهي";
			break;
		default:
			break;
		}
		return lessonName;
	}

	public static boolean isFileExist(int lessonNumber) {
		File file = new File(
				android.os.Environment.getExternalStorageDirectory(),
				"Andalos/" + getLessonName(lessonNumber) + ".mp3");
		return file.exists();
	}

	/*
	 * Checks if the device has Internet connection.
	 * 
	 * @return <code>true</code> if the phone is connected to the Internet.
	 */
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	/*
	 * this method check for that lesson number is it's completely downloaded or
	 * not
	 */
	public static boolean isLessonCompletelyDownloaded(int lessonNumber) {
		boolean isCompletelyDownloaded = downloadedLessons.getBoolean(""
				+ lessonNumber, false);

		return isCompletelyDownloaded;
	}
	
	/*
	 * fill finished
	 */
	public void fillFinishedVideosArray(SharedPreferences sharedPref,String prefName, boolean[] array){
	    sharedPref = getSharedPreferences(prefName, 0);
	    for(int i=0; i<array.length; i++){
	        array[i] = sharedPref.getBoolean("Lesson" + i, false);
	    }
	}
@Override
protected void onRestart() {
	// TODO Auto-generated method stub
	super.onRestart();
	fillFinishedVideosArray(listenedLessons, "listened_lessons", andalosFinished);
}

}
