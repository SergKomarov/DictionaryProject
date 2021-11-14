package sys101_rest;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class DictionaryList {

    private UUID id;
    private String code;
    private UUID serviceCode;
    private String name;
    private UUID parent;
    private LocalDate actualDate;

    public DictionaryList() {
    }

    public DictionaryList(UUID id, String code, UUID serviceCode, String name, UUID parent, LocalDate actualDate) {
        this.id = id;
        this.code = code;
        this.serviceCode = serviceCode;
        this.name = name;
        this.parent = parent;
        this.actualDate = actualDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(UUID serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getParent() {
        return parent;
    }

    public void setParent(UUID parent) {
        this.parent = parent;
    }

    public LocalDate getActualDate() {
        return actualDate;
    }

    public void setActualDate(LocalDate actualDate) {
        this.actualDate = actualDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryList that = (DictionaryList) o;
        return id.equals(that.id) &&
                code.equals(that.code) &&
                serviceCode.equals(that.serviceCode) &&
                name.equals(that.name) &&
                parent.equals(that.parent) &&
                actualDate.equals(that.actualDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, serviceCode, name, parent, actualDate);
    }

    @Override
    public String toString() {
        return "com.cis.sys101_rest.DictionaryList{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", serviceCode=" + serviceCode +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", actualDate=" + actualDate +
                '}';
    }

}
