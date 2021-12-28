package com.github.mjaroslav.ihategui.model.layout;

import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.model.Layout;
import com.github.mjaroslav.ihategui.model.Orientation;
import com.github.mjaroslav.ihategui.util.JsonHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class LinearLayout extends Layout {
    protected Orientation orientation = Orientation.HORIZONTAL;

    @Override
    public void loadFromJson(JsonObject object) {
        super.loadFromJson(object);
        orientation = Orientation.fromName(JsonHelper.getOrDefault(object, "orientation", orientation.toString()));
    }
}
