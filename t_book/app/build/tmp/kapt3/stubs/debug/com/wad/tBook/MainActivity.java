package com.wad.tBook;

import java.lang.System;

@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0003:;<B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u0004J\u000e\u0010+\u001a\u00020\'2\u0006\u0010,\u001a\u00020)J\b\u0010-\u001a\u00020%H\u0002J\"\u0010.\u001a\u00020%2\u0006\u0010/\u001a\u00020)2\u0006\u00100\u001a\u00020)2\b\u00101\u001a\u0004\u0018\u000102H\u0014J\u0012\u00103\u001a\u00020%2\b\u00104\u001a\u0004\u0018\u000105H\u0014J\b\u00106\u001a\u00020%H\u0014J\b\u00107\u001a\u00020%H\u0014J\u0010\u00108\u001a\u00020%2\u0006\u00109\u001a\u00020)H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R \u0010\u0015\u001a\b\u0018\u00010\u0016R\u00020\u0000X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\b\"\u0004\b\u001d\u0010\nR\u001a\u0010\u001e\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\b\"\u0004\b \u0010\nR \u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0012\"\u0004\b#\u0010\u0014\u00a8\u0006="}, d2 = {"Lcom/wad/tBook/MainActivity;", "Landroidx/fragment/app/FragmentActivity;", "()V", "TAG", "", "accountFragment", "Landroidx/fragment/app/Fragment;", "getAccountFragment", "()Landroidx/fragment/app/Fragment;", "setAccountFragment", "(Landroidx/fragment/app/Fragment;)V", "analysisFragment", "getAnalysisFragment", "setAnalysisFragment", "fragmentList", "", "", "getFragmentList", "()Ljava/util/List;", "setFragmentList", "(Ljava/util/List;)V", "mViewPagerAdapter", "Lcom/wad/tBook/MainActivity$ViewPagerAdapter;", "getMViewPagerAdapter", "()Lcom/wad/tBook/MainActivity$ViewPagerAdapter;", "setMViewPagerAdapter", "(Lcom/wad/tBook/MainActivity$ViewPagerAdapter;)V", "pipelineFragment", "getPipelineFragment", "setPipelineFragment", "settingFragment", "getSettingFragment", "setSettingFragment", "titleList", "getTitleList", "setTitleList", "formatToggleButtonTab", "", "mToggleButton", "Landroid/widget/ToggleButton;", "resId", "", "txt", "getTabView", "position", "initView", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRestart", "onStart", "setTabShow", "id", "TabClickListener", "TabViewOnPageChangeListener", "ViewPagerAdapter", "app_debug"})
public final class MainActivity extends androidx.fragment.app.FragmentActivity {
    private final java.lang.String TAG = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<java.lang.String> titleList;
    @org.jetbrains.annotations.NotNull()
    private androidx.fragment.app.Fragment accountFragment;
    @org.jetbrains.annotations.NotNull()
    private androidx.fragment.app.Fragment pipelineFragment;
    @org.jetbrains.annotations.NotNull()
    private androidx.fragment.app.Fragment analysisFragment;
    @org.jetbrains.annotations.NotNull()
    private androidx.fragment.app.Fragment settingFragment;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<java.lang.Object> fragmentList;
    @org.jetbrains.annotations.Nullable()
    private com.wad.tBook.MainActivity.ViewPagerAdapter mViewPagerAdapter;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getTitleList() {
        return null;
    }
    
    public final void setTitleList(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.fragment.app.Fragment getAccountFragment() {
        return null;
    }
    
    public final void setAccountFragment(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.fragment.app.Fragment getPipelineFragment() {
        return null;
    }
    
    public final void setPipelineFragment(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.fragment.app.Fragment getAnalysisFragment() {
        return null;
    }
    
    public final void setAnalysisFragment(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.fragment.app.Fragment getSettingFragment() {
        return null;
    }
    
    public final void setSettingFragment(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Object> getFragmentList() {
        return null;
    }
    
    public final void setFragmentList(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Object> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.wad.tBook.MainActivity.ViewPagerAdapter getMViewPagerAdapter() {
        return null;
    }
    
    public final void setMViewPagerAdapter(@org.jetbrains.annotations.Nullable()
    com.wad.tBook.MainActivity.ViewPagerAdapter p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    @java.lang.Override()
    protected void onRestart() {
    }
    
    @java.lang.Override()
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable()
    android.content.Intent data) {
    }
    
    /**
     * 初始化方法
     * 设置TabLayout
     * 创建ViewPagerAdapter对象
     * 关联Tablayout与ViewPager
     * 给每一个TabView设置点击监听事件
     * 给ViewPager设置pageChange改变的事件
     */
    private final void initView() {
    }
    
    private final void setTabShow(int id) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.widget.ToggleButton getTabView(int position) {
        return null;
    }
    
    public final void formatToggleButtonTab(@org.jetbrains.annotations.NotNull()
    android.widget.ToggleButton mToggleButton, int resId, @org.jetbrains.annotations.NotNull()
    java.lang.String txt) {
    }
    
    public MainActivity() {
        super();
    }
    
    /**
     * tabLayout中TablView的点击监听，
     * 更改当前显示的Fragment
     */
    @kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016\u00a8\u0006\u0007"}, d2 = {"Lcom/wad/tBook/MainActivity$TabClickListener;", "Landroid/view/View$OnClickListener;", "(Lcom/wad/tBook/MainActivity;)V", "onClick", "", "p0", "Landroid/view/View;", "app_debug"})
    final class TabClickListener implements android.view.View.OnClickListener {
        
        @java.lang.Override()
        public void onClick(@org.jetbrains.annotations.Nullable()
        android.view.View p0) {
        }
        
        public TabClickListener() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J \u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0016J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0006H\u0016\u00a8\u0006\r"}, d2 = {"Lcom/wad/tBook/MainActivity$TabViewOnPageChangeListener;", "Landroidx/viewpager/widget/ViewPager$OnPageChangeListener;", "(Lcom/wad/tBook/MainActivity;)V", "onPageScrollStateChanged", "", "state", "", "onPageScrolled", "position", "positionOffset", "", "positionOffsetPixels", "onPageSelected", "app_debug"})
    final class TabViewOnPageChangeListener implements androidx.viewpager.widget.ViewPager.OnPageChangeListener {
        
        @java.lang.Override()
        public void onPageScrollStateChanged(int state) {
        }
        
        @java.lang.Override()
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        
        @java.lang.Override()
        public void onPageSelected(int position) {
        }
        
        public TabViewOnPageChangeListener() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nH\u0016R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u000e"}, d2 = {"Lcom/wad/tBook/MainActivity$ViewPagerAdapter;", "Landroidx/fragment/app/FragmentPagerAdapter;", "fm", "Landroidx/fragment/app/FragmentManager;", "(Lcom/wad/tBook/MainActivity;Landroidx/fragment/app/FragmentManager;)V", "getFm", "()Landroidx/fragment/app/FragmentManager;", "setFm", "(Landroidx/fragment/app/FragmentManager;)V", "getCount", "", "getItem", "Landroidx/fragment/app/Fragment;", "position", "app_debug"})
    public final class ViewPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {
        @org.jetbrains.annotations.Nullable()
        private androidx.fragment.app.FragmentManager fm;
        
        @org.jetbrains.annotations.Nullable()
        public final androidx.fragment.app.FragmentManager getFm() {
            return null;
        }
        
        public final void setFm(@org.jetbrains.annotations.Nullable()
        androidx.fragment.app.FragmentManager p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public androidx.fragment.app.Fragment getItem(int position) {
            return null;
        }
        
        @java.lang.Override()
        public int getCount() {
            return 0;
        }
        
        public ViewPagerAdapter(@org.jetbrains.annotations.NotNull()
        androidx.fragment.app.FragmentManager fm) {
            super(null);
        }
    }
}