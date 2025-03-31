package jokerhut.main;

import entities.NPC;

public class DialogueHandler {

    NPC npc;
    FarmScreen screen;
    public int currentLine;

    private String[] lines = {
        "Hello there!",
        "Nice to see you around the farm.",
        "Come talk to me again later."
    };

    public DialogueHandler (NPC npc, FarmScreen screen) {
        this.npc = npc;
        this.screen = screen;
        this.currentLine = -1;
    }

    public void startDialogue() {
        currentLine = 0;
    }

    public boolean advanceLine() {
        currentLine++;
        return currentLine < lines.length;
    }

    public String getCurrentLine() {
        if (currentLine >= 0 && currentLine < lines.length) {
            return lines[currentLine];
        }
        return "";
    }

}
