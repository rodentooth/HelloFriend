package com.frozensparks.hellofriend.SignUp;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.frozensparks.hellofriend.R;
import com.frozensparks.hellofriend.your_suggestions.Your_Suggestions;

import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class EditProfile extends Fragment {




    private static final int RESULT_LOAD_IMAGE = 100;



    EditText Years_input;
    TextInputLayout text_input_layout_date;
    EditText UserName_input;
    EditText country_input;
    String country="";
    String username="";
    String name;
    String age="";
    String gender="";
    int user_id;
    String gid;
    String email;
    Button SignUpButton;
    Boolean valid=true;

    ImageView picprod1;
    Button deletepic1;
    ImageView picprod2;
    Button deletepic2;
    ImageView picprod3;
    Button deletepic3;
    ImageView picprod4;
    Button deletepic4;
    String imagePath ="";
    String imagePath1 ="";
    String imagePath2 ="";
    String imagePath3="";
    String imagePath4 ="";
    int picnr;
    Bitmap bitmapResized;
    int serverResponseCode = 0;


    ScrollView scrollView_SignUp;
    int animation=0;
    int animation_slides=0;

    String sc_username;
    RelativeLayout bgtorequestfocus_edit_profile;
    ProgressDialog pd;







    public EditProfile() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);


        pd = new ProgressDialog(getContext());
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.setCancelable(false);
        pd.create();



        bgtorequestfocus_edit_profile = view.findViewById(R.id.bgtorequestfocus_edit_profile);
        bgtorequestfocus_edit_profile.requestFocus();


        final SharedPreferences filter = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        gender = filter.getString("gender", "");
        country = filter.getString("country", "");
        sc_username = filter.getString("sc_username", "");
        age = filter.getString("age", "");
        user_id = (filter.getInt("userid", 0));
        gid = (filter.getString("gid", ""));
        email = (filter.getString("email", ""));


        UserName_input = view.findViewById(R.id.UserName_input_edit_profile);
        UserName_input.setText(sc_username);
        Years_input =  view.findViewById(R.id.Years_input_edit_profile);
        Years_input.setText(String.valueOf(age));
        text_input_layout_date = view.findViewById(R.id.text_input_layout_date_edit_profile);
        text_input_layout_date.setErrorEnabled(true);

        Years_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    showDatePicker();
                }
            }
        });

        final RadioGroup radioGroup1 = view.findViewById(R.id.radioGroup1_edit_profile);

        country_input =  view.findViewById(R.id.country_input_edit_profile);
        country_input.setText(country);

        final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {

                country =name;
                //Toast.makeText(getContext(), code, Toast.LENGTH_SHORT).show();
                country_input.setText(name);
                picker.dismiss();
                bgtorequestfocus_edit_profile.requestFocus();


            }
        });
        country_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                picker.show(getFragmentManager(), "COUNTRY_PICKER");

            }
        });

        SignUpButton=view.findViewById(R.id.UpdateProfile);


        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(valid){

                    if(
                            !username.equals(filter.getString("sc_username", "")) ||
                            !age.equals(filter.getString("age", "")) ||
                            !gender.equals(filter.getString("gender", "")) ||
                            !country.equals(filter.getString("country", ""))||
                            imagePath2.equals("delete")||
                            imagePath3.equals("delete")||
                            imagePath4.equals("delete")||
                            !imagePath1.equals("")||
                            !imagePath2.equals("")||
                            !imagePath3.equals("")||
                            !imagePath4.equals("")) {


                        int pictures_to_delete=0;

                        if (imagePath2.equals("delete")) {
                            pictures_to_delete=+1;
                        }
                        if (imagePath3.equals("delete")) {
                            pictures_to_delete=+1;

                        }
                        if (imagePath4.equals("delete")) {
                            pictures_to_delete=+1;
                        }
                        if(pictures_to_delete!=0){


                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    getContext());

                            // set title
                            alertDialogBuilder.setTitle(R.string.deleteimages);

                            // set dialog message
                            alertDialogBuilder
                                    .setMessage(String.valueOf(pictures_to_delete)+"  "+getString(R.string.pictureswillbedeleted))
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.deleteandupdate, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            AsyncTask_SignIn lol = new AsyncTask_SignIn(getContext());
                                            lol.execute("update_user","");

                                            pd.show();



                                        }
                                    })
                                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });

                            // create alert dialog
                            AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it
                            alertDialog.show();
                        }
                        else{

                            AsyncTask_SignIn lol = new AsyncTask_SignIn(getContext());
                            lol.execute("update_user","");

                            pd.show();


                        }

                    }

                    else{

                        Snackbar snack = Snackbar.make(getView(), R.string.yourprofileisuptodate, Snackbar.LENGTH_LONG);
                        View view2 = snack.getView();
                        TextView tv = (TextView) view2.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setTextColor(Color.WHITE);
                        snack.show();

                        Your_Suggestions nextFrag= new Your_Suggestions();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_main, nextFrag,"")
                                .addToBackStack(null)
                                .commit();
                    }



                }

            }
        });


        picprod1 =  view.findViewById(R.id.picprod1_edit_profile);
        picprod2 =  view.findViewById(R.id.picprod2_edit_profile);
        picprod3 =  view.findViewById(R.id.picprod3_edit_profile);
        picprod4 =  view.findViewById(R.id.picprod4_edit_profile);




        deletepic1 = view.findViewById(R.id.deletepic1_edit_profile);
        deletepic1.setVisibility(View.INVISIBLE);
        deletepic2 = view.findViewById(R.id.deletepic2_edit_profile);
        deletepic2.setVisibility(View.INVISIBLE);
        deletepic3 = view.findViewById(R.id.deletepic3_edit_profile);
        deletepic3.setVisibility(View.INVISIBLE);
        deletepic4 = view.findViewById(R.id.deletepic4_edit_profile);
        deletepic4.setVisibility(View.INVISIBLE);


        user_id = (filter.getInt("userid", 0));


        Log.d("userID",String.valueOf(user_id));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true);

        Glide.with(getContext())
                .load("http://snapchat.frozensparks.com/user/" + String.valueOf(user_id) + "/pictures/1.jpg")
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        deletepic1.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        deletepic1.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .apply(requestOptions)
                .into(picprod1);


        Glide.with(getContext())
                .load("http://snapchat.frozensparks.com/user/" + user_id + "/pictures/2.jpg")
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        deletepic2.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        deletepic2.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .apply(requestOptions)
                .into(picprod2);



        Glide.with(getContext())
                .load("http://snapchat.frozensparks.com/user/" + user_id + "/pictures/3.jpg")
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        deletepic3.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        deletepic3.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .apply(requestOptions)
                .into(picprod3);

        Glide.with(getContext())
                .load("http://snapchat.frozensparks.com/user/" + user_id + "/pictures/4.jpg")
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        deletepic4.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        deletepic4.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .apply(requestOptions)
                .into(picprod4);

        picprod1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpic(1);
            }
        });
        picprod2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpic(2);
            }
        });
        picprod3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpic(3);
            }
        });
        picprod4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectpic(4);
            }
        });

        deletepic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picprod1.setImageResource(R.drawable.ic_tag_faces_black_24dp);
                deletepic1.setVisibility(View.INVISIBLE);

                imagePath1 = "delete";

            }
        });

        deletepic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picprod2.setImageResource(R.drawable.ic_tag_faces_black_24dp);
                deletepic2.setVisibility(View.INVISIBLE);

                imagePath2 = "delete";

            }
        });
        deletepic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picprod3.setImageResource(R.drawable.ic_tag_faces_black_24dp);
                deletepic3.setVisibility(View.INVISIBLE);
                imagePath3 = "delete";


            }
        });
        deletepic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picprod4.setImageResource(R.drawable.ic_tag_faces_black_24dp);
                deletepic4.setVisibility(View.INVISIBLE);

                imagePath4 = "delete";


            }
        });





        final Handler ha2=new Handler();
        ha2.postDelayed(new Runnable() {


            @Override
            public void run() {

            if(pd.isShowing())
                pd.incrementProgressBy(1);




                    ha2.postDelayed(this, 500);
        }
    }, 1000);










    final Handler ha=new Handler();
        ha.postDelayed(new Runnable() {


            @Override
            public void run() {


                username = UserName_input.getText().toString();
                age = Years_input.getText().toString();
                final int idx = radioGroup1.indexOfChild(radioGroup1.findViewById(radioGroup1.getCheckedRadioButtonId()));
                RadioButton r = (RadioButton)  radioGroup1.getChildAt(idx);
                if (r!=null) {
                     gender = String.valueOf(r.getTag());


                }
                final TextView genderdesc_signup = view.findViewById(R.id.genderdesc_edit);

                if(gender.equals("0"))
                    genderdesc_signup.setText(R.string.male);
                if(gender.equals("1"))
                    genderdesc_signup.setText(R.string.female);
                if(gender.equals("2"))
                    genderdesc_signup.setText(R.string.bisexual);

                if(username.equals("") || age.equals("") || gender.equals("") || country.equals("") || imagePath1.equals("delete")) {

                    if (valid) {

                        int colorTo = getResources().getColor(R.color.colorHeartRed);
                        int colorFrom = getResources().getColor(R.color.colorAccent);
                        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                        colorAnimation.setDuration(250); // milliseconds
                        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
                                SignUpButton.setBackgroundColor((int) animator.getAnimatedValue());

                            }

                        });

                        colorAnimation.start();


                        valid = false;
                    }
                }

                    if (!username.equals("") && !age.equals("") && !gender.equals("") && !country.equals("") && !imagePath1.equals("delete")) {

                        if (!valid) {

                            int colorFrom = getResources().getColor(R.color.colorHeartRed);
                            int colorTo = getResources().getColor(R.color.colorAccent);
                            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                            colorAnimation.setDuration(250); // milliseconds
                            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                                @Override
                                public void onAnimationUpdate(ValueAnimator animator) {
                                    SignUpButton.setBackgroundColor((int) animator.getAnimatedValue());

                                }

                            });

                            colorAnimation.start();


                            valid = true;
                        }
                    }




                    ha.postDelayed(this, 10);
            }
        }, 100);



        return view;
    }


    public void selectpic(int i){

        picnr = i;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
            return;
        }

        Intent ia = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(ia, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContext().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();




            if (picnr==1) {
                Glide.with(getContext()).load(new File(picturePath)).into(picprod1);

                deletepic1.setVisibility(View.VISIBLE);
                picprod1.setBackgroundColor(0);
                picprod2.setVisibility(View.VISIBLE);
                imagePath1 =picturePath;
            }
            if (picnr==2) {
                Glide.with(getContext()).load(new File(picturePath)).into(picprod2);
                deletepic2.setVisibility(View.VISIBLE);
                picprod2.setBackgroundColor(0);
                imagePath2 =picturePath;
                picprod3.setVisibility(View.VISIBLE);


            }
            if (picnr==3) {
                Glide.with(getContext()).load(new File(picturePath)).into(picprod3);
                deletepic3.setVisibility(View.VISIBLE);
                picprod3.setBackgroundColor(0);
                imagePath3 =picturePath;
                picprod4.setVisibility(View.VISIBLE);

            }
            if (picnr==4) {
                Glide.with(getContext()).load(new File(picturePath)).into(picprod4);
                deletepic4.setVisibility(View.VISIBLE);
                picprod4.setBackgroundColor(0);
                imagePath4 =picturePath;

            }
        }


        else{

            Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
        }

    }




    public int uploadFile(String sourceFileUri, final int way) {
        final String nr = Integer.toString(way);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                pd.incrementProgressBy(25);
                pd.setMessage((R.string.uploadingPicNr) + nr);

            }
        });


        String upLoadServerUri = "http://snapchat.frozensparks.com/upload_pic.php?user_id=" + user_id +"&nr=" + nr;


//        Toast.makeText(context, "Creating your product", Toast.LENGTH_SHORT).show();


        if (sourceFileUri == null){
            Log.e("uploadFile", "Source File is null §");

            return 0;


        }

        HttpURLConnection conn;
        DataOutputStream dos;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            Log.e("uploadFile", "Source File not exist :" +imagePath);
            return 0;
        }
        else
        {
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(sourceFileUri);

                int resizeint;

                do{
                    bitmap = Bitmap.createScaledBitmap(bitmap,
                            (int) (bitmap.getWidth() * 0.9), (int) (bitmap.getHeight() * 0.9), false);
                    resizeint = bitmap.getRowBytes() * bitmap.getHeight();

                    //Toast.makeText(this, Integer.toString(bitmapResized.getRowBytes() * bitmapResized.getHeight()), Toast.LENGTH_SHORT).show();

                    Log.d("resizeBitmap",Integer.toString(resizeint));
                }
                while (resizeint > 20000000);//20000000

                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(sourceFileUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                bitmap = rotateBitmap(bitmap, orientation);


                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] bitmapdata = outputStream.toByteArray();

                InputStream fileInputStream = new ByteArrayInputStream(bitmapdata);


                // open a URL connection to the Servlet
                //FileInputStream fileInputStream = new FileInputStream(sourceFile);


                URL url = new URL(upLoadServerUri);

                //Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("upfile", sourceFileUri);


                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"upfile\";filename="+ sourceFileUri + "" + lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;


                }


                if(serverResponseCode == 200){


                    final String finalResult = result;
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {

                            //Toast.makeText(product_creator.this, finalResult,Toast.LENGTH_SHORT).show();
                            Log.i("uploadFile", "Server Response is : "+ finalResult + ": " + serverResponseCode);


                            if(way==1){
                                imagePath1="";

                                if(!imagePath2.equals("")) {

                                    new Thread(new Runnable() {
                                        public void run() {
                                            uploadFile(imagePath2, 2);
                                        }
                                    }).start();
                                }

                                else if(!imagePath3.equals("")) {

                                    new Thread(new Runnable() {
                                        public void run() {
                                            uploadFile(imagePath3, 3);
                                        }
                                    }).start();

                                }
                                else if(!imagePath4.equals("")){

                                    new Thread(new Runnable() {
                                        public void run() {
                                            uploadFile(imagePath4, 4);
                                        }
                                    }).start();

                                }
                                else{

                                    finishit();

                                }

                            }
                            if(way==2){
                                imagePath2="";

                                if(!imagePath3.equals("")) {

                                    new Thread(new Runnable() {
                                        public void run() {
                                            uploadFile(imagePath3, 3);
                                        }
                                    }).start();


                                }
                                else if(!imagePath4.equals("")){

                                    new Thread(new Runnable() {
                                        public void run() {
                                            uploadFile(imagePath4, 4);
                                        }
                                    }).start();

                                }
                                else{


                                    finishit();

                                }

                            }

                            if(way==3){
                                imagePath3="";


                                if(!imagePath4.equals("")){

                                    new Thread(new Runnable() {
                                        public void run() {
                                            uploadFile(imagePath4, 4);
                                        }
                                    }).start();

                                }
                                else{
                                    finishit();

                                }

                            }
                            if(way==4){
                                imagePath4="";

                                finishit();


                            }

                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(getContext(), "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {


                e.printStackTrace();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {



                        pd.dismiss();

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                getContext());

                        // set title
                        alertDialogBuilder.setTitle(R.string.thatsuptous);

                        // set dialog message
                        alertDialogBuilder
                                .setMessage(R.string.wewilluploadwheninternentiaavailable)
                                .setCancelable(false)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        finishit();
                                    }
                                });


                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    }
                });



                SharedPreferences.Editor filter = getActivity().getSharedPreferences("imageUpload", MODE_PRIVATE).edit();
                filter.putString("image1", imagePath1);
                filter.putString("image2", imagePath2);
                filter.putString("image3", imagePath3);
                filter.putString("image4", imagePath4);
                filter.apply();

                Log.e("Upload Error:", "Exception : "
                        + e.getMessage(), e);
            }

            return serverResponseCode;

        } // End else block
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public void finishit(){

        if(pd!=null){
            pd.dismiss();
        }

        Snackbar snack = Snackbar.make(getView(), R.string.yourprofileisuptodate, Snackbar.LENGTH_LONG);
        View view2 = snack.getView();
        TextView tv = (TextView) view2.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snack.show();
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("gender", gender);
        editor.putInt("userid", user_id);
        editor.putString("age", age);
        editor.putString("gid", gid);
        editor.putString("country", country);
        editor.putString("sc_username",username);


        editor.apply();

        Your_Suggestions nextFrag= new Your_Suggestions();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, nextFrag,"Your_Suggestions")
                .addToBackStack(null)
                .commit();

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        /*FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);*/
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPause() {
        super.onPause();

    }



    private void showDatePicker() {

        final Dialog d = new Dialog(getContext());
        d.setCancelable(false);
        d.setContentView(R.layout.np_dialog);
        Button b1 = (Button) d.findViewById(R.id.ok_npdialog);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.np_npdialog);
        np.setMaxValue((Calendar.getInstance().get(Calendar.YEAR)));
        np.setMinValue((Calendar.getInstance().get(Calendar.YEAR)-99));
        np.setValue((Calendar.getInstance().get(Calendar.YEAR))-15);
        //np.setWrapSelectorWheel(false);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Calendar.getInstance().get(Calendar.YEAR)-np.getValue())<13){
                    text_input_layout_date.setError(getString(R.string.youmustbeatleast13));
                    Years_input.clearFocus();

                }
                else{
                    Years_input.setText(String.valueOf(np.getValue()));
                    text_input_layout_date.setError(null);
                    // text_input_layout_date.setErrorTextAppearance();

                }

                d.dismiss();
                bgtorequestfocus_edit_profile.requestFocus();

            }
        });


        d.show();


    }






    private class AsyncTask_SignIn extends android.os.AsyncTask<String, Void, String> {

        String doafter ="";
        Context context;
        String type;


        AsyncTask_SignIn(Context activity) {


            context = activity;

        }


        @Override
        protected String doInBackground(String... params) {

            type = params[0];
            doafter = params[1];



            if (type.equals("update_user")) {
                try {
                    String URL = "http://snapchat.frozensparks.com/update_user.php";

                    java.net.URL url = new URL(URL);
                    HttpURLConnection httpurlconn = (HttpURLConnection) url.openConnection();
                    httpurlconn.setRequestMethod("POST");
                    httpurlconn.setDoOutput(true);
                    httpurlconn.setDoInput(true);
                    OutputStream outputStream = httpurlconn.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") +"&"+
                            URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode((age), "UTF-8") +"&"+
                            URLEncoder.encode("country", "UTF-8") + "=" + URLEncoder.encode((country), "UTF-8") +"&"+
                            URLEncoder.encode("gid", "UTF-8") + "=" + URLEncoder.encode((gid), "UTF-8") +"&"+
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode((email), "UTF-8") +"&"+
                            URLEncoder.encode("image2", "UTF-8") + "=" + URLEncoder.encode((imagePath2), "UTF-8") +"&"+
                            URLEncoder.encode("image3", "UTF-8") + "=" + URLEncoder.encode((imagePath3), "UTF-8") +"&"+
                            URLEncoder.encode("image4", "UTF-8") + "=" + URLEncoder.encode((imagePath4), "UTF-8") +"&"+
                            URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode((String.valueOf(user_id)), "UTF-8") +"&"+
                            URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode((username), "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpurlconn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpurlconn.disconnect();

                    return result;


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }



            return null;
        }


        @Override
        protected void onPreExecute() {



        }

        @Override
        protected void onPostExecute(String result) {


            if (result != null) {
                if (Objects.equals(result, "")) {
                    result = "0";
                }
                Log.d("AsyncTask says",result);
            }


            if (type.equals("update_user")) {


                try {
                    user_id = Integer.parseInt(result);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "error:" + e, Toast.LENGTH_SHORT).show();

                }

                if (user_id != 0) {

                    new Thread(new Runnable() {
                        public void run() {

                            if(!imagePath1.equals("")) {

                                new Thread(new Runnable() {
                                    public void run() {
                                        uploadFile(imagePath1, 1);
                                    }
                                }).start();
                            }

                            else if(!imagePath2.equals("")&&!imagePath2.equals("delete")) {

                                new Thread(new Runnable() {
                                    public void run() {
                                        uploadFile(imagePath2, 2);
                                    }
                                }).start();
                            }

                            else if(!imagePath3.equals("")&&!imagePath3.equals("delete")) {

                                new Thread(new Runnable() {
                                    public void run() {
                                        uploadFile(imagePath3, 3);
                                    }
                                }).start();

                            }
                            else if(!imagePath4.equals("")&&!imagePath4.equals("delete")){

                                new Thread(new Runnable() {
                                    public void run() {
                                        uploadFile(imagePath4, 4);
                                    }
                                }).start();

                            }
                            else{

                                finishit();
                            }



                        }
                    }).start();

                }

            }

        }

    }

}
