package com.example.lcc.mykitchen.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lcc on 2017/1/17.
 */
public class FoodFromWeb implements Serializable {
    private String resultcode;
    private String reason;
    private mResult result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public mResult getResult() {
        return result;
    }

    public void setResult(mResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "FoodFromWeb{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }

    public class mResult{
        private List<Detials> data;
        private String totalNum;
        private String pn;
        private String rn;

        public List<Detials> getData() {
            return data;
        }

        public void setData(List<Detials> data) {
            this.data = data;
        }

        public String getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }

        public String getPn() {
            return pn;
        }

        public void setPn(String pn) {
            this.pn = pn;
        }

        public String getRn() {
            return rn;
        }

        public void setRn(String rn) {
            this.rn = rn;
        }

        @Override
        public String toString() {
            return "mResult{" +
                    "data=" + data +
                    ", totalNum='" + totalNum + '\'' +
                    ", pn='" + pn + '\'' +
                    ", rn='" + rn + '\'' +
                    '}';
        }
    }
   public class Detials implements Serializable{
       private  String id;
       private String title;
       private String tags;
       private String imtro;
       private String ingredients;
       private String burden;
       private List<String> albums;
       private List<Steps> steps;

       public String getId() {
           return id;
       }

       public void setId(String id) {
           this.id = id;
       }

       public String getTitle() {
           return title;
       }

       public void setTitle(String title) {
           this.title = title;
       }

       public String getTags() {
           return tags;
       }

       public void setTags(String tags) {
           this.tags = tags;
       }

       public String getImtro() {
           return imtro;
       }

       public void setImtro(String imtro) {
           this.imtro = imtro;
       }

       public String getIngredients() {
           return ingredients;
       }

       public void setIngredients(String ingredients) {
           this.ingredients = ingredients;
       }

       public String getBurden() {
           return burden;
       }

       public void setBurden(String burden) {
           this.burden = burden;
       }

       public List<String> getAlbums() {
           return albums;
       }

       public void setAlbums(List<String> albums) {
           this.albums = albums;
       }

       public List<Steps> getSteps() {
           return steps;
       }

       public void setSteps(List<Steps> steps) {
           this.steps = steps;
       }

       @Override
       public String toString() {
           return "Detials{" +
                   "id='" + id + '\'' +
                   ", title='" + title + '\'' +
                   ", tags='" + tags + '\'' +
                   ", imtro='" + imtro + '\'' +
                   ", ingredients='" + ingredients + '\'' +
                   ", burden='" + burden + '\'' +
                   ", albums=" + albums +
                   ", steps=" + steps +
                   '}';
       }
   }
    public class Steps implements Serializable{
        private String img;
        private String step;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        @Override
        public String toString() {
            return "Steps{" +
                    "img='" + img + '\'' +
                    ", step='" + step + '\'' +
                    '}';
        }
    }
}
