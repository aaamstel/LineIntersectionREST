package LineIntersectionREST;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;


@Entity
@Table(name = "geoj")
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class),@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
public class JsonEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int dataId;

    @Type(type = "jsonb")
    @Column(name = "data", columnDefinition = "jsonb")
    private String data;

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getJsonData() {
        return data;
    }

    public void setJsonData(String data) {
        this.data = data;
    }

}
