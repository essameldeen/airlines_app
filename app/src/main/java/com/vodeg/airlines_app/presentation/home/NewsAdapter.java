package com.vodeg.airlines_app.presentation.home;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vodeg.airlines_app.data.model.Airline;
import com.vodeg.airlines_app.presentation.description.DescriptionFragment;

import java.util.ArrayList;


/**
 * Created by Mohamed on 25/6/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	Context context;
	DescriptionFragment fragment;
	ArrayList<News> news = new ArrayList<>();

	public NewsAdapter(Context context, NewsFragment fragment)
	{
		this.context = context;
		this.fragment = fragment;
	}

	public void setData(ArrayList<Airline> items)
	{
		news = new ArrayList<Airline>();
		for (Airline item : items)
		{
			news.add(item);
		}
		notifyDataSetChanged();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
	{
		ViewHolder viewHolder = (ViewHolder) holder;

		News news = getItem(position);
		viewHolder.news.setText(news.title + "\n" + news.category);
		viewHolder.image.setImageDrawable(TextDrawable.builder().beginConfig().useFont(HostActivity.TypoG).textColor(Color.WHITE).fontSize(50).width((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics())).height((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics())).toUpperCase().endConfig().buildRound((position + 1) + "", context.getResources().getColor(R.color.colorPrimary)));
	}

	@Override
	public int getItemCount()
	{
		return news.size();
	}

	public News getItem(int position)
	{
		return news.get(position);
	}

	public void addItem(News newModelData, int position)
	{
		news.add(position, newModelData);
		notifyItemInserted(position);
	}

	public void moveItem(int fromPosition, int toPosition)
	{
		final News doctor = news.remove(fromPosition);
		news.add(toPosition, doctor);
		notifyItemMoved(fromPosition, toPosition);
	}

	public void removeItem(int position)
	{
		news.remove(position);
		notifyItemRemoved(position);
	}

	public void animateTo(List<News> models)
	{
		applyAndAnimateRemovals(models);
		applyAndAnimateAdditions(models);
		applyAndAnimateMovedStrings(models);
	}

	private void applyAndAnimateRemovals(List<News> newModels)
	{
		for (int i = news.size() - 1; i >= 0; i--)
		{
			final News model = news.get(i);
			if (!newModels.contains(model))
			{
				removeItem(i);
			}
		}
	}


	private void applyAndAnimateAdditions(List<News> news)
	{
		for (int i = 0, count = news.size(); i < count; i++)
		{
			final News model = news.get(i);
			if (!this.news.contains(model))
			{
				addItem(model, i);
			}
		}
	}

	public ArrayList<News> filter(List<News> allItems, String query)
	{
		query = query.toLowerCase();
		final ArrayList<News> filteredModelList = new ArrayList<>();
		for (News item : allItems)
		{
			String data = item.title + " " + item.category;
			if (data.toLowerCase().contains(query))
			{
				filteredModelList.add(item);
			}
		}
		return filteredModelList;
	}

	private void applyAndAnimateMovedStrings(List<News> newModels)
	{
		for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--)
		{
			final News model = newModels.get(toPosition);
			final int fromPosition = news.indexOf(model);
			if (fromPosition >= 0 && fromPosition != toPosition)
			{
				moveItem(fromPosition, toPosition);
			}
		}
	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		ImageView image;
		TextView news;

		public ViewHolder(View itemView)
		{
			super(itemView);
			itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
				}
			});
			image = (ImageView) itemView.findViewById(R.id.image);
			news = (TextView) itemView.findViewById(R.id.news);

		}
	}
}
