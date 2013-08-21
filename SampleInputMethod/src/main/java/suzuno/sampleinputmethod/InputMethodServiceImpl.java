package suzuno.sampleinputmethod;

import android.graphics.Rect;
import android.inputmethodservice.AbstractInputMethodService;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by suzuno on 13-8-21.
 */
public class InputMethodServiceImpl extends AbstractInputMethodService {

    private IBinder mToken;
    private InputBinding mInputBinding;
    private InputConnection mInputConnection;
    private InputMethodManager mImm;
    private LayoutInflater mInflater;
    private InputMethodWindow mWindow;

    private View mRootView;
    private FrameLayout mCandidateFrame;
    private FrameLayout mSoftkeyFrame;

    private boolean mCandidateShown;
    private boolean mSoftkeyShown;
    private boolean mInputWindowShown;
    private boolean mViewAdded;

    private PinyinInputSession mSession;

    public class InputMethodImpl extends AbstractInputMethodImpl{

        @Override
        public void attachToken(IBinder token) {
            mToken = token;
        }

        @Override
        public void bindInput(InputBinding binding) {
            mInputBinding = binding;
            mInputConnection = binding.getConnection();
        }

        @Override
        public void unbindInput() {
            mInputBinding = null;
        }

        @Override
        public void startInput(InputConnection inputConnection, EditorInfo info) {
            Toast.makeText(InputMethodServiceImpl.this,"start input",Toast.LENGTH_SHORT).show();
            doInput();
        }

        @Override
        public void restartInput(InputConnection inputConnection, EditorInfo attribute) {
            Toast.makeText(InputMethodServiceImpl.this,"restart input",Toast.LENGTH_SHORT).show();
            doInput();
        }

        @Override
        public void showSoftInput(int flags, ResultReceiver resultReceiver) {
            Toast.makeText(InputMethodServiceImpl.this,"show soft input",Toast.LENGTH_SHORT).show();
            showWindow();
        }

        @Override
        public void hideSoftInput(int flags, ResultReceiver resultReceiver) {
            Toast.makeText(InputMethodServiceImpl.this,"hide soft input",Toast.LENGTH_SHORT).show();
            hideWindow();
        }

        @Override
        public void changeInputMethodSubtype(InputMethodSubtype subtype) {

        }
    }

    public class InputMethodSessionImpl extends  AbstractInputMethodSessionImpl{

        @Override
        public void finishInput() {
            hideWindow();
        }

        @Override
        public void updateSelection(int oldSelStart, int oldSelEnd, int newSelStart, int newSelEnd, int candidatesStart, int candidatesEnd) {

        }

        @Override
        public void viewClicked(boolean focusChanged) {

        }

        @Override
        public void updateCursor(Rect newCursor) {

        }

        @Override
        public void displayCompletions(CompletionInfo[] completions) {

        }

        @Override
        public void updateExtractedText(int token, ExtractedText text) {

        }

        @Override
        public void appPrivateCommand(String action, Bundle data) {

        }

        @Override
        public void toggleSoftInput(int showFlags, int hideFlags) {
            toggleWindow();
        }
    }

    @Override
    public void onCreate(){
        mImm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mWindow = new InputMethodWindow(this);
        initViews();
    }

    @Override
    public AbstractInputMethodImpl onCreateInputMethodInterface() {
        return new InputMethodImpl();
    }

    @Override
    public AbstractInputMethodSessionImpl onCreateInputMethodSessionInterface() {
        return new InputMethodSessionImpl();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            hideWindow();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
        return false;
    }

    private void initViews(){
        mRootView = mInflater.inflate(R.layout.input_frame,null);
        mCandidateFrame = (FrameLayout) mRootView.findViewById(R.id.candidateFrame);
        mSoftkeyFrame = (FrameLayout) mRootView.findViewById(R.id.softkeyFrame);
        mCandidateShown = false;
        mSoftkeyShown =false;

        mWindow.setContentView(mRootView);
        mWindow.getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);

        mSession = new PinyinInputSession(this,getResources());
        mWindow.setmPySession(mSession);
    }

    public InputConnection getInputConnection(){
        return mInputConnection;
    }

    private void doInput(){
        addViews();
        if(!mInputWindowShown){
            mImm.showSoftInputFromInputMethod(mToken,0);
            mWindow.show();
            mInputWindowShown = true;
        }
    }

    private void showWindow(){
        addViews();
        if(!mInputWindowShown){
            mWindow.show();
            mInputWindowShown = true;
        }
    }

    private void hideWindow(){
        if(mInputWindowShown){
            mImm.hideSoftInputFromInputMethod(mToken, 0);
            mWindow.hide();
            mInputWindowShown = false;
        }
    }

    private void toggleWindow(){
        addViews();
        if(mInputWindowShown){
            mWindow.hide();
            mInputWindowShown = false;
        }else{
            mWindow.show();
            mInputWindowShown = true;
        }
    }

    private void addViews(){
        mWindow.setToken(mToken);
        if(!mViewAdded){
            View softKeyboard = mInflater.inflate(R.layout.demo_softkey_view,null);
            mSoftkeyFrame.addView(softKeyboard);

            mWindow.initListener((ViewGroup) softKeyboard);

            LinearLayout candidate = (LinearLayout) mInflater.inflate(R.layout.demo_candidate_view,null);
            mCandidateFrame.addView(candidate);

            mSession.setInputConnection(mInputConnection);
            mSession.setCandidateView(candidate);
        }
    }
}

