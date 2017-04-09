package com.example.lcc.mykitchen.entity;

import java.util.List;

/**
 * Created by lcc on 2017/1/19.
 */
public class TypeFoodsFromWeb {
    private String resultcode;
    private String reason;
    private List<FoodResult> result;
    private int error_code;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

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

    public List<FoodResult> getResult() {
        return result;
    }

    public void setResult(List<FoodResult> result) {
        this.result = result;
    }

    public class FoodResult {
        private String parentId;
        private String name;
        private List<Menus> list;

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Menus> getList() {
            return list;
        }

        public void setList(List<Menus> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "FoodResult{" +
                    "parentId='" + parentId + '\'' +
                    ", name='" + name + '\'' +
                    ", list=" + list +
                    '}';
        }
    }
    public class Menus{
        private String id;
        private String name;
        private String parentId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        @Override
        public String toString() {
            return "Menus{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", parentId='" + parentId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TypeFoodsFromWeb{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }
}
