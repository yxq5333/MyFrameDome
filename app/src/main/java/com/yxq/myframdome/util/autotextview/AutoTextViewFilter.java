package com.yxq.myframdome.util.autotextview;

import android.text.TextUtils;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

class AutoTextViewFilter extends Filter {

	private ArrayList<String> mUnfilteredData=null;
	private DataFilterListener mDataFilterListener=null;
	 AutoTextViewFilter(List<String> data){
		mUnfilteredData=(ArrayList<String>) data;
	}
	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		FilterResults aFilterResults=new FilterResults();
		if (mUnfilteredData == null) {  
           return aFilterResults;  
        }  
		if (constraint == null || constraint.length() == 0) {  
            ArrayList<String> list = mUnfilteredData;
            aFilterResults.values = list;  
            aFilterResults.count = list.size();  
        } else{
			String prefixString = constraint.toString().toLowerCase();
			ArrayList<String> unfilteredValues = mUnfilteredData;
            int count = unfilteredValues.size();  
            ArrayList<String> newValues = new ArrayList<>();
           
            for (int i = 0; i < count; i++) {
				String equit = unfilteredValues.get(i);
                if (equit != null) {
                    if(equit!=null && equit.toLowerCase().contains(prefixString)){
                    	newValues.add(equit);
                    } 
                }  
            }
            aFilterResults.values = newValues;
            aFilterResults.count = newValues.size();
		}
        return aFilterResults;  
	}

	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		if (!TextUtils.isEmpty(constraint)){
			if (results.count > 0) {
				mDataFilterListener.hasFiler(results.values);
			} else {
				mDataFilterListener.noFilter();
			}
		}else {
			mDataFilterListener.setTree();
		}
		

	}
	void setDataFilterListener(DataFilterListener dataFilterListener){
		this.mDataFilterListener=dataFilterListener;
	}
	 interface DataFilterListener{
		 void hasFiler(Object aT);
		 void noFilter();
		 void setTree();
	}

}
