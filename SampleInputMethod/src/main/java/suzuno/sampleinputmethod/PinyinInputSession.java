package suzuno.sampleinputmethod;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suzuno on 13-8-21.
 */
public class PinyinInputSession implements View.OnClickListener{

    private InputMethodServiceImpl mService;
    private Resources mResources;
    private InputConnection mInputConnection;
    private TextView mText;
    private LinearLayout mCandidateFrame;
    private LinearLayout mCandidateView;

    private StringBuilder mPinyinText = new StringBuilder();
    private List<String> words = new ArrayList<String>();
    private List<String> dictKeys = new ArrayList<String>();
    private Map<String,List<String>> dict = new HashMap<String, List<String>>();
    private List<Integer> strings = new ArrayList<Integer>();

    public PinyinInputSession(InputMethodServiceImpl service,Resources resources){
        mService = service;
        mResources = resources;
        loadDict();
    }

    public void loadDict(){
        words.add("ren");
        words.add("min");
        words.add("du");
        words.add("shu");
        words.add("lang");
        words.add("mi");
        words.add("min");
        words.add("jie");
        words.add("lei");
        strings.add(R.string.ren);
        strings.add(R.string.min);
        strings.add(R.string.du);
        strings.add(R.string.shu);
        strings.add(R.string.lang);
        strings.add(R.string.mi);
        strings.add(R.string.min2);
        strings.add(R.string.jie);
        strings.add(R.string.lei);
        ArrayList<String> list0 = new ArrayList<String>();
        list0.add(getString(1));
        list0.add(getString(8));
        dict.put(getString(0),list0);
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add(getString(7));
        dict.put(getString(6),list1);
    }

    public String getString(int index){
        return mResources.getString(strings.get(index));
    }

    public void onPinyinInput(char c){

        boolean connected = getInputConnection();
        if(!connected) return;
        mPinyinText.append(c);
        mText.append(String.valueOf(c));
        if(c==' '){
            mInputConnection.commitText(mPinyinText.toString(),0);
            mPinyinText.setLength(0);
            mText.setText("");
        }

        isInDict(mPinyinText.toString());
    }

    public void setInputConnection(InputConnection inputConnection){
        mInputConnection = inputConnection;
    }

    public void setCandidateView(LinearLayout view){
        mCandidateFrame = view;
        mText = (TextView) view.findViewById(R.id.input);
        mCandidateView = (LinearLayout) view.findViewById(R.id.candidate);
    }

    public void onBackspace(){
        boolean connected = getInputConnection();
        if(!connected) return;
        if(mPinyinText.length()==0){
            mInputConnection.deleteSurroundingText(1,0);
        }else{
            mPinyinText.setLength(mPinyinText.length()-1);
            mText.setText(mPinyinText.toString());
        }
    }

    public void onEnterKey(){

    }

    public void isInDict(String str){
        removeAllCandidate();
        for(int i=0;i< words.size();i++){
            String s = words.get(i);
            if(s.equals(str)){
                addCandidate(mResources.getString(strings.get(i)));
            }
        }
    }

    private void removeAllCandidate() {
        mCandidateView.removeAllViews();
    }

    private void addCandidate(String s){
        final Button b = new Button(mCandidateView.getContext());
        b.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        b.setText(s);
        b.setOnClickListener(this);
        mCandidateView.addView(b);
    }

    private boolean getInputConnection(){
        mInputConnection = mService.getInputConnection();
        if(mInputConnection!=null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        commitText(b.getText());
        mCandidateView.removeView(b);
    }

    public void commitText(CharSequence s){
        mInputConnection.commitText(s,0);
        removeAllCandidate();
        List<String> l = dict.get(s);
        if(l!=null){
            for(String string:l){
                addCandidate(string);
            }
        }

    }
}
