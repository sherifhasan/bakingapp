package example.android.bakingappudacity.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Step;

/**
 * Created by sheri on 11/14/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.MyViewHolder> {

    Listener mListener;

    private List<Step> mSteps;

    public StepAdapter() {
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    public void updateAdapter(List<Step> steps) {
        this.mSteps = steps;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.step_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.setContent(mSteps != null ? mSteps.get(position) : null);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSteps == null ? 0 : mSteps.size();
    }


    public interface Listener {
        void onClick(int pos);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_desc_text_view)
        TextView shortDescNameText = null;
        @BindView(R.id.step_card_view)
        CardView cardView;
        private Step mStep;

        MyViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }

        void setContent(Step step) {
            mStep = step;
            if (mStep.getShortDescription() != null) {
                Log.d("step :", mStep.getShortDescription());
                shortDescNameText.setText(mStep.getShortDescription());
            }

        }
    }
}