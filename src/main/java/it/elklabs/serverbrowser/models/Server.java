package it.elklabs.serverbrowser.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


/**
 * The Server model, both XML and DB model
 */
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

    /**
     * get the server ID
     *
     * @return the ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * get the server name
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the servers name
     *
     * @param name the new server name
     * @return the server object
     */
    public Server setName(String name) {
        this.name = name;
        this.isDirty = true;
        return this;
    }

    /**
     * Gets the server description
     *
     * @return the servers description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the server description
     *
     * @param description the servers description
     * @return the server object
     */
    public Server setDescription(String description) {
        this.description = description;
        this.isDirty = true;
        return this;
    }

    /**
     * Get the latest updated date/time object
     *
     * @return the latest date/time
     */
    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * Is the server modified, but not updated?
     * This method is now useless, I'm leaving it here for possible future use.
     * Removing the extension of DAO object has made the save method this relies on move
     * outside of this class into ServerController
     *
     * @return dirty?
     * @deprecated there is no new use.
     */
    @Deprecated
    public boolean dirty() {
        return this.isDirty;
    }

}
