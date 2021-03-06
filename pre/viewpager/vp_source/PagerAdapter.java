/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.viewpager.widget;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Base class providing the adapter to populate pages inside of
 * a {@link ViewPager}.  You will most likely want to use a more
 * specific implementation of this, such as
 * {@link androidx.fragment.app.FragmentPagerAdapter} or
 * {@link androidx.fragment.app.FragmentStatePagerAdapter}.
 *
 * <p>When you implement a PagerAdapter, you must override the following methods
 * at minimum:</p>
 * <ul>
 * <li>{@link #instantiateItem(ViewGroup, int)}</li>
 * <li>{@link #destroyItem(ViewGroup, int, Object)}</li>
 * <li>{@link #getCount()}</li>
 * <li>{@link #isViewFromObject(View, Object)}</li>
 * </ul>
 *
 * <p>PagerAdapter is more general than the adapters used for
 * {@link android.widget.AdapterView AdapterViews}. Instead of providing a
 * View recycling mechanism directly ViewPager uses callbacks to indicate the
 * steps taken during an update. A PagerAdapter may implement a form of View
 * recycling if desired or use a more sophisticated method of managing page
 * Views such as Fragment transactions where each page is represented by its
 * own Fragment.</p>
 *
 * <p>ViewPager associates each page with a key Object instead of working with
 * Views directly. This key is used to track and uniquely identify a given page
 * independent of its position in the adapter. A call to the PagerAdapter method
 * {@link #startUpdate(ViewGroup)} indicates that the contents of the ViewPager
 * are about to change. One or more calls to {@link #instantiateItem(ViewGroup, int)}
 * and/or {@link #destroyItem(ViewGroup, int, Object)} will follow, and the end
 * of an update will be signaled by a call to {@link #finishUpdate(ViewGroup)}.
 * By the time {@link #finishUpdate(ViewGroup) finishUpdate} returns the views
 * associated with the key objects returned by
 * {@link #instantiateItem(ViewGroup, int) instantiateItem} should be added to
 * the parent ViewGroup passed to these methods and the views associated with
 * the keys passed to {@link #destroyItem(ViewGroup, int, Object) destroyItem}
 * should be removed. The method {@link #isViewFromObject(View, Object)} identifies
 * whether a page View is associated with a given key object.</p>
 *
 * <p>A very simple PagerAdapter may choose to use the page Views themselves
 * as key objects, returning them from {@link #instantiateItem(ViewGroup, int)}
 * after creation and adding them to the parent ViewGroup. A matching
 * {@link #destroyItem(ViewGroup, int, Object)} implementation would remove the
 * View from the parent ViewGroup and {@link #isViewFromObject(View, Object)}
 * could be implemented as <code>return view == object;</code>.</p>
 *
 * <p>PagerAdapter supports data set changes. Data set changes must occur on the
 * main thread and must end with a call to {@link #notifyDataSetChanged()} similar
 * to AdapterView adapters derived from {@link android.widget.BaseAdapter}. A data
 * set change may involve pages being added, removed, or changing position. The
 * ViewPager will keep the current page active provided the adapter implements
 * the method {@link #getItemPosition(Object)}.</p>
 */
/**
* PagerAdapter是用于“将多个页面填充到ViewPager”的适配器的一个基类，大多数情况下，
* 你们可能更倾向于使用一个实现了PagerAdapter并且更加具体的适配器，
* 例如FragmentPagerAdapter或者FragmentStatePagerAdapter。

* 当你实现一个PagerAdapter时，你至少需要重写下面的几个方法：

* instantiateItem(ViewGroup, int)
* destroyItem(ViewGroup, int, Object)
* getCount()
* isViewFromObject(View, Object)
* PagerAdapter比很多AdapterView的适配器更加通用。ViewPager使用回调机制来显示一个更新步骤，
* 不是直接使用视图回收机制。如果需要时，PagerAdapter也可以实现视图回收方法，
* 或者直接使用一种更加巧妙的方法来管理页面，比如直接使用能够管理自身事务的Fragment。

* ViewPager并不直接管理页面，而是通过一个key将每个页面联系起来。
* 这个key用来跟踪和唯一标识一个给定的页面，且该key独立于adapter之外。
* PagerAdapter中的startUpdate(ViewGroup)方法一旦被执行，
* 就说明ViewPager的内容即将开始改变。紧接着，instantiateItem(ViewGroup, int)
* 和/或destroyItem(ViewGroup, int, Object)方法将会被执行，
* 然后finishUpdate(ViewGroup)的执行就意味着这一次刷新的完成。当finishUpdate(ViewGroup)方法执行完时，
* 与instantiateItem(ViewGroup, int)方法返回的key相对应的视图将会被加入到父ViewGroup中，
* 而与传递给destroyItem(ViewGroup, int, Object)方法的key相对应的视图将会被移除。
* isViewFromObject(View, Object)方法则判断一个视图是否与一个给定的key相对应。

* 一个简单的PagerAdapter会选择将视图本身作为key，在将视图创建并加入父ViewGroup之后
* 通过instantiateItem(ViewGroup, int)返回。这种情况下，
* destroyItem(ViewGroup, int, Object) 的实现方法只需要将View从ViewGroup中移除即可，
* 而isViewFromObject(View, Object)的实现方法可以直接写成return view == object;。

* PagerAdapter支持数据集的改变。数据集的改变必须放在主线程中，
* 并且在结束时调用notifyDataSetChanged()方法，这与通过BaseAdapter适配的AdapterView类似。
* 一个数据集的改变包含了页面的添加、移除或者位移。
* ViewPager可以通过在适配器中实现getItemPosition(Object)方法来保持当前页面处于运行状态。
*/
public abstract class PagerAdapter {
    //被观察者
    private final DataSetObservable mObservable = new DataSetObservable();
    private DataSetObserver mViewPagerObserver;

    public static final int POSITION_UNCHANGED = -1;
    public static final int POSITION_NONE = -2;

    /**
     * Return the number of views available.
     */
    public abstract int getCount();

    /**
     * Called when a change in the shown pages is going to start being made.
     * @param container The containing View which is displaying this adapter's
     * page views.
     */
    public void startUpdate(@NonNull ViewGroup container) {
        startUpdate((View) container);
    }

    /**
     * Create the page for the given position.  The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate(ViewGroup)}.
     *
     * @param container The containing View in which the page will be shown.
     * @param position The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not
     * need to be a View, but can be some other container of the page.
     */
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return instantiateItem((View) container, position);
    }

    /**
     * Remove a page for the given position.  The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate(ViewGroup)}.
     *
     * @param container The containing View from which the page will be removed.
     * @param position The page position to be removed.
     * @param object The same object that was returned by
     * {@link #instantiateItem(View, int)}.
     */
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        destroyItem((View) container, position, object);
    }

    /**
     * Called to inform the adapter of which item is currently considered to
     * be the "primary", that is the one show to the user as the current page.
     * This method will not be invoked when the adapter contains no items.
     *
     * @param container The containing View from which the page will be removed.
     * @param position The page position that is now the primary.
     * @param object The same object that was returned by
     * {@link #instantiateItem(View, int)}.
     */
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        setPrimaryItem((View) container, position, object);
    }

    /**
     * Called when the a change in the shown pages has been completed.  At this
     * point you must ensure that all of the pages have actually been added or
     * removed from the container as appropriate.
     * @param container The containing View which is displaying this adapter's
     * page views.
     */
    public void finishUpdate(@NonNull ViewGroup container) {
        finishUpdate((View) container);
    }

    /**
     * Called when a change in the shown pages is going to start being made.
     * @param container The containing View which is displaying this adapter's
     * page views.
     *
     * @deprecated Use {@link #startUpdate(ViewGroup)}
     */
    @Deprecated
    public void startUpdate(@NonNull View container) {
    }

    /**
     * Create the page for the given position.  The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate(ViewGroup)}.
     *
     * @param container The containing View in which the page will be shown.
     * @param position The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not
     * need to be a View, but can be some other container of the page.
     * 
     * @deprecated Use {@link #instantiateItem(ViewGroup, int)}
     */
    /**
    * 创建指定位置的页面视图。适配器有责任增加即将创建的View视图到这里给定的container中，这是为了确保在finishUpdate(viewGroup)返回时this is be done!
    * 返回值：返回一个代表新增视图页面的Object（Key），这里没必要非要返回视图本身，也可以这个页面的其它容器。其实我的理解是可以代表当前页面的任意值，只要你可以与你增加的View一一对应即可，比如position变量也可以做为Key

    * 有两个操作，一个是原视图的移除（不再显示的视图），另一个是新增显示视图（即将显示的视图）
    * 往PageView里添加自己需要的page。同时注意它还有个返回值object，这就是那个id
    * 这个view的id是不是这个object。
    *        谷歌官方推荐把view当id用，所以常规的instantiateItem（）函数的返回值是你自己定义的view，
    *        而isViewFromObject（）的返回值是view == object。
    */
    @Deprecated
    @NonNull
    public Object instantiateItem(@NonNull View container, int position) {
        throw new UnsupportedOperationException(
                "Required method instantiateItem was not overridden");
    }

    /**
     * Remove a page for the given position.  The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate(View)}.
     *
     * @param container The containing View from which the page will be removed.
     * @param position The page position to be removed.
     * @param object The same object that was returned by
     * {@link #instantiateItem(View, int)}.
     *
     * @deprecated Use {@link #destroyItem(ViewGroup, int, Object)}
     */
    /**
    * 移除一个给定位置的页面。适配器有责任从容器中删除这个视图。这是为了确保在finishUpdate(viewGroup)返回时视图能够被移除
    */
    @Deprecated
    public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
        throw new UnsupportedOperationException("Required method destroyItem was not overridden");
    }

    /**
     * Called to inform the adapter of which item is currently considered to
     * be the "primary", that is the one show to the user as the current page.
     *
     * @param container The containing View from which the page will be removed.
     * @param position The page position that is now the primary.
     * @param object The same object that was returned by
     * {@link #instantiateItem(View, int)}.
     *
     * @deprecated Use {@link #setPrimaryItem(ViewGroup, int, Object)}
     */
    @Deprecated
    public void setPrimaryItem(@NonNull View container, int position, @NonNull Object object) {
    }

    /**
     * Called when the a change in the shown pages has been completed.  At this
     * point you must ensure that all of the pages have actually been added or
     * removed from the container as appropriate.
     * @param container The containing View which is displaying this adapter's
     * page views.
     *
     * @deprecated Use {@link #finishUpdate(ViewGroup)}
     */
    @Deprecated
    public void finishUpdate(@NonNull View container) {
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    /**
    * ViewPager里面对每个页面的管理是key-value形式的，也就是说每个page都有个对应的id（id是object类型），
    * 需要对page操作的时候都是通过id来完成的
    * 看下addNewItem方法，会找到instantiateItem的使用方法，注意这里的mItems变量。然后你再搜索下isViewFromObject，会发现其被infoForChild方法调用，返回值是ItemInfo。再去看下ItemInfo的结构，其中有一个object对象，该值就是instantiateItem返回的。

    *也就是说，ViewPager里面用了一个mItems(ArrayList)来存储每个page的信息(ItemInfo)，当界面要展示或者发生变化时，需要依据page的当前信息来调整，但此时只能通过view来查找，所以只能遍历mItems通过比较view和object来找到对应的ItemInfo。
    */
    public abstract boolean isViewFromObject(@NonNull View view, @NonNull Object object);

    /**
     * Save any instance state associated with this adapter and its pages that should be
     * restored if the current UI state needs to be reconstructed.
     *
     * @return Saved state for this adapter
     */
    @Nullable
    public Parcelable saveState() {
        return null;
    }

    /**
     * Restore any instance state associated with this adapter and its pages
     * that was previously saved by {@link #saveState()}.
     *
     * @param state State previously saved by a call to {@link #saveState()}
     * @param loader A ClassLoader that should be used to instantiate any restored objects
     */
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
    }

    /**
     * Called when the host view is attempting to determine if an item's position
     * has changed. Returns {@link #POSITION_UNCHANGED} if the position of the given
     * item has not changed or {@link #POSITION_NONE} if the item is no longer present
     * in the adapter.
     *
     * <p>The default implementation assumes that items will never
     * change position and always returns {@link #POSITION_UNCHANGED}.
     *
     * @param object Object representing an item, previously returned by a call to
     *               {@link #instantiateItem(View, int)}.
     * @return object's new position index from [0, {@link #getCount()}),
     *         {@link #POSITION_UNCHANGED} if the object's position has not changed,
     *         or {@link #POSITION_NONE} if the item is no longer present.
     */
    public int getItemPosition(@NonNull Object object) {
        return POSITION_UNCHANGED;
    }

    /**
     * This method should be called by the application if the data backing this adapter has changed
     * and associated views should update.
     */
    public void notifyDataSetChanged() {
        synchronized (this) {
            if (mViewPagerObserver != null) {
                mViewPagerObserver.onChanged();
            }
        }
        mObservable.notifyChanged();
    }

    /**
     * Register an observer to receive callbacks related to the adapter's data changing.
     *
     * @param observer The {@link android.database.DataSetObserver} which will receive callbacks.
     */
    public void registerDataSetObserver(@NonNull DataSetObserver observer) {
        mObservable.registerObserver(observer);
    }

    /**
     * Unregister an observer from callbacks related to the adapter's data changing.
     *
     * @param observer The {@link android.database.DataSetObserver} which will be unregistered.
     */
    public void unregisterDataSetObserver(@NonNull DataSetObserver observer) {
        mObservable.unregisterObserver(observer);
    }

    void setViewPagerObserver(DataSetObserver observer) {
        synchronized (this) {
            mViewPagerObserver = observer;
        }
    }

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Nullable
    public CharSequence getPageTitle(int position) {
        return null;
    }

    /**
     * Returns the proportional width of a given page as a percentage of the
     * ViewPager's measured width from (0.f-1.f]
     *
     * @param position The position of the page requested
     * @return Proportional width for the given page position
     */
    public float getPageWidth(int position) {
        return 1.f;
    }
}
