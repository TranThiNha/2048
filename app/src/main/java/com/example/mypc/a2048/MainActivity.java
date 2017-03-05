package com.example.mypc.a2048;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    public static Integer NUMBER_OF_CELL = 16;
    List<TextView> rowList;
    List<Integer> valueList;
    int highScore = 2;
    float x1 = 0 ,y1=0,x2=0,y2=0;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.cat);
        rowList = new ArrayList<>();
        valueList = new ArrayList<>();

        TextView view1 = (TextView) findViewById(R.id.row1);
        rowList.add(view1);
        TextView view2 = (TextView) findViewById(R.id.row2);
        rowList.add(view2);
        TextView view3 = (TextView) findViewById(R.id.row3);
        rowList.add(view3);
        TextView view4 = (TextView) findViewById(R.id.row4);
        rowList.add(view4);
        TextView view5 = (TextView) findViewById(R.id.row5);
        rowList.add(view5);
        TextView view6 = (TextView) findViewById(R.id.row6);
        rowList.add(view6);
        TextView view7 = (TextView) findViewById(R.id.row7);
        rowList.add(view7);
        TextView view8 = (TextView) findViewById(R.id.row8);
        rowList.add(view8);
        TextView view9 = (TextView) findViewById(R.id.row9);
        rowList.add(view9);
        TextView view10 = (TextView) findViewById(R.id.row10);
        rowList.add(view10);
        TextView view11 = (TextView) findViewById(R.id.row11);
        rowList.add(view11);
        TextView view12 = (TextView) findViewById(R.id.row12);
        rowList.add(view12);
        TextView view13 = (TextView) findViewById(R.id.row13);
        rowList.add(view13);
        TextView view14 = (TextView) findViewById(R.id.row14);
        rowList.add(view14);
        TextView view15 = (TextView) findViewById(R.id.row15);
        rowList.add(view15);
        TextView view16 = (TextView) findViewById(R.id.row16);
        rowList.add(view16);
        init();
        RandomValue();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Boolean isGameOver = false;

        switch (event.getAction())
        {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN:
            {
                x1 = event.getX();
                y1 = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                x2 = event.getX();
                y2 = event.getY();

                //if left to right sweep event on screen
                if (x2- x1>=120)
                {
                    onEventLeftToRight();
                    RandomValue();

                }

                // if right to left sweep event on screen
                if (x1 - x2>=120)
                {
                    onEventRightToLeft();
                    RandomValue();
                }

                // if UP to Down sweep event on screen
                if (y2 - y1>=120)
                {

                    onEventUpToDown();
                    RandomValue();
                }

                //if Down to UP sweep event on screen
                if (y1 - y2>=120)
                {
                    onEventDownToUp();
                    RandomValue();
                }
                break;
            }

        }

        isGameOver = isGameOver();
        if (isGameOver){
            Toast.makeText(getApplicationContext(),"GAME OVER!",Toast.LENGTH_SHORT).show();
        }

        return super.onTouchEvent(event);
    }


    void onEventRightToLeft(){
        boolean check = false;
        for (int j = 0; j < NUMBER_OF_CELL/4;j++){
            for (int i =0; i < NUMBER_OF_CELL/4 ;i++){
                if (valueList.get(i*4+j)!=0){
                    if (j !=NUMBER_OF_CELL/4-1 )
                    {
                        for (int l = 0; l< NUMBER_OF_CELL/4-j-1;l++){
                            if (isAssocitableRightToLeft(i*4+j,i*4+1+j+l)){
                                valueList.set(i*4+j,valueList.get(i*4+j)+valueList.get(i*4+1+j+l));
                                valueList.set(i*4+j+l+1,0);
                                rowList.get(i*4+j+l+1).setText("");
                                Resources resource = getApplicationContext().getResources();
                                rowList.get(i*4+j+l+1).setBackgroundColor(resource.getColor(R.color.baseColor));
                                rowList.get(i*4+j).setText(String.valueOf(valueList.get(i*4+j)));
                                setColor(rowList.get(i*4+j),valueList.get(i*4+j));
                                if (valueList.get(i*4+j)>highScore)
                                {
                                    highScore =valueList.get(i*4+j);
                                }

                                int checkTemp = 0;

                                for (int k = i*4; k<i*4+j; k++){
                                    if (valueList.get(k)==0){
                                        checkTemp=1;
                                        valueList.set(k,valueList.get(i*4+j)+valueList.get(i*4+1+j+l));
                                        rowList.get(i*4+j).setText("");
                                        rowList.get(i*4+j).setBackgroundColor(resource.getColor(R.color.baseColor));
                                        rowList.get(k).setText(String.valueOf(valueList.get(i*4+j)));
                                        setColor(rowList.get(k),valueList.get(i*4+j));
                                        valueList.set(i*4+j,0);
                                        onAssociateCell(rowList.get(k),valueList.get(i*4+j));

                                        break;
                                    }
                                }

                                if (checkTemp==0){
                                    onAssociateCell(rowList.get(i*4+j),valueList.get(i*4+j));
                                }

                                break;
                            }
                            else check=true;
                        }

                    }
                    else check=true;

                    if (check)
                    {
                        Resources resource = getApplicationContext().getResources();
                        for (int k = i*4;k<i*4+j;k++){
                            if (valueList.get(k)==0){
                                valueList.set(k,valueList.get(i*4+j));
                                rowList.get(i*4+j).setText("");
                                rowList.get(i*4+j).setBackgroundColor(resource.getColor(R.color.baseColor));
                                rowList.get(k).setText(String.valueOf(valueList.get(i*4+j)));
                                setColor(rowList.get(k),valueList.get(i*4+j));
                                valueList.set(i*4+j,0);
                                break;
                            }
                        }

                        check= false;
                    }
                }

            }
        }
    }

    void onEventLeftToRight(){
        boolean check = false;
        for (int j = 0; j < NUMBER_OF_CELL/4;j++)        {
            for (int i =0; i < NUMBER_OF_CELL/4 ;i++){
                if (valueList.get(i*4+3-j)!=0)
                {
                    if (j !=NUMBER_OF_CELL/4-1 )
                    {
                        for (int l = 0; l< NUMBER_OF_CELL/4-j-1;l++){
                            if (isAssocitableLeftToRight(i*4+3-j,i*4+2-j-l)){

                                valueList.set(i*4+3-j,valueList.get(i*4+3-j)+valueList.get(i*4+2-j-l));
                                valueList.set(i*4+2-j-l,0);
                                rowList.get(i*4+2-j-l).setText("");
                                Resources resource = getApplicationContext().getResources();
                                rowList.get(i*4+2-j-l).setBackgroundColor(resource.getColor(R.color.baseColor));
                                rowList.get(i*4+3-j).setText(String.valueOf(valueList.get(i*4+3-j)));
                                setColor(rowList.get(i*4+3-j),valueList.get(i*4+3-j));

                                if (valueList.get(i*4+3-j)>highScore)
                                {
                                    highScore=valueList.get(i*4+3-j);
                                }

                                int checkTemp = 0;

                                for (int k = i*4+3;k>i*4+3-j;k--){
                                    if (valueList.get(k)==0){
                                        checkTemp=1;
                                        valueList.set(k,valueList.get(i*4+3-j)+valueList.get(i*4+2-j-l));
                                        rowList.get(i*4+3-j).setText("");
                                        rowList.get(i*4+3-j).setBackgroundColor(resource.getColor(R.color.baseColor));
                                        rowList.get(k).setText(String.valueOf(valueList.get(i*4+3-j)));
                                        setColor(rowList.get(k),valueList.get(i*4+3-j));
                                        valueList.set(i*4+3-j,0);
                                        onAssociateCell(rowList.get(k),valueList.get(i*4+3-j));
                                        break;
                                    }
                                }
                                if (checkTemp==0){
                                    onAssociateCell(rowList.get(i*4+3-j),valueList.get(i*4+3-j));
                                }
                                break;
                            }
                            else {
                                check = true;
                            }
                        }}
                    else check=true;
                    if (check)
                    {
                        Resources resource = getApplicationContext().getResources();
                        for (int k = i*4+3;k>i*4+3-j;k--){
                            if (valueList.get(k)==0){
                                valueList.set(k,valueList.get(i*4+3-j));
                                rowList.get(i*4+3-j).setText("");
                                rowList.get(i*4+3-j).setBackgroundColor(resource.getColor(R.color.baseColor));
                                rowList.get(k).setText(String.valueOf(valueList.get(i*4+3-j)));
                                setColor(rowList.get(k),valueList.get(i*4+3-j));
                                valueList.set(i*4+3-j,0);
                                break;
                            }
                        }

                        check= false;
                    }
                }
            }
        }

    }

    void onEventDownToUp(){
        boolean check = false;
        for (int j = 0; j < NUMBER_OF_CELL/4;j++)        {
            for (int i =0; i < NUMBER_OF_CELL/4 ;i++){
                if (valueList.get(i+4*j)!=0)
                {
                    int temp = valueList.get(i+4*j);
                    if (j !=NUMBER_OF_CELL/4-1 )
                    {
                        for (int l = 0; l< NUMBER_OF_CELL/4-j-1;l++){
                            if (isAssocitableDownToUp(i+4*j,i+4*(j+1))){
                                valueList.set(i+4*j,valueList.get(i+4*j)+valueList.get(i+4*(1+j)));
                                valueList.set(i+4*(1+j),0);
                                rowList.get(i+4*(1+j)).setText("");
                                Resources resource = getApplicationContext().getResources();
                                rowList.get(i+4*(1+j)).setBackgroundColor(resource.getColor(R.color.baseColor));
                                rowList.get(i+4*j).setText(String.valueOf(valueList.get(i+4*j)));
                                rowList.get(i+4*j).setBackgroundColor(resource.getColor(R.color.colorRow2));

                                setColor(rowList.get(i+4*j),valueList.get(i+4*j));

                                if (valueList.get(i+4*j)>highScore){
                                    highScore = valueList.get(i+4*j);
                                }

                                int checkTemp = 0;

                                for (int k = i;k<i+4*j;k+=4){
                                    if (valueList.get(k)==0){
                                        checkTemp=1;
                                        valueList.set(k,valueList.get(i+4*j));
                                        rowList.get(i+4*j).setText("");
                                        rowList.get(i+4*j).setBackgroundColor(resource.getColor(R.color.baseColor));
                                        rowList.get(k).setText(String.valueOf(valueList.get(i+4*j)));
                                        setColor(rowList.get(k),valueList.get(i+4*j));
                                        valueList.set(i+4*j,0);
                                        onAssociateCell(rowList.get(k),valueList.get(i+4*j));
                                        break;
                                    }
                                }

                                if (checkTemp==0) onAssociateCell(rowList.get(i+4*j),valueList.get(i+4*j));
                                break;
                            }
                            else {
                                check = true;
                            }
                        }}
                    else check=true;
                    if (check)
                    {
                        Resources resource = getApplicationContext().getResources();
                        for (int k = i;k<i+4*j;k+=4){
                            if (valueList.get(k)==0){
                                valueList.set(k,valueList.get(i+4*j));
                                rowList.get(i+4*j).setText("");
                                rowList.get(i+4*j).setBackgroundColor(resource.getColor(R.color.baseColor));
                                rowList.get(k).setText(String.valueOf(valueList.get(i+4*j)));
                                setColor(rowList.get(k),valueList.get(i+4*j));
                                valueList.set(i+4*j,0);
                                break;
                            }
                        }

                        check= false;
                    }
                }
            }
        }

    }

    void onEventUpToDown(){
        boolean check = false;
        for (int j = 0; j < NUMBER_OF_CELL/4;j++)        {
            for (int i =0; i < NUMBER_OF_CELL/4 ;i++){
                if (valueList.get(i+4*(3-j))!=0)
                {
                    if (j !=NUMBER_OF_CELL/4-1 )
                    {
                        for (int l = 0; l< NUMBER_OF_CELL/4-j-1;l++){
                            if (isAssocitableUpToDown(i+4*(3-j),i+4*(2-j-l))){
                                valueList.set(i+4*(3-j),valueList.get(i+4*(3-j))+valueList.get(i+4*(2-j-l)));
                                valueList.set(i+4*(2-j-l),0);
                                rowList.get(i+4*(2-j-l)).setText("");
                                Resources resource = getApplicationContext().getResources();
                                rowList.get(i+4*(2-j-l)).setBackgroundColor(resource.getColor(R.color.baseColor));
                                rowList.get(i+4*(3-j)).setText(String.valueOf(valueList.get(i+4*(3-j))));
                                setColor(rowList.get(i+4*(3-j)),valueList.get(i+4*(3-j)));

                                if (valueList.get(i+4*(3-j))>highScore)
                                {
                                    highScore=valueList.get(i+4*(3-j));
                                }

                                int checkTemp = 0;

                                for (int k = i+ NUMBER_OF_CELL-4;k>i+4*(3-j);k-=4){
                                    if (valueList.get(k)==0){
                                        checkTemp =1;
                                        valueList.set(k,valueList.get(i+4*(3-j)));
                                        rowList.get(i+4*(3-j)).setText("");
                                        rowList.get(i+4*(3-j)).setBackgroundColor(resource.getColor(R.color.baseColor));
                                        rowList.get(k).setText(String.valueOf(valueList.get(i+4*(3-j))));
                                        setColor(rowList.get(k),valueList.get(i+4*(3-j)));
                                        valueList.set(i+4*(3-j),0);
                                        onAssociateCell(rowList.get(k),valueList.get(i+4*(3-j)));
                                        break;
                                    }
                                }
                                if (checkTemp==0) onAssociateCell(rowList.get(i+4*(3-j)),valueList.get(i+4*(3-j)));

                                break;
                            }
                            else {
                                check = true;
                            }
                        }}
                    else check=true;
                    if (check)
                    {
                        Resources resource = getApplicationContext().getResources();
                        for (int k = i+ NUMBER_OF_CELL - 4;k>i+4*(3-j);k-=4){
                            if (valueList.get(k)==0){
                                valueList.set(k,valueList.get(i+4*(3-j)));
                                rowList.get(i+4*(3-j)).setText("");
                                rowList.get(i+4*(3-j)).setBackgroundColor(resource.getColor(R.color.baseColor));
                                rowList.get(k).setText(String.valueOf(valueList.get(i+4*(3-j))));
                                setColor(rowList.get(k),valueList.get(i+4*(3-j)));
                                valueList.set(i+4*(3-j),0);
                                break;
                            }
                        }

                        check= false;
                    }
                }
            }
        }

    }


    boolean isAssocitableLeftToRight(int pos1, int pos2){
        if (valueList.get(pos1).equals(valueList.get(pos2))){
            for (int i = pos2+1;i<pos1;i++){
                if (valueList.get(i)!=0){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    boolean isAssocitableDownToUp(int pos1, int pos2){
        if (valueList.get(pos1).equals(valueList.get(pos2))){
            for (int i = pos1+4;i<pos2;i+=4){
                if (valueList.get(i)!=0){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    boolean isAssocitableUpToDown(int pos1, int pos2){
        if (valueList.get(pos1).equals(valueList.get(pos2))){
            for (int i = pos2+4;i<pos1;i+=4){
                if (valueList.get(i)!=0){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    boolean isAssocitableRightToLeft(int pos1, int pos2){
        if (valueList.get(pos1).equals(valueList.get(pos2))){
            for (int i = pos1+1;i<pos2;i++){
                if (valueList.get(i)!=0){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    void init(){
        for (int  i= 0 ; i < NUMBER_OF_CELL ; i++){
            valueList.add(0);
        }
    }


    Boolean CheckTheLastPosition(){
        int count = 0;
        for (int  i = 0 ; i < NUMBER_OF_CELL; i++){
            if (valueList.get(i)==0){
                count++;
            }
        }
        if (count == 1)
            return true;
        return false;
    }


    Boolean isFull(){
        int count = 0;
        for (int  i = 0 ; i < NUMBER_OF_CELL; i++){
            if (valueList.get(i)==0){
                count++;
            }
        }
        if (count == 0)
            return true;
        return false;
    }

    Boolean isGameOver(){

        if (isFull()) {
            for (int j = 0; j < NUMBER_OF_CELL / 4 - 1; j++) {
                for (int i = 0; i < NUMBER_OF_CELL / 4; i++) {
                    for (int l = 0; l < NUMBER_OF_CELL / 4 - j - 1; l++) {
                        if (isAssocitableLeftToRight(i * 4 + 3 - j, i * 4 + 2 - j - l)) {
                            return false;
                        }
                    }
                }
            }

            for (int j = 0; j < NUMBER_OF_CELL / 4; j++) {
                for (int i = 0; i < NUMBER_OF_CELL / 4; i++) {
                    for (int l = 0; l < NUMBER_OF_CELL / 4 - j - 1; l++) {
                        if (isAssocitableUpToDown(i + 4 * (3 - j), i + 4 * (2 - j - l))) {
                            return false;
                        }
                    }
                }
            }
        }
        else return false;
        return true;
    }

    Boolean RandomValue(){
        Random r = new Random();
        int value = 2 + (r.nextInt(2)*2);
        int position = 0;
        if (isFull())return true;

        if (CheckTheLastPosition()){
            for (int  i = 0 ; i < NUMBER_OF_CELL;i++){
                if (valueList.get(i)==0)
                    position = i;
            }

        }
        else {
        position = (r.nextInt(NUMBER_OF_CELL));
            while (valueList.get(position)!=0){
                position = (r.nextInt(NUMBER_OF_CELL));
            }
        }
        valueList.set(position,value);

        rowList.get(position).setText(String.valueOf(value));
        Resources resource = getApplicationContext().getResources();
        if (value==2)
        rowList.get(position).setBackgroundColor(resource.getColor(R.color.colorRow2));
        else
            rowList.get(position).setBackgroundColor(resource.getColor(R.color.colorRow4));

        return false;
    }


    void onAssociateCell(TextView textView, int sum){
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_cell);
        textView.setAnimation(anim);
        if (sum==64||sum==128 || sum==256|| sum==512 || sum == 1024 || sum == 2048){
            mediaPlayer.start();
        }
    }

    void setColor(TextView textView, int sum){

        Resources resource = getApplicationContext().getResources();
        if (sum==2)textView.setBackgroundColor(resource.getColor(R.color.colorRow2));
        if (sum==4)textView.setBackgroundColor(resource.getColor(R.color.colorRow4));
        if (sum==8)textView.setBackgroundColor(resource.getColor(R.color.colorRow8));
        if (sum==16)textView.setBackgroundColor(resource.getColor(R.color.colorRow16));
        if (sum==32)textView.setBackgroundColor(resource.getColor(R.color.colorRow32));
        if (sum==64)textView.setBackgroundColor(resource.getColor(R.color.colorRow64));
        if (sum==128)textView.setBackgroundColor(resource.getColor(R.color.colorRow128));
        if (sum==256)textView.setBackgroundColor(resource.getColor(R.color.colorRow256));
        if (sum==512)textView.setBackgroundColor(resource.getColor(R.color.colorRow512));
        if (sum==1024)textView.setBackgroundColor(resource.getColor(R.color.colorRow1024));
        if (sum==2048)textView.setBackgroundColor(resource.getColor(R.color.colorRow2048));


    }

}

