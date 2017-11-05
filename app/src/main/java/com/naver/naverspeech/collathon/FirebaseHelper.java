package com.naver.naverspeech.collathon;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by sinsu on 2017-11-02.
 */

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved;
    ArrayList<PhraseItem> phraseItems = new ArrayList<>();

    /*
 PASS DATABASE REFRENCE
  */
    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //WRITE IF NOT NULL
    public Boolean save(PhraseItem phraseItem) {
        if (phraseItem == null) {
            saved = false;
        } else {
            try {
                db.child("List").push().setValue(phraseItem);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }
        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot) {
        phraseItems.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            PhraseItem phraseItem = ds.getValue(PhraseItem.class);
            phraseItem.setDataKey(ds.getKey());
            phraseItems.add(phraseItem);
        }
    }

    public void retrieve(final PhraseItemAdapter adapter) {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
                adapter.setPhraseItems(phraseItems);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
                adapter.setPhraseItems(phraseItems);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}