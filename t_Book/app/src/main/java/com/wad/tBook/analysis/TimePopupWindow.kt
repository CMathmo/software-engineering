//package com.wad.tBook.analysis
//
//import android.animation.Animator
//import android.animation.ObjectAnimator
//import android.animation.ValueAnimator
//import android.app.Activity
//import android.content.Context
//import android.view.Gravity
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import android.widget.PopupWindow
//import android.widget.RelativeLayout
//import android.widget.Toast
//import com.wad.tBook.R
//import io.reactivex.schedulers.Schedulers
//
//class TimePopupWindow : PopupWindow, View.OnClickListener {
//    private lateinit var rootView: View
//    private lateinit var contentView: RelativeLayout
//    private lateinit var mContext: Activity
//
//    constructor(mContext: Activity) : super() {
//        this.mContext = mContext
//    }
//
//    fun showMoreWindow(anchor: View) {
//        //获取LayoutInflater 对象 在kotlin中优点之一，不用指定定义的变量的类型，运行代码
//        //的时候，代码会更具其值确定其类型
//        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        rootView = inflater.inflate(R.layout.fragment_write_posts, null)
//        //获取屏幕的宽高
//        val height = mContext.windowManager.defaultDisplay.height
//        val width = mContext.windowManager.defaultDisplay.width
//        //PopupWindow设置布局
//        setContentView(rootView)
//        this.width = width
//        //高度使用减掉状态栏的高度
//        //this.height = (height - ScreenUtils.getStatusHeight(mContext)).toInt()
//        //找到我们的根布局节点
//        contentView = rootView.findViewById(R.id.contentView)
//        //取消按钮
//        val close = rootView.findViewById<LinearLayout>(R.id.ll_close)
//        close.setBackgroundColor(0xFFFFFFFF.toInt())
//        close.setOnClickListener(this)
//        showAnimation(contentView)
//        //setBackgroundDrawable(mContext.resources.getDrawable(R.drawable.translucence_with_white))
//        isOutsideTouchable = true
//        isFocusable = true
//        showAtLocation(anchor, Gravity.BOTTOM, 0, 0)
//
//    }
//
//    private fun showAnimation(contentView: ViewGroup) {
//        //contentView 是我们刚才获得的根节点 获取在这个节点中的所有的 子节点的个数
//        var childCount: Int = contentView.childCount
//        //KLogger.i("view", "    " + childCount)
//        //kotlin 的循环的一种方法（有好几种，每一种都有对应的优点和不足）
//        for (i in 0..childCount) {
//            val view = contentView.getChildAt(i)
//            if (view != null) {
//                if (view.id == R.id.ll_close) { //忽略取消控件
//                    continue
//                }
//                //设置所有一级菜单的点击事件
//                view.setOnClickListener(this)
//                view.visibility = View.INVISIBLE
//                //延迟显示每个子视图
//                //使用rxjava和rxAndroid 完成延迟操作 在不同时间中开始动画 出现弹出时先后的效果
////                Observable.timer(i * 50.toLong(), java.util.concurrent.TimeUnit.MILLISECONDS)
////                    .subscribeOn(Schedulers.newThread())
////                    //.observeOn(AndroidSchedulers.mainThread())
////                    .subscribe {
////                        view.visibility = View.VISIBLE
////                        /**
////                         * ofFloat()
////                         * arg1:view 对象
////                         * arg2:动画改变的类型
////                         * arg3:args4: 依次是开始透明度和结束透明度
////                         */
////                        val fadeAnim: ValueAnimator = ObjectAnimator.ofFloat(view, "translationY", 600F, 0F)
////                        //设置动画的时间
////                        fadeAnim.duration = 300
////                        fadeAnim.start()
////                    }
//            }
//
//        }
//
//    }
//
//    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.video_window -> {
//                Toast.makeText(mContext, "video_window", Toast.LENGTH_LONG).show()
//            }
//            R.id.link_window -> {
//                Toast.makeText(mContext, "video_window", Toast.LENGTH_LONG).show()
//            }
//            R.id.voice_window -> {
//                Toast.makeText(mContext, "video_window", Toast.LENGTH_LONG).show()
//            }
//            R.id.photo_window -> {
//                Toast.makeText(mContext, "video_window", Toast.LENGTH_LONG).show()
//            }
//            R.id.satin_window -> {
//                Toast.makeText(mContext, "video_window", Toast.LENGTH_LONG).show()
//            }
//            R.id.music_window -> {
//                Toast.makeText(mContext, "video_window", Toast.LENGTH_LONG).show()
//            }
//            R.id.ll_close -> {
//                if (isShowing) {
//                    closeAnimation(contentView)
//                }
//            }
//        }
//    }
//
//    /**
//     * 关闭动画
//     */
//    private fun closeAnimation(contentView: RelativeLayout) {
//        for (i in 0..contentView.childCount) {
//            val view = contentView.getChildAt(i)
//            if (view != null) {
//                if (view.id == R.id.ll_close) { //忽略取消控件
//                    continue
//                }
//                //设置所有一级菜单的点击事件
//                view.setOnClickListener(this)
//                view.visibility = View.INVISIBLE
//
////                //延迟显示每个子视图
////                Observable.timer(((contentView.childCount - i - 1) * 30).toLong(),
////                    java.util.concurrent.TimeUnit.MILLISECONDS
////                )
////                    .subscribeOn(Schedulers.newThread())
////                    .observeOn(AndroidSchedulers.mainThread())
////                    .subscribe {
////                        view.visibility = View.VISIBLE
////                        val fadeAnim: ValueAnimator = ObjectAnimator.ofFloat(view, "translationY", 0F, 600F)
////                        fadeAnim.duration = 200
////
////                        fadeAnim.run {
////                            start()
////                            addListener(object : Animator.AnimatorListener {
////                                override fun onAnimationRepeat(animation: Animator?) {
////                                    //动画循环播放的时候
////                                }
////
////                                override fun onAnimationEnd(animation: Animator?) {
////                                    //动画结束的时候
////                                    view.visibility = View.INVISIBLE
////                                }
////
////                                override fun onAnimationCancel(animation: Animator?) {
////                                    //动画被取消的时候
////                                }
////
////                                override fun onAnimationStart(animation: Animator?) {
////                                    //动画开始的时候调用
////                                }
////
////                            })
////                        }
////
////                    }
////                //将个别的取出来 再延时显示 制造效果
////                if (view.id == R.id.video_window) {
////                    Observable.timer(((contentView.childCount - i) * 30 + 80).toLong(),
////                        java.util.concurrent.TimeUnit.MILLISECONDS
////                    )
////                        .subscribeOn(Schedulers.newThread())
////                        .observeOn(AndroidSchedulers.mainThread())
////                        .subscribe({
////                            dismiss()
////                        })
////                }
//            }
//        }
//    }
//}