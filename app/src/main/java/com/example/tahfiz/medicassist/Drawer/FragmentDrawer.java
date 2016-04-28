package com.example.tahfiz.medicassist.Drawer;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.example.tahfiz.medicassist.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tahfiz on 28/4/2016.
 */
public class FragmentDrawer extends Fragment{

    private static String[] titles = null;
    private FragmentDrawerListener drawListener;
    private RecyclerView recyclerView;
    private NavigationDrawerAdapter adapter;
    private DrawerLayout drawerLayout;
    private View containerView;
    private ActionBarDrawerToggle drawerToggle;

    public void setDrawListener(FragmentDrawerListener listener){
        this.drawListener = listener;
    }

    public static List<NavDrawerItem> getData(){
        List<NavDrawerItem> data = new ArrayList<>();

        //prepare navigation drawer items
        for (String title : titles){
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(title);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navigation_drawer,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.drawerList);

        adapter = new NavigationDrawerAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawListener.onDrawerItemSelected(view,position);
                drawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    public void setUp(int fragmentID,DrawerLayout drawerLayout,final Toolbar toolbar){
        containerView = getActivity().findViewById(fragmentID);
        this.drawerLayout = drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
    }

    public static interface ClickListener{
        public void onClick(View view,int position);

        public void onLongClick(View view,int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{


        private final ClickListener clickListener;
        private final GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context,final RecyclerView recyclerView,final ClickListener clickListener){
            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(),e.getY());

                    if (child != null && clickListener != null){
                        clickListener.onLongClick(child,recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(),e.getY());

            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child,rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    public interface FragmentDrawerListener{
        public void onDrawerItemSelected(View view,int position);
    }
}
