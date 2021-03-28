package com.example.app.printing.printers;

import com.example.app.printing.representers.FriendsRepresenter;
import com.example.model.Character;

public class FriendsPrinter extends EntityPrinter<Character, FriendsRepresenter> {
    public FriendsPrinter(FriendsRepresenter representer) {
        super(representer);
    }
    public FriendsPrinter(){
        this(new FriendsRepresenter());
    }
}
