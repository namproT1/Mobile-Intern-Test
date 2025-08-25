package com.example.internsearchapp.ui.main;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internsearchapp.R;
import com.example.internsearchapp.data.model.LocationIQPlace;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocationAdapter extends ListAdapter<LocationIQPlace, LocationAdapter.VH> {

    public interface OnItemClick { void onClick(LocationIQPlace item); }

    private final Context context;
    private final OnItemClick onItemClick;
    private String keyword = "";

    public LocationAdapter(Context context, OnItemClick onItemClick) {
        super(DIFF);
        this.context = context;
        this.onItemClick = onItemClick;
    }

    public void submitList(java.util.List<LocationIQPlace> list, String keyword) {
        this.keyword = keyword == null ? "" : keyword;
        super.submitList(list);
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        LocationIQPlace item = getItem(position);
        holder.bind(item, keyword, onItemClick);
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvCoords;
        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCoords = itemView.findViewById(R.id.tvCoords);
        }
        void bind(LocationIQPlace item, String keyword, OnItemClick cb) {
            tvCoords.setText(String.format(Locale.getDefault(),"Lat: %s, Lon: %s", item.getLat(), item.getLon()));
            tvName.setText(highlight(item.getDisplayName(), keyword));
            itemView.setOnClickListener(v -> cb.onClick(item));
        }

        private CharSequence highlight(String text, String keyword) {
            if (text == null || keyword == null || keyword.isEmpty()) return text;
            SpannableString ss = new SpannableString(text);
            try {
                Pattern p = Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(text);
                while (m.find()) {
                    ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } catch (Exception ignored) {}
            return ss;
        }
    }

    private static final DiffUtil.ItemCallback<LocationIQPlace> DIFF = new DiffUtil.ItemCallback<LocationIQPlace>() {
        @Override
        public boolean areItemsTheSame(@NonNull LocationIQPlace oldItem, @NonNull LocationIQPlace newItem) {
            return oldItem.getPlaceId().equals(newItem.getPlaceId());
        }
        @Override
        public boolean areContentsTheSame(@NonNull LocationIQPlace oldItem, @NonNull LocationIQPlace newItem) {
            return oldItem.equals(newItem);
        }
    };
}
