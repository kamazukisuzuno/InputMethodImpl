package suzuno.sampleinputmethod;

import android.app.Dialog;
import android.content.Context;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suzuno on 13-8-21.
 */
public class InputMethodWindow extends Dialog implements View.OnClickListener{

    private PinyinInputSession mPySession;

    public InputMethodWindow(Context context) {
        super(context);
        initDockWindow();
    }

    public InputMethodWindow(Context context, int theme) {
        super(context, theme);
    }

    protected InputMethodWindow(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setToken(IBinder token) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.token = token;
        getWindow().setAttributes(params);
    }

    public void setmPySession(PinyinInputSession pySession){
        mPySession = pySession;
    }


    private void initDockWindow(){
        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.type = WindowManager.LayoutParams.TYPE_INPUT_METHOD;
        params.setTitle("InputMethod");

        params.gravity = Gravity.BOTTOM;
        params.width = -1;

        getWindow().setAttributes(params);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.a:
                mPySession.onPinyinInput('a');
                break;
            case R.id.b:
                mPySession.onPinyinInput('b');
                break;
            case R.id.c:
                mPySession.onPinyinInput('c');
                break;
            case R.id.d:
                mPySession.onPinyinInput('d');
                break;
            case R.id.e:
                mPySession.onPinyinInput('e');
                break;
            case R.id.f:
                mPySession.onPinyinInput('f');
                break;
            case R.id.g:
                mPySession.onPinyinInput('g');
                break;
            case R.id.h:
                mPySession.onPinyinInput('h');
                break;
            case R.id.i:
                mPySession.onPinyinInput('i');
                break;
            case R.id.j:
                mPySession.onPinyinInput('j');
                break;
            case R.id.k:
                mPySession.onPinyinInput('k');
                break;
            case R.id.l:
                mPySession.onPinyinInput('l');
                break;
            case R.id.m:
                mPySession.onPinyinInput('m');
                break;
            case R.id.n:
                mPySession.onPinyinInput('n');
                break;
            case R.id.o:
                mPySession.onPinyinInput('o');
                break;
            case R.id.p:
                mPySession.onPinyinInput('p');
                break;
            case R.id.q:
                mPySession.onPinyinInput('q');
                break;
            case R.id.r:
                mPySession.onPinyinInput('r');
                break;
            case R.id.s:
                mPySession.onPinyinInput('s');
                break;
            case R.id.t:
                mPySession.onPinyinInput('t');
                break;
            case R.id.u:
                mPySession.onPinyinInput('u');
                break;
            case R.id.v:
                mPySession.onPinyinInput('v');
                break;
            case R.id.w:
                mPySession.onPinyinInput('w');
                break;
            case R.id.x:
                mPySession.onPinyinInput('x');
                break;
            case R.id.y:
                mPySession.onPinyinInput('y');
                break;
            case R.id.z:
                mPySession.onPinyinInput('z');
                break;
            case R.id.space:
                mPySession.onPinyinInput(' ');
                break;
            case R.id.backspace:
                mPySession.onBackspace();
                break;
            case R.id.enter:
                mPySession.onEnterKey();
                break;
        }
    }

    int[] ids = {   R.id.a,
                    R.id.b,
            R.id.c,
            R.id.d,
            R.id.e,
            R.id.f,
            R.id.g,
            R.id.h,
            R.id.i,
            R.id.j,
            R.id.k,
            R.id.l,
            R.id.m,
            R.id.n,
            R.id.o,
            R.id.p,
            R.id.q,
            R.id.r,
            R.id.s,
            R.id.t,
            R.id.u,
            R.id.v,
            R.id.w,
            R.id.x,
            R.id.y,
            R.id.z,
            R.id.space,
            R.id.backspace,
            R.id.enter,

    };

    public void initListener(ViewGroup viewGroup){
        for(int i:ids){
            viewGroup.findViewById(i).setOnClickListener(this);
        }
    }
}
