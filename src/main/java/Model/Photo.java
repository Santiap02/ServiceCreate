package Model;

import lombok.Getter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Document(collection = "images")
public class Photo {
    @Id
    private String id;

    @Indexed(unique = true)
    private int clientId;

    private Binary image;

    public void setImage(Binary image) {
        this.image = image;
    }

}