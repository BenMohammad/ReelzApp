package com.benmohammad.reelz.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.benmohammad.reelz.R;
import com.benmohammad.reelz.api.ApiHandler;
import com.benmohammad.reelz.api.ApiService;
import com.benmohammad.reelz.api.PostData;
import com.benmohammad.reelz.model.ColorScheme;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public int MYREQUEST = 1;
    EditText codeBox;
    TextView output;
    private ProgressBar progress;
    private ImageView clearOutputBtn;
    private ImageView shareWhatsappBtn;
    private ImageView searchOutputGoogleBtn;
    private RelativeLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.mainLayout);
        output = findViewById(R.id.outputbox);
        progress = findViewById(R.id.progress);
        codeBox = findViewById(R.id.codebox);
        clearOutputBtn = findViewById(R.id.clearOutputBtn);
        clearOutputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(output.getText().toString().length() > 0) {
                    output.setText("");
                    Snackbar.make(view, "Output cleared...", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(view, "Output empty...", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        shareWhatsappBtn = findViewById(R.id.shareWhatsAppBtn);
        shareWhatsappBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOnWhatsApp(view.getContext());
            }
        });
        searchOutputGoogleBtn = findViewById(R.id.searchOutputGoogleBtn);
        searchOutputGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchOnGoogle(view.getContext());
            }
        });
    }

    private void shareOnWhatsApp(Context context){

        if(codeBox.getText().length() > 0 && output.getText().toString().length() > 0) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            String concatString = String.format(codeBox.getText().toString() + "\n" + "--- OUTPUT ---" + "\n" + output.getText().toString());
            if(concatString.length() > 65535) {
                Snackbar.make(output, "WhatsApp messages can on be 65,536 Characters long", Snackbar.LENGTH_SHORT).show();
                return;
            }
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, concatString);
            try {
                context.startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Snackbar snackbar1 = Snackbar.make(output, "Error sending message", Snackbar.LENGTH_SHORT);
                snackbar1.show();
                return;
            }
        }else {
            Snackbar snackbar = Snackbar.make(output, "Run the code to get an output.....", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;
        }
    }

    private void searchOnGoogle(Context context){
        if(output.getText().toString().length() > 0) {
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            String term = output.getText().toString();
            intent.putExtra(SearchManager.QUERY, term);
            context.startActivity(intent);
        } else {
            Snackbar snackbar = Snackbar.make(output, "No output to search Google...", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.runcodebutton:
                if(codeBox.getText().length() > 0) {
                    compileCode();

                } else {
                    Snackbar.make(codeBox, "Editor is empty, No code to run....", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            case R.id.snippetscode:
                openSnippets();
                return true;
            case R.id.delete:
                if(codeBox.getText().length() > 0) {
                    codeBox.getText().clear();
                    Snackbar.make(codeBox, "Cleared", Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(codeBox, "Editor is empty...", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            default:
                return false;

    }
    }

    private void compileCode() {
        ApiService apiService = ApiHandler.getRetroFit();
        Call<String> execute = apiService.execute(new PostData(codeBox.getText().toString()));


        codeBox.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.VISIBLE);
        execute.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.isSuccessful()) {
                        JSONObject responseJson = new JSONObject(response.body().toString());
                        String outputString = responseJson.getString("output");
                        progress.setVisibility(View.GONE);
                        codeBox.setVisibility(View.VISIBLE);
                        output.setText(outputString);
                    } else {
                        progress.setVisibility(View.GONE);
                        codeBox.setVisibility(View.VISIBLE);
                        Snackbar snackbar = Snackbar.make(output, response.errorBody().string(), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                } catch (JSONException je) {
                    progress.setVisibility(View.GONE);
                    codeBox.setVisibility(View.VISIBLE);
                    je.printStackTrace();
                } catch (IOException ie) {
                    progress.setVisibility(View.GONE);
                    codeBox.setVisibility(View.VISIBLE);
                    ie.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                output.setText("Error");

            }
        });
    }


    private void openSnippets(){
        Intent i =  new Intent(this, CodeSnippetsActivity.class);
        startActivityForResult(i, MYREQUEST);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,final  @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MYREQUEST && data != null) {
            if(resultCode == Activity.RESULT_OK) {


                    String snip = data.getStringExtra("snippet");






                codeBox.addTextChangedListener(new TextWatcher() {


                                                    ColorScheme keyWords = new ColorScheme(Pattern.compile("\\b(package|transient|strictfp|void|char|short|int|long|double|float|const|static|volatile|byte|boolean|class|interface|native|private|protected|public|final|abstract|synchronized|enum|instanceof|assert|if|else|switch|case|default|break|goto|return|for|while|do|continue|new|throw|throws|try|catch|finally|this|super|extends|implements|import|true|false|null)\\b"),Color.CYAN);

                                                    ColorScheme numbers = new ColorScheme(
                                                            Pattern.compile("(\\b(\\d*[.]?\\d+)\\b)"),
                                                            Color.YELLOW);

                                                    final ColorScheme[] schemes = {keyWords, numbers};

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        removeSpans(editable, ForegroundColorSpan.class);
                        for(ColorScheme scheme : schemes) {
                            for(Matcher m = scheme.pattern.matcher(editable);
                                m.find();) {
                                editable.setSpan(new ForegroundColorSpan(scheme.color),
                                        m.start(),
                                        m.end(),
                                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


                            }
                        }


                    }
                }
                );



                        Editable editable = codeBox.getText();
                        int start = codeBox.getSelectionStart();
                        editable.insert(start, snip);
                        codeBox.setSelection(start);



                    }
                }
    }




    private void removeSpans(Editable e, Class<? extends CharacterStyle> type) {
        CharacterStyle[] spans = e.getSpans(0, e.length(), type);
        for(CharacterStyle span: spans) {
            e.removeSpan(span);
        }
    }
}