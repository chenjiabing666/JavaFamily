package cn.zifangsky.mqwebsocket.model;

import java.util.Objects;

public class Func {
    private Integer id;

    private String name;

    private String description;

    private String code;

    private String url;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Func func = (Func) o;
        return Objects.equals(id, func.id) &&
                Objects.equals(name, func.name) &&
                Objects.equals(description, func.description) &&
                Objects.equals(code, func.code) &&
                Objects.equals(url, func.url) &&
                Objects.equals(status, func.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, code, url, status);
    }
}