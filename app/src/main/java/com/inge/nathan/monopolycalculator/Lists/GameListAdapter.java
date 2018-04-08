package com.inge.nathan.monopolycalculator.Lists;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.inge.nathan.monopolycalculator.MonopolyObjects.MonopolyGame;
import com.inge.nathan.monopolycalculator.R;
import com.inge.nathan.monopolycalculator.UI.StandingsActivity;
import com.inge.nathan.monopolycalculator.Utilities.MCFileManager;

import java.io.IOException;
import java.util.ArrayList;

public class GameListAdapter extends ArrayAdapter<MonopolyGame> {

    public GameListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public GameListAdapter(Context context, int resource, ArrayList<MonopolyGame> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(final int position, final View convertView, @NonNull ViewGroup parent) {

        View gameListView = convertView;

        if (gameListView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            gameListView = vi.inflate(R.layout.list_row_saved_game, null);
        }

        MonopolyGame game = getItem(position);

        if (game != null) {
            TextView nameView = gameListView.findViewById(R.id.game_name_view);
            TextView dateView = gameListView.findViewById(R.id.game_date_view);

            nameView.setText(game.getName());
            dateView.setText(game.getFormattedDateModified());

            gameListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MonopolyGame.setCurrentGame(game);
                    // Go to standings activity
                    Intent i = new Intent(getContext(), StandingsActivity.class);
                    getContext().startActivity(i);
                }
            });

            View finalGameListView = gameListView;
            GameListAdapter finalGameListAdapter = this;
            gameListView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popup = new PopupMenu(getContext(), finalGameListView);
                    popup.getMenuInflater().inflate(R.menu.menu_game_list_popup, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete_popup_item:
                                    finalGameListAdapter.remove(game);

                                    ArrayList<MonopolyGame> savedGames = new ArrayList<>();
                                    for (int i = 0; i < finalGameListAdapter .getCount(); i++)
                                        savedGames.add(finalGameListAdapter .getItem(i));

                                    try {
                                        MCFileManager.saveGames(getContext(), savedGames);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    return true;

                                default:
                                    return false;
                            }
                        }
                    });

                    popup.show();//showing popup menu
                    return true;
                }
            });//closing the setOnClickListener method
        }

        return gameListView;
    }

}