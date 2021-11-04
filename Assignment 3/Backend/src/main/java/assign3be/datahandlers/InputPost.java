package assign3be.datahandlers;

import lombok.Data;

@Data
public final class InputPost {
    public String title;
    public String description;
    public String creator;

    public InputPost(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
