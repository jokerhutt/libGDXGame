package hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Gdx;
import jokerhut.main.FarmScreen;
import objects.GameObject;


//Draw inventory boxes,
//Assign each box to something in the players inventory
//

public class HUD {

    protected Stage stage;
    protected Table inventoryTable;

    protected ImageButton soundToggleButton;
    protected Texture slotTexture;
    protected Texture selectedSlotTexture;
    TextureRegionDrawable selectedSlotDrawable;
    TextureRegionDrawable slotDrawable;
    protected Texture soundIcon;
    FarmScreen screen;
    private BitmapFont font = new BitmapFont();
    Texture dialogueBoxTexture = new Texture("Premade dialog box  big.png");

    public HUD(Viewport viewport, SpriteBatch batch, FarmScreen screen) {
        stage = new Stage(new ScreenViewport(), batch);
        this.screen = screen;
        // Inventory slot background
        slotTexture = new Texture("brownSquare.png");
        selectedSlotTexture = new Texture("brownSquareSelected.png");
        slotDrawable = new TextureRegionDrawable(new TextureRegion(slotTexture));
         selectedSlotDrawable = new TextureRegionDrawable(new TextureRegion(selectedSlotTexture));

        // Inventory Table (Bottom Left)
        inventoryTable = new Table();
        inventoryTable.bottom().left().pad(10);
        inventoryTable.setFillParent(true);


        refreshInventory();

        stage.addActor(inventoryTable);

        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void drawDialogue(String line, SpriteBatch batch) {
        float screenWidth = Gdx.graphics.getWidth();

        float slotSize = 64f;
        float padding = 4f;
        float inventoryWidth = 4 * slotSize + 3 * padding * 2;
        float boxHeight = 2 * slotSize + padding * 2;
        float boxWidth = screenWidth - inventoryWidth - 40;
        float boxX = inventoryWidth + 30;
        float boxY = 10;

        // 1. Draw the dialogue box first (behind the portrait)
        batch.draw(dialogueBoxTexture, boxX, boxY, boxWidth, boxHeight);

        // 2. Draw the portrait on top, slightly more to the right
        float portraitSize = 72f;
        float portraitX = boxX + 30; // shift into the dialogue box slightly
        float portraitY = boxY + boxHeight - portraitSize - 30;

        if (screen.currentNPC != null && screen.currentNPC.portrait != null) {
            batch.draw(screen.currentNPC.portrait, portraitX, portraitY, portraitSize, portraitSize);
        }

        // 3. Draw the dialogue text, shifted right to avoid portrait
        font.getData().setScale(1.2f);
        float textX = portraitX + portraitSize + 35;
        float textY = boxY + boxHeight - 20;
        font.draw(batch, screen.currentNPC.name, textX, textY);

        float lineTextX = textX;
        float lineTextY = textY - 35;

        font.draw(batch, line, lineTextX, lineTextY);
    }

    public void refreshInventory () {
        inventoryTable.clear();
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 4; col++) {
                int index = row * 4 + col;

                GameObject currItem = screen.player.inventory[index];

                Stack slot = new Stack();

                if (screen.player.getSelectedItemIndex() == index) {
                    slot.add(new Image(selectedSlotDrawable));
                } else {
                    slot.add(new Image(slotDrawable));
                }

                if (currItem != null && currItem.sprite != null) {
                    TextureRegionDrawable itemDrawable = new TextureRegionDrawable(new TextureRegionDrawable(currItem.sprite));
                    slot.add(new Image(itemDrawable));
                }

                inventoryTable.add(slot).size(64, 64).pad(4);

            }
            inventoryTable.row();
        }
    }



}
