package com.example.juanjojg.funwithflags.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.juanjojg.funwithflags.MainActivity;
import com.example.juanjojg.funwithflags.common.Common;

import java.util.List;

public class GridViewSuggestAdapter extends BaseAdapter {

    private List<String> suggestSource;
    private Context context;
    private MainActivity mainActivity;

    public GridViewSuggestAdapter(List<String> suggestSource, Context context, MainActivity mainActivity) {
        this.suggestSource = suggestSource;
        this.context = context;
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return suggestSource.size();
    }

    @Override
    public Object getItem(int i) {
        return suggestSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Button button;
        if (view == null) {
            if (suggestSource.get(i).equals("null")) {
                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(85, 85));
                button.setPadding(8, 8, 8, 8);
                button.setBackgroundColor(Color.DKGRAY);
            } else {
                button = new Button(context);
                button.setLayoutParams(new GridView.LayoutParams(85, 85));
                button.setPadding(8, 8, 8, 8);
                button.setBackgroundColor(Color.DKGRAY);
                button.setTextColor(Color.YELLOW);
                button.setText(suggestSource.get(i));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // If correct answer contains character user selected
                        if (String.valueOf(mainActivity.answer).contains(suggestSource.get(i))) {
                            // Get Char
                            char compare = suggestSource.get(i).charAt(0);

                            for (int i = 0; i < mainActivity.answer.length; i++) {
                                if (compare == mainActivity.answer[i]) {
                                    Common.user_submit_answer[i] = compare;
                                }
                            }

                            // Update user interface
                            GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(Common.user_submit_answer, context);
                            mainActivity.mGridViewAnswer.setAdapter(answerAdapter);

                            // Remove from suggest source
                            mainActivity.mSuggestSource.set(i, "null");
                            mainActivity.mSuggestAdapter = new GridViewSuggestAdapter(mainActivity.mSuggestSource, context, mainActivity);
                            mainActivity.mGridViewSuggest.setAdapter(mainActivity.mSuggestAdapter);
                            mainActivity.mSuggestAdapter.notifyDataSetChanged();
                        } else {
                            // Remove from suggest source
                            mainActivity.mSuggestSource.set(i, "null");
                            mainActivity.mSuggestAdapter = new GridViewSuggestAdapter(mainActivity.mSuggestSource, context, mainActivity);
                            mainActivity.mGridViewSuggest.setAdapter(mainActivity.mSuggestAdapter);
                            mainActivity.mSuggestAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        } else {
            button = (Button) view;
        }
        return button;
    }
}
