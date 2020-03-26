package com.example.first;

import android.animation.TypeEvaluator;

public class MyStringEvaluator implements TypeEvaluator {
    @Override

        public Object evaluate(float fraction ,Object startValue,Object endValue){
            String startStr=(String) startValue;
            String endStr=(String)endValue;
            String currStr=null;

            if (fraction<=0.1f)
                currStr=startStr;
            else if (fraction<=0.5f)
                currStr=startStr.substring(0,3)+endStr;
            else
                currStr=endStr;
            return currStr;
        }

}
