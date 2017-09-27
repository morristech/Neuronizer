package de.djuelg.neuronizer.presentation.ui.flexibleadapter;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.djuelg.neuronizer.R;
import de.djuelg.neuronizer.domain.comparator.PreviewCompareable;
import de.djuelg.neuronizer.domain.model.preview.TodoListPreview;
import de.djuelg.neuronizer.domain.model.todolist.TodoListHeader;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by djuelg on 20.07.17.
 */

public class TodoListPreviewViewModel extends AbstractFlexibleItem<TodoListPreviewViewModel.ViewHolder> implements PreviewCompareable {

    private final TodoListPreview preview;

    public TodoListPreviewViewModel(TodoListPreview preview) {
        this.preview = Objects.requireNonNull(preview);
    }

    public TodoListPreview getPreview() {
        return preview;
    }

    public String getTodoListUuid() {
        return preview.getTodoList().getUuid();
    }

    public String getTodoListTitle() {
        return preview.getTodoList().getTitle();
    }

    @Override
    public boolean isSwipeable() {
        return true;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.todo_list_preview;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        return new ViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ViewHolder holder, int position, List payloads) {
        DateFormat date = SimpleDateFormat.getDateInstance();
        DateFormat time = SimpleDateFormat.getTimeInstance(DateFormat.SHORT);

        holder.title.setText(getTitle());
        holder.lastChange.setText(DateUtils.isToday(getChangedAt().getTime())
                ? time.format(getChangedAt())
                : date.format(getChangedAt()));
        holder.header.setText(preview.getHeader().or(TodoListHeader.absent()).toString());
        holder.items.setText(TextUtils.join(", ", preview.getItems()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TodoListPreviewViewModel that = (TodoListPreviewViewModel) o;
        return Objects.equals(preview, that.preview);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preview);
    }

    @Override
    public String toString() {
        return getTodoListTitle();
    }

    @Override
    public long getImportance() {
        return  preview.getTodoList().calculateImportance();
    }

    @Override
    public Date getChangedAt() {
        return  preview.getTodoList().getChangedAt();
    }

    @Override
    public Date getCreatedAt() {
        return preview.getTodoList().getCreatedAt();
    }

    @Override
    public String getTitle() {
        return getTodoListTitle();
    }

    /**
     * Needed ViewHolder
     */
    class ViewHolder extends FlexibleViewHolder {

        @BindView(R.id.front_view) View frontView;
        @BindView(R.id.rear_left_view) View rearLeftView;
        @BindView(R.id.rear_right_view) View rearRightView;
        @BindView(R.id.title_preview) TextView title;
        @BindView(R.id.last_change_preview) TextView lastChange;
        @BindView(R.id.header_preview) TextView header;
        @BindView(R.id.items_preview) TextView items;

        ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            ButterKnife.bind(this, view);
        }

        @Override
        public float getActivationElevation() {
            return 8f;
        }

        @Override
        public View getFrontView() {
            return frontView;
        }

        @Override
        public View getRearLeftView() {
            return rearLeftView;
        }

        @Override
        public View getRearRightView() {
            return rearRightView;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final ViewHolder that = (ViewHolder) o;
            return Objects.equals(title, that.title) &&
                    Objects.equals(lastChange, that.lastChange) &&
                    Objects.equals(header, that.header) &&
                    Objects.equals(items, that.items);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, lastChange, header, items);
        }
    }
}
