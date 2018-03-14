package com.app.concertbud.concertbuddies.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.Adapters.TinderCardsAdapter;
import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.Networking.Responses.UserProfileResponse;
import com.app.concertbud.concertbuddies.R;
import com.app.concertbud.concertbuddies.ViewHolders.TinderCardViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yuqirong.cardswipelayout.CardConfig;
import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
import me.yuqirong.cardswipelayout.CardLayoutManager;
import me.yuqirong.cardswipelayout.OnSwipeListener;

public class FindMatchActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mCardRecycler;

    private List<UserProfileResponse> userProfileResponses;

    private Unbinder unbinder;
    private TinderCardsAdapter tinderCardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_match);

        userProfileResponses = new ArrayList<>();
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());
        userProfileResponses.add(new UserProfileResponse());

        unbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tinderCardsAdapter = new TinderCardsAdapter(userProfileResponses);

        mCardRecycler.setItemAnimator(new DefaultItemAnimator());
        mCardRecycler.setAdapter(tinderCardsAdapter);
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(mCardRecycler.getAdapter(), userProfileResponses);
        cardCallback.setOnSwipedListener(new OnSwipeListener<UserProfileResponse>() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                TinderCardViewHolder cardViewHolder = (TinderCardViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
                if (direction == CardConfig.SWIPING_LEFT) {
                    // About to dislike
                } else if (direction == CardConfig.SWIPING_RIGHT) {
                    // about to like
                } else {
                    // Normal state
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, UserProfileResponse userProfileResponse, int direction) {
                TinderCardViewHolder cardViewHolder = (TinderCardViewHolder) viewHolder;
                viewHolder.itemView.setAlpha(1f);

                // Refresh View after swiped

                Toast.makeText(FindMatchActivity.this, direction == CardConfig.SWIPED_LEFT ? "swiped left" : "swiped right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipedClear() {
                Toast.makeText(FindMatchActivity.this, "data clear", Toast.LENGTH_SHORT).show();
                mCardRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Clear data again
                        tinderCardsAdapter.notifyDataSetChanged();
                    }
                }, 3000L);
            }
        });

        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(mCardRecycler, touchHelper);
        mCardRecycler.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(mCardRecycler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
