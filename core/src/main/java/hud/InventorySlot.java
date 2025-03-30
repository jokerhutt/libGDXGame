package hud;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class InventorySlot extends Stack {

    private TextureRegionDrawable background;
    private Image itemImage;
    private TextureRegionDrawable selectedDrawable;
    private boolean isSelected = false;
    private Array<InventorySlot> inventorySlots = new Array<>();
    private int selectedIndex = 0;

    public InventorySlot(TextureRegionDrawable slotDrawable) {
        background = slotDrawable;
        Image slotImage = new Image(background);

        this.add(slotImage);
    }

    public void setItem(TextureRegion itemTexture) {
        if (itemImage != null) this.removeActor(itemImage);

        if (itemTexture != null) {
            itemImage = new Image(itemTexture);
            this.add(itemImage); // Render on top
        }
    }

    public void updateSelection(int newIndex) {
        if (newIndex >= 0 && newIndex < inventorySlots.size) {
            inventorySlots.get(selectedIndex).setSelected(false);
            selectedIndex = newIndex;
            inventorySlots.get(selectedIndex).setSelected(true);
        }
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

}
