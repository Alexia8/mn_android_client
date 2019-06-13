package com.alexiacdura.mn_ui.ui.adapters.any;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alexiacdura.mn_ui.core.utils.StyleUtils;
import com.alexiacdura.mn_ui.ui.adapters.BindingViewHolder;

/**
 * Created by nwelander on 20/09/2016.
 */

public interface AnyItem {

    /**
     * Getter to give the BindingGenerator to the {@link AnyAdapter}
     *
     * @return This implementation would need to create {@link BindingGenerator} and return it here.
     * Simplest is to use "new {@link BindingGenerator} and fill in the parameters"
     */
    BindingGenerator getBindingGenerator();

    class BindingGenerator {

        private static final int NO_LAYOUT = -300;
        private static final int NO_VARIABLE = -600;
        private static final int NO_STYLE = 0;

        private int layout = NO_LAYOUT;
        private int bindingVariableId = NO_VARIABLE;
        private int styleId = NO_STYLE;

        /**
         * A generator object used by the adapter that handles linking and ids for your AnyItem
         */
        public BindingGenerator(@LayoutRes int layout, int bindingVariableId) {
            this(layout, bindingVariableId, NO_STYLE);
        }

        /**
         * A generator object used by the adapter that handles linking and ids for your AnyItem
         */
        public BindingGenerator(@LayoutRes int layout, int bindingVariableId, int styleId) {
            this.layout = layout;
            this.bindingVariableId = bindingVariableId;
            this.styleId = styleId;
        }

        public int getLayout() {
            return layout;
        }

        BindingViewHolder getViewBindingViewHolder(ViewGroup parent) {
            if (layout == NO_LAYOUT || layout == 0) {
                throw new IllegalArgumentException(this.getClass().getSimpleName() + " must have a @LayoutRes assigned!");
            } else {
                LayoutInflater layoutInflater = LayoutInflater.from(getThemedContext(parent.getContext()));
                View root = DataBindingUtil.inflate(layoutInflater, layout, parent, false).getRoot();
                root.setTag(BindingViewHolder.LAYOUT_TAG, layout);
                return createBindingViewHolder(root);
            }
        }

        @NonNull
        BindingViewHolder createBindingViewHolder(View root) {
            return new BindingViewHolder(root, bindingVariableId);
        }

        private Context getThemedContext(Context parentContext) {
            Context newContext = parentContext;

            if (styleId != NO_STYLE) {
                newContext = StyleUtils.getContextThemeWrapper(parentContext, styleId);
            }

            return newContext;
        }
    }
}