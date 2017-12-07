package us.mifeng.anim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //定义6个控件，用来显示周围
    private int[] imgIds = {R.id.chek01, R.id.chek02, R.id.chek03, R.id.chek04, R.id.chek05, R.id.chek06, R.id.chek07, R.id.chek08, R.id.chek09};
    private ImageView[] imgViews = new ImageView[imgIds.length];
    //定义三个控件，用来显示底部的三个控件
    private int[] meiyeIds = {R.id.chek07, R.id.chek08, R.id.chek09};
    private ImageView[] meiyeViews = new ImageView[meiyeIds.length];
    private ImageView hobby;
    private ImageView meiye;
    //定义一个变量，用来标识动画是否已经执行
    private int selectAgin = 0;
    private ImageView startImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        hobby = (ImageView) findViewById(R.id.hobby);
        meiye = (ImageView) findViewById(R.id.meiye);
        startImg = (ImageView) findViewById(R.id.start_select_img);
        startImg.setVisibility(View.GONE);
        final TextView selectAgin = (TextView) findViewById(R.id.selectAgin);
        for (int i = 0; i < imgViews.length; i++) {
            imgViews[i] = (ImageView) findViewById(imgIds[i]);
            imgViews[i].setVisibility(View.GONE);
            imgViews[i].setOnClickListener(new SelectImgListener());
        }
        for (int i = 0; i < meiyeViews.length; i++) {
            meiyeViews[i] = (ImageView) findViewById(meiyeIds[i]);
            meiyeViews[i].setVisibility(View.GONE);
            meiyeViews[i].setOnClickListener(new MeiyeImgListener());
        }
        //重新选择监听
        selectAgin.setOnClickListener(new MyClickListener());
        hobby.setOnClickListener(new MyClickListener());
        meiye.setOnClickListener(new MyClickListener());
        initAnim();
    }

    /**
     * 点击爱好者和美业人的监听事件
     */
    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hobby:
                    collAnim(0);
                    setEnable(false);
                    break;
                case R.id.meiye:
                    collAnim(1);
                    setEnable(false);
                    break;
                case R.id.selectAgin:
                    if (selectAgin > 0) {
                        selectAgin = 0;
                        selectAgain();
                    }
                    break;
            }
        }
    }

    /**
     * 点击小图标的点击事件
     */
    class SelectImgListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int cur = 0;
            switch (v.getId()) {
                case R.id.chek01:
                    cur = 0;
                    break;
                case R.id.chek02:
                    cur = 1;
                    break;
                case R.id.chek03:
                    cur = 2;
                    break;
                case R.id.chek04:
                    cur = 3;
                    break;
                case R.id.chek05:
                    cur = 4;
                    break;
                case R.id.chek06:
                    cur = 5;
                    break;
            }
            if (imgViews[cur].isSelected()) {
                imgViews[cur].setSelected(false);
            } else {
                imgViews[cur].setSelected(true);
            }
        }
    }

    /**
     * 点击美页人下面的三张图片的点击事件
     */
    class MeiyeImgListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.chek07:
                    setMeiyeChildSoundAnim(7);
                    break;
                case R.id.chek08:
                    setMeiyeChildSoundAnim(8);
                    break;
                case R.id.chek09:
                    setMeiyeChildSoundAnim(9);
                    break;
            }
        }
    }
    private void collAnim(final int type) {
        ObjectAnimator left_back_x = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.translation_left_back_x);

        //点击爱好者给煤业人添加透明度变化
        AnimatorSet right_back_x = null;
        if (type == 0) {
            right_back_x = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.translation_right_back_dis_x);
        } else {
            right_back_x = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.translation_right_back_x);
        }
        left_back_x.setTarget(hobby);
        right_back_x.setTarget(meiye);
        right_back_x.start();
        left_back_x.start();
        left_back_x.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //设置已经点击了控件
                selectAgin++;
                //0代表爱好，1代表美业人
                initSelectHobby(type);
                if (type == 0) {
                    meiye.setVisibility(View.GONE);
                    //在动画之前，改变控件上的数据
                    setHobbySoundAnim(0);
                } else {
                    hobby.setVisibility(View.GONE);
                    setMeiyeSoundAnim();
                }
            }
        });
    }
    private void initSelectHobby(int type) {
        setShowImg(false);
        if (type == 0) {
            imgViews[0].setImageDrawable(getResources().getDrawable(R.drawable.hoby_select01));
            imgViews[1].setImageDrawable(getResources().getDrawable(R.drawable.hoby_select02));
            imgViews[2].setImageDrawable(getResources().getDrawable(R.drawable.hoby_select03));
            imgViews[3].setImageDrawable(getResources().getDrawable(R.drawable.hoby_select04));
        } else if (type == 1) {
            imgViews[6].setImageResource(R.mipmap.chuxuezhe);
            imgViews[7].setImageResource(R.mipmap.jingyingzhe);
            imgViews[8].setImageResource(R.mipmap.zaizhizhe);
        }else if (type == 7){//初学者
            imgViews[0].setImageDrawable(getResources().getDrawable(R.drawable.scholar_select01));
            imgViews[1].setImageDrawable(getResources().getDrawable(R.drawable.scholar_select02));
            imgViews[2].setImageDrawable(getResources().getDrawable(R.drawable.scholar_select03));
            imgViews[3].setImageDrawable(getResources().getDrawable(R.drawable.scholar_select04));
            imgViews[4].setImageDrawable(getResources().getDrawable(R.drawable.scholar_select05));
        }else if (type == 8){//精英者
            imgViews[0].setImageDrawable(getResources().getDrawable(R.drawable.elite_select01));
            imgViews[1].setImageDrawable(getResources().getDrawable(R.drawable.elite_select02));
            imgViews[2].setImageDrawable(getResources().getDrawable(R.drawable.elite_select03));
            imgViews[3].setImageDrawable(getResources().getDrawable(R.drawable.elite_select04));
            imgViews[4].setImageDrawable(getResources().getDrawable(R.drawable.elite_select05));
            imgViews[5].setImageDrawable(getResources().getDrawable(R.drawable.elite_select06));
        }else if (type == 9){//在职者
            imgViews[0].setImageDrawable(getResources().getDrawable(R.drawable.injob_select01));
            imgViews[1].setImageDrawable(getResources().getDrawable(R.drawable.injob_select02));
            imgViews[2].setImageDrawable(getResources().getDrawable(R.drawable.injob_select03));
            imgViews[3].setImageDrawable(getResources().getDrawable(R.drawable.injob_select04));
            imgViews[4].setImageDrawable(getResources().getDrawable(R.drawable.injob_select05));
        }
    }

    //设置周围图片的动画

    /**
     *
     * @param postion:代表你点击的位置
     *               0代表：爱好者
     *               7代表：初学者
     *               8代表：经营者
     *               9代表：在职者
     */
    private void setHobbySoundAnim(final int postion) {

        AnimatorSet set01 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.sound_anim01_xy);
        AnimatorSet set02 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.sound_anim02_xy);
        AnimatorSet set03 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.sound_anim03_xy);
        AnimatorSet set04 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.sound_anim04_xy);
        set01.setTarget(setCurShowImg(imgViews[0]));
        set02.setTarget(setCurShowImg(imgViews[1]));
        set03.setTarget(setCurShowImg(imgViews[2]));
        set04.setTarget(setCurShowImg(imgViews[3]));
        if (postion==7){
            AnimatorSet set05 = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.sound_anim05_xy);
            set05.setTarget(setCurShowImg(imgViews[4]));
            set05.start();
        }else if (postion==8){
            AnimatorSet set05 = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.sound_anim05_xy);
            AnimatorSet set06 = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.sound_anim06_xy);
            set05.setTarget(setCurShowImg(imgViews[4]));
            set06.setTarget(setCurShowImg(imgViews[5]));
            set05.start();
            set06.start();
        }else if (postion == 9){
            AnimatorSet set05 = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.sound_anim06_xy);
            set05.setTarget(setCurShowImg(imgViews[4]));
            set05.start();
        }
        set01.start();
        set02.start();
        set03.start();
        set04.start();
        set04.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (postion==0){
                    startImg.setVisibility(View.VISIBLE);
                    startImg.setEnabled(true);
                }else{
                    startImg.setVisibility(View.VISIBLE);
                    startImg.setEnabled(false);
                }
            }
        });
    }

    private void setMeiyeSoundAnim() {
        AnimatorSet set07 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.meiye_anim07_xy);
        AnimatorSet set08 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.meiye_anim08_xy);
        AnimatorSet set09 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.meiye_anim09_xy);
        set07.setTarget(setCurShowImg(imgViews[6]));
        set08.setTarget(setCurShowImg(imgViews[7]));
        set09.setTarget(setCurShowImg(imgViews[8]));
        set07.start();
        set08.start();
        set09.start();
    }
    private void setMeiyeChildSoundAnim(int postion) {
        //设置让所有的控件隐藏。
        initSelectHobby(postion);
        //设置刚开始的两个控件隐藏
        setShow(false);
        //初学者动画，
        AnimatorSet set = null;
        if (postion==7){
            set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.meiye_anim07_xy_back);
            set.setTarget(setCurShowImg(imgViews[6]));
            setHobbySoundAnim(postion);
        }else if (postion==8){
            set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.meiye_anim08_xy_back);
            set.setTarget(setCurShowImg(imgViews[7]));
            setHobbySoundAnim(postion);
        }else if (postion==9){
            set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.meiye_anim09_xy_back);
            set.setTarget(setCurShowImg(imgViews[8]));
            setHobbySoundAnim(postion);
        }
        set.setStartDelay(2200);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //屏蔽三个界面的点击事件
                setChildMeiyeEnable(false);
            }
        });
    }

    private void initAnim() {
        //设置初始动画
        ObjectAnimator left_x = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.translation_left_x);
        AnimatorSet right_x = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.translation_right_x);

        left_x.setTarget(hobby);
        right_x.setTarget(meiye);
        left_x.start();
        right_x.start();
    }

    /**
     * 重新开始加载
     */
    private void selectAgain() {
        setShow(true);
        //所有的控件间隐藏
        setShowImg(false);
        //回到出发点
        initAnim();
    }

    /**
     * 设置控件的隐藏与显示
     */
    private void setShow(boolean flag) {
        hobby.setVisibility(flag ? View.VISIBLE : View.GONE);
        meiye.setVisibility(flag ? View.VISIBLE : View.GONE);
        setEnable(flag);
    }

    private void setShowImg(boolean flag) {
        for (int i = 0; i < imgViews.length; i++) {
            imgViews[i].setVisibility(flag ? View.VISIBLE : View.GONE);
        }
        for(int i=0;i<meiyeViews.length;i++){
            meiyeViews[i].setVisibility(flag ? View.VISIBLE : View.GONE);
        }
    }

    private ImageView setCurShowImg(ImageView view) {
        view.setVisibility(View.VISIBLE);
        return view;
    }

    private void setEnable(boolean flag) {
        hobby.setEnabled(flag);
        meiye.setEnabled(flag);
        setChildMeiyeEnable(true);
    }
    private void setChildMeiyeEnable(boolean flag){
        for(int i=0;i<meiyeViews.length;i++){
            meiyeViews[i].setEnabled(flag);
        }
    }
}
