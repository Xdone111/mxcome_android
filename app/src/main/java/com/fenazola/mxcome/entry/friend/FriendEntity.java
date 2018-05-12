package com.fenazola.mxcome.entry.friend;

/**
 * Created by quan on 2016/5/6.
 */
public class FriendEntity {

    String friendName;
    String word;

    public FriendEntity(String friendName, String word) {
        this.friendName = friendName;
        this.word = word;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "FriendEntity{" +
                "friendName='" + friendName + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
