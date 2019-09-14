package com.example.zoodemo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ZooData {
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        private String limit;
        private String offset;
        private String count;
        private String sort;
        private List<Results> results;

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getOffset() {
            return offset;
        }

        public void setOffset(String offset) {
            this.offset = offset;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public List<Results> getResults() {
            return results;
        }

        public void setResults(List<Results> results) {
            this.results = results;
        }

        public static class Results implements Parcelable {
            private String E_Pic_URL;
            private String E_Geo;
            private String E_Info;
            private String E_no;
            private String E_Category;
            private String E_Name;
            private String E_Memo;
            private String _id;
            private String E_URL;



            private String F_Name_Ch;
            private String F_Name_En;
            private String F_Pic01_URL;
            private String F_AlsoKnown;
            private String F_Name_Latin;
            private String F_Location;
            private String F_Brief;
            private String F_Feature;
            private String F_Family;
            private String F_Genus;
            @SerializedName("F_Function＆Application")
            private String F_Function;
            private String F_Update;


            protected Results(Parcel in) {
                E_Pic_URL = in.readString();
                E_Geo = in.readString();
                E_Info = in.readString();
                E_no = in.readString();
                E_Category = in.readString();
                E_Name = in.readString();
                E_Memo = in.readString();
                _id = in.readString();
                E_URL = in.readString();

                F_Name_Ch = in.readString();
                F_Pic01_URL = in.readString();
                F_AlsoKnown = in.readString();
                F_Name_En = in.readString();
                F_Brief = in.readString();
                F_Feature = in.readString();
                F_Function = in.readString();
                F_Update = in.readString();
            }

            public static final Creator<Results> CREATOR = new Creator<Results>() {
                @Override
                public Results createFromParcel(Parcel in) {
                    return new Results(in);
                }

                @Override
                public Results[] newArray(int size) {
                    return new Results[size];
                }
            };

            public String getF_Update() {
                return F_Update;
            }

            public void setF_Update(String f_Update) {
                F_Update = f_Update;
            }

            public String getF_Function() {
                return F_Function;
            }

            public void setF_Function(String f_Function) {
                F_Function = f_Function;
            }

            public String getF_Feature() {
                return F_Feature;
            }

            public void setF_Feature(String f_Feature) {
                F_Feature = f_Feature;
            }

            public String getF_Brief() {
                return F_Brief;
            }

            public void setF_Brief(String f_Brief) {
                F_Brief = f_Brief;
            }

            public String getF_Name_En() {
                return F_Name_En;
            }

            public void setF_Name_En(String f_Name_En) {
                F_Name_En = f_Name_En;
            }

            public String getF_AlsoKnown() {
                return F_AlsoKnown;
            }

            public void setF_AlsoKnown(String f_AlsoKnown) {
                F_AlsoKnown = f_AlsoKnown;
            }

            public String getF_Pic01_URL() {
                return F_Pic01_URL;
            }

            public void setF_Pic01_URL(String f_Pic01_URL) {
                F_Pic01_URL = f_Pic01_URL;
            }

            public String getF_Name_Ch() {
                return F_Name_Ch;
            }

            public void setF_Name_Ch(String f_Name_Ch) {
                F_Name_Ch = f_Name_Ch;
            }

            public String getE_Pic_URL() {
                return E_Pic_URL;
            }

            public void setE_Pic_URL(String e_Pic_URL) {
                E_Pic_URL = e_Pic_URL;
            }

            public String getE_Geo() {
                return E_Geo;
            }

            public void setE_Geo(String e_Geo) {
                E_Geo = e_Geo;
            }

            public String getE_Info() {
                return E_Info;
            }

            public void setE_Info(String e_Info) {
                E_Info = e_Info;
            }

            public String getE_no() {
                return E_no;
            }

            public void setE_no(String e_no) {
                E_no = e_no;
            }

            public String getE_Category() {
                return E_Category;
            }

            public void setE_Category(String e_Category) {
                E_Category = e_Category;
            }

            public String getE_Name() {
                return E_Name;
            }

            public void setE_Name(String e_Name) {
                E_Name = e_Name;
            }

            public String getE_Memo() {
                return E_Memo;
            }

            public void setE_Memo(String e_Memo) {
                E_Memo = e_Memo;
            }

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getE_URL() {
                return E_URL;
            }

            public void setE_URL(String e_URL) {
                E_URL = e_URL;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(E_Pic_URL);
                dest.writeString(E_Geo);
                dest.writeString(E_Info);
                dest.writeString(E_no);
                dest.writeString(E_Category);
                dest.writeString(E_Name);
                dest.writeString(E_Memo);
                dest.writeString(_id);
                dest.writeString(E_URL);

                dest.writeString(F_Name_Ch);
                dest.writeString(F_Pic01_URL);
                dest.writeString(F_AlsoKnown);
                dest.writeString(F_Name_En);
                dest.writeString(F_Brief);
                dest.writeString(F_Feature);
                dest.writeString(F_Function);
                dest.writeString(F_Update);
            }
        }
    }
}
