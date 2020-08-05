package com.yxq.myframdome.api_entity.bean;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by Hollow Goods on 2019-05-28.
 */
public class SearchKeys implements SearchSuggestion {

    private String mKey;
    private boolean mIsHistory = false;
    private String id;

    public SearchKeys(String suggestion) {
        this.mKey = suggestion.toLowerCase();
        this.mIsHistory = true;
    }

    public SearchKeys(String mKey, String id) {
        this.mKey = mKey;
        this.id = id;
    }

    public SearchKeys(Parcel source) {
        this.mKey = source.readString();
        this.mIsHistory = source.readInt() != 0;
        this.id = source.readString();
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getBody() {
        return mKey;
    }

    public static final Creator<SearchKeys> CREATOR = new Creator<SearchKeys>() {
        @Override
        public SearchKeys createFromParcel(Parcel in) {
            return new SearchKeys(in);
        }

        @Override
        public SearchKeys[] newArray(int size) {
            return new SearchKeys[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mKey);
        dest.writeInt(mIsHistory ? 1 : 0);
        dest.writeString(id);
    }
}
