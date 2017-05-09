package it.elklabs.serverbrowser.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@DatabaseTable(tableName = "servers")
public class Server {

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String UPDATED_FIELD = "updated_at";

    @DatabaseField(generatedId = true)
    private Integer id;
    @XmlElement(name = "name")
    @DatabaseField(canBeNull = false)
    private String name;
    @XmlElement(name = "description")
    @DatabaseField(canBeNull = false)
    private String description;
    @DatabaseField(dataType = DataType.DATE_STRING, version = true, columnName = "updated_at")
    private Date updatedAt;

    private Boolean isDirty;

    public Server() {
    }

    public Server(String name, String description) {
        this.name = name;
        this.description = description;
        this.isDirty = false;
        this.id = null;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Server setName(String name) {
        this.name = name;
        this.isDirty = true;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Server setDescription(String description) {
        this.description = description;
        this.isDirty = true;
        return this;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public boolean dirty() {
        return this.isDirty;
    }

}
