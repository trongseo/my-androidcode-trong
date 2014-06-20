package fast.email.com;





import fast.email.com.R;
import fast.email.com.HttpFileUpload;
import fast.email.com.TextHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.*;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.Files;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;




public class MainActivity extends Activity
{
	private MainActivity maact ;
	
    // GPSTracker class
    GPSTracker gpsobj;
    int icount=0;
    private int isbreak;
     Button btnRun;
      Button btnMAKH;
      Button btnStop;
      Button btnGui;
     Button btnChupHinh;
     Button btnHide;
     Button btnFile;
    Thread thread;
    TextView txtDiaChi;
    TextView txtPhone;
   TextView lblSDT;
   TextView lblGhiChu;
     EditText txtSDT ;
       EditText txtMAKH ;
     EditText  txtGhiChu;
     AutoCompleteTextView  txtEmail;
    TextView lblStatus;
    ImageView imgView;
    int wait_tamp =1;
    double lat_end =0;
    double lng_end=0;
   double longitude = 0;
    double    latitude = 0;
    int milisecode = 1000;
    int WAIT_SECOND=10*milisecode;
     //  String ippost="113.161.225.12";
    String ippost="rubyshopgovap.com";
    private ArrayAdapter<String> adapter;
     //camere
    int TAKE_PHOTO_CODE = 0;
public static int count=0;
String fullPathPic = "";
private TextView lblPathFile;
EditText txtSubject;
final int ACTIVITY_CHOOSE_FILE = 1;
private Button btnUnAttach;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        setContentView(R.layout.fastemail);
        createShortcut();
        
        
   
         txtSubject = (EditText)findViewById(R.id.txtSubject);
   
        
        btnFile = (Button)findViewById(R.id.btnFile);
       
        txtGhiChu= (EditText)findViewById(R.id.txtGhiChu);
        
        lblPathFile = (TextView)findViewById(R.id.lblPathFile);
        
        btnGui = (Button)findViewById(R.id.btnGui);
        btnUnAttach =  (Button)findViewById(R.id.btnUnAttach);
       // btnStop = (Button)findViewById(R.id.btnStop);
      
lblStatus = (TextView)findViewById(R.id.lblStatus);
createShortcut();
   String fileRead =  TextHelper.readFileAsString(getBaseContext(),  "fastmail.txt");
   // txtEmail.setText(fileRead);
   // StartAppSDK.init(this, "106464757", "206400255");
 // get the defined string-array 
 		//String[] colors = getResources().getStringArray(R.array.colorList);
   String[] listEmail;
  
   txtEmail = (AutoCompleteTextView) findViewById(R.id.txtEmail);
   String emailFile =  TextHelper.readFileAsString(getBaseContext(),  "email.txt");
   if(emailFile!="")		
   {
	   listEmail = emailFile.split(";");
	   adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listEmail);
		// txtEmail  = (EditText)findViewById(R.id.txtEmail);
	 
		// set adapter for the auto complete fields
		 		txtEmail.setAdapter(adapter);
		 		txtEmail.setThreshold(1);
   }
 		
 		
 		// specify the minimum type of characters before drop-down list is shown
 		
   btnUnAttach.setOnClickListener(new View.OnClickListener() {			
		@SuppressWarnings("unchecked")
		@Override
		public void onClick(View v) {
			
			
			fullPathPic  ="";
			lblPathFile.setText("");
			
		}});	
   btnGui.setOnClickListener(new View.OnClickListener() {			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				
				 
                      
                      btnGui.setEnabled(false);
                      String emailse = txtEmail.getText()+"";
                      TextHelper thp = new TextHelper();
                      
                      if (!thp.validateEmail(emailse)) {
                          lblStatus.setText("Email invalid!" );
                          txtEmail.setFocusable(true);
                           btnGui.setEnabled(true);
                          return;
                      }
                      if (txtSubject.getText().toString()=="") {
                          lblStatus.setText("Plz Input subject!" );
                          txtSubject.setFocusable(true);
                           btnGui.setEnabled(true);
                          return;
                      }
                      lblStatus.setText("Sending...!" );
                      fullScalePath = lblPathFile.getText().toString();
                      if(fullScalePath!="")
                          {
                            
                             FileInputStream fstrm;
							try {
								fstrm = new FileInputStream(fullScalePath);
								String ext = "";
								int i = fullScalePath.lastIndexOf('.');
								ext = fullScalePath.substring(i+1);
								
								 String strtitle=txtSubject.getText().toString();
									String strbody=txtGhiChu.getText().toString();
									HttpFileUpload hfu = new HttpFileUpload("http://"+ippost+"/upload_mail.aspx", strtitle,strbody);
									hfu.emailto = txtEmail.getText().toString();
									hfu.iFileName ="uploadfile."+ext;
									hfu.fileExt =ext;
									hfu.Send_Now(fstrm);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								 lblStatus.setText("Error: "+ e.toString() +"!" );
								  btnGui.setEnabled(true);
								return;
							}//fullPathPic
                            
                          }else
                          {
                        	  String strtitle=txtSubject.getText().toString();
								String strbody=txtGhiChu.getText().toString();
								HttpFileUpload hfu;
								try {
									hfu = new HttpFileUpload("http://"+ippost+"/upload_mail.aspx", strtitle,strbody);
									hfu.emailto = txtEmail.getText().toString();
									hfu.iFileName ="";
									hfu.fileExt ="";
									hfu.Send_Now(null);
								} catch (Exception e) {
									 lblStatus.setText("Error: "+ e.toString() +"!" );
									 btnGui.setEnabled(true);
										return;
								}
								
                          }
                      
                      btnGui.setEnabled(true);
                      Date cDate = new Date();
                      String fDate = new SimpleDateFormat("hh:mm:ss").format(cDate);
                      lblStatus.setText("Sent complete at:"+ fDate +"!" );
                      
				//update email.txt
				TextHelper.writeStringAsFile(getBaseContext(), txtEmail.getText().toString(), "email.txt");
				String emailFile =  TextHelper.readFileAsString(getBaseContext(),  "email.txt");
				   if(emailFile!="")		
				   {
					 String []  listEmail = emailFile.split(";");
					   adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_2,listEmail);
						// txtEmail  = (EditText)findViewById(R.id.txtEmail);
						
						// set adapter for the auto complete fields
						 		txtEmail.setAdapter(adapter);
						 		txtEmail.setThreshold(1);
				   }
			}     
		});
   
    btnFile.setOnClickListener(new View.OnClickListener() {			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				 Intent chooseFile;
			        Intent intent;
			        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
			        chooseFile.setType("file/*");
			        intent = Intent.createChooser(chooseFile, "Choose a file");
			        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);   
			}
		});
         

         //Toast.makeText(this, pupu, Toast.LENGTH_SHORT).show();
         // thiet lap ket noi internet,lay dia chi
                    
    }//end init
     
   
    public String postData(String emails,String diachi,String sodt,String latlong,String ghichu,String pathHinh)   {
        try {
           
            HttpClient httpclient = new DefaultHttpClient();
           HttpPost httppost = new HttpPost("http://"+ippost+"/post_cus.aspx"); 
           // HttpPost httppost = new HttpPost("http://laytin.wew.vn/post.aspx"); 
    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
           nameValuePairs.add(new BasicNameValuePair("emails", emails));
           nameValuePairs.add(new BasicNameValuePair("diachi",diachi)); 
            nameValuePairs.add(new BasicNameValuePair("sodt",sodt)); 
             nameValuePairs.add(new BasicNameValuePair("latlong",latlong)); 
              nameValuePairs.add(new BasicNameValuePair("ghichu",ghichu)); 
               nameValuePairs.add(new BasicNameValuePair("pathHinh",pathHinh)); 
            nameValuePairs.add(new BasicNameValuePair("from", "sendmail"));
            
           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
           
        try { 
          HttpResponse response=   httpclient.execute(httppost);
          // lblStatus.setText();
           
              return TextHelper.GetText(response);
               } catch (IOException e) {
               // TODO Auto-generated catch block
                     return "client:fail";
           }
       
        } catch (UnsupportedEncodingException ex) {
             lblStatus.setText("client2:fail");
        }
         return "";
} 
    
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == ACTIVITY_CHOOSE_FILE && resultCode == RESULT_OK) {
    	Uri uri = data.getData();
       fullPathPic = uri.getPath();
       lblPathFile.setText(fullPathPic);
    }
}
String fullScalePath = "";


   // @Override
//public boolean onKeyDown(int keyCode, KeyEvent event) {
//    // if (keyCode == KeyEvent.KEYCODE_BACK) {
//     //preventing default implementation previous to android.os.Build.VERSION_CODES.ECLAIR
//    // return true;
//    // }
//    // return super.onKeyDown(keyCode, event);    
//}

     double[] gpsADD = new double[2];

    
      public String getCurAddress(String valuepost)   {
        try {
           
            HttpClient httpclient = new DefaultHttpClient();//203.201.208.121
           HttpPost httppost = new HttpPost("http://"+ippost+"/post_cus.aspx"); 
           // HttpPost httppost = new HttpPost("http://laytin.wew.vn/post.aspx"); 
    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
           nameValuePairs.add(new BasicNameValuePair("mylocate", valuepost));
            nameValuePairs.add(new BasicNameValuePair("get", "address"));
            nameValuePairs.add(new BasicNameValuePair("MAKH","mkh")); 
           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
           
        try { 
          HttpResponse response=   httpclient.execute(httppost);
          // txtView.setText());
           
              return TextHelper.GetText(response);
               } catch (IOException e) {
               // TODO Auto-generated catch block
                    return "client:fail";
           }
       
        } catch (UnsupportedEncodingException ex) {
           return "clien2t:fail";
        }
        
}
     @SuppressLint("NewApi")
	public String getMyPhoneNumber()
    {
        
       // TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//String uid = tManager.getDeviceId();

       // String timlum = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getSubscriberId();
 //String timlum = "";       
//timlum += uid ;//+ ""+((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getLine1Number();
     
         String timlum = Secure.getString(getApplicationContext().getContentResolver(),
                                                        Secure.ANDROID_ID); 
        return timlum;

    }
     public String postDataSDTIME(String valuepost)   {
        try {
            HttpClient httpclient = new DefaultHttpClient();
           HttpPost httppost = new HttpPost("http://"+ippost+"/postime.aspx"); 
    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
           nameValuePairs.add(new BasicNameValuePair("mylocate", valuepost));
           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
           
        try { 
            HttpResponse response = httpclient.execute(httppost);
           // Toast.makeText(this, TextHelper.GetText(response), Toast.LENGTH_SHORT).show();
       //  txtSDT.setText(TextHelper.GetText(response));
         
         lblSDT.setText(TextHelper.GetText(response));
              return "1";
               } catch (IOException e) {
               // TODO Auto-generated catch block
                   Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
           }
        return "";
        } catch (UnsupportedEncodingException ex) {
           // Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
             Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
         return "";
} 
     public String getData(String valuepost)   {
        try {
           
            return "";
//
//            String textsdt = txtSDT.getText().toString();
//            HttpClient httpclient = new DefaultHttpClient();//203.201.208.121
//           HttpPost httppost = new HttpPost("http://"+ippost+"/post_cus.aspx"); 
//           // HttpPost httppost = new HttpPost("http://laytin.wew.vn/post.aspx"); 
//    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
//           nameValuePairs.add(new BasicNameValuePair("mylocate", valuepost));
//            nameValuePairs.add(new BasicNameValuePair("get", "address"));
//            nameValuePairs.add(new BasicNameValuePair("MAKH",textsdt)); 
//           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//           
//        try { 
//          HttpResponse response=   httpclient.execute(httppost);
//          // lblStatus.setText());
//           
//              return TextHelper.GetText(response);
//               } catch (IOException e) {
//               // TODO Auto-generated catch block
//                    return "client:fail";
//           }
       
        } catch (Exception ex) {
           return "clien2t:fail";
        }
        
}
     
     
    private double[] getGPS() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
 Criteria criteria = new Criteria();
 
 String   provider = lm.getBestProvider(criteria, false);
    Location location = lm.getLastKnownLocation(provider);
        /*
         * Loop over the array backwards, and if you get an accurate location,
         * then break out the loop
         */
        Location l = null;

        for (int i = providers.size() - 1; i >= 0; i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null) {
                break;
            }
        }

        double[] gps = new double[2];
        if (l != null) {
           // gps[0] = l.getLatitude();
           // gps[1] = l.getLongitude();
        }
       if (location != null) {
         //  gps[0] = location.getLatitude();
           // gps[1] = location.getLongitude();
        }
           gpsobj = new GPSTracker(MainActivity.this);
 
                // check if GPS enabled     
                if(gpsobj.canGetLocation()){
                     
                    double latitude1 = gpsobj.getLatitude();
                    double longitude1 = gpsobj.getLongitude();
                     gps[0] = latitude1;
           gps[1] = longitude1;
                    // \n is for new line
                  //  Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude1 + "\nLong: " + longitude1, Toast.LENGTH_LONG).show();    
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gpsobj.showSettingsAlert();
                }     
        return gps;
    }

    Context mContext=MainActivity.this;
       SharedPreferences appPreferences;
       boolean isAppInstalled = false;

       @Override
       public void onResume() {
           super.onResume();
          
       }
 
    private void createShortcut() {
        /**
               * check if application is running first time, only then create shorcut
               */
              appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
              isAppInstalled = appPreferences.getBoolean("isAppInstalled",false);
              if(isAppInstalled==false){
              /**
               * create short code
               */
              Intent shortcutIntent = new Intent(getApplicationContext(),MainActivity.class);
              shortcutIntent.setAction(Intent.ACTION_MAIN);
              Intent intent = new Intent();
              intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
              intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Fast Email");
              intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.myicon));
              intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
              getApplicationContext().sendBroadcast(intent);
              /**
               * Make preference true
               */
              SharedPreferences.Editor editor = appPreferences.edit();
              editor.putBoolean("isAppInstalled", true);
              editor.commit();


    }}
    
}
