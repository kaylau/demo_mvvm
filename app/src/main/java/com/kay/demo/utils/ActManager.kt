package com.kay.demo.utils

import android.app.Activity
import android.text.TextUtils
import java.lang.ref.WeakReference
import java.util.*


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/26 16:34
 * @Description:
 */
class ActManager {
    companion object {

        private val TAG = ActManager::class.java.simpleName

        /**
         * 堆栈管理对象
         */
        private val STACK = ActivityStack()

        /**
         * 当前显示的Activity
         */
        private var topActivity: WeakReference<Activity?>? = null

        /**
         * 额外操作
         */
        private var operations: ExtraOperations? = null

        /**
         * push this activity to stack
         */
        fun push(activity: Activity) {
            LogUtils.e(TAG, "push = $activity")
            STACK.pushToStack(activity)
        }

        /**
         * pop top activity from stack
         */
        fun pop() {
            val activity = STACK.popFromStack()
            activity?.finish()
            LogUtils.e(TAG, "pop = $activity")
        }

        /**
         * remove this activity from stack, maybe is null
         */
        fun remove(activity: Activity) {
            LogUtils.e(TAG, "remove = $activity")
            STACK.removeFromStack(activity)
        }

        /**
         * finish the top activity
         */
        fun finish() {
            if (null != topActivity && null != topActivity?.get()) {
                finish(topActivity?.get())
            }
        }

        /**
         * finish the activity
         */
        private fun finish(activity: Activity?) {
            activity?.finish()
        }

        /**
         * pop activities until this Activity
         */
        fun <T : Activity?> popUntil(clazz: Class<T>?): T? {
            if (clazz != null) {
                while (!STACK.isEmpty) {
                    val activity = STACK.popFromStack()
                    if (activity != null) {
                        // if (clazz.isAssignableFrom(activity.getClass())) {
                        if (TextUtils.equals(clazz.name, activity.javaClass.name)) {
                            return activity as T
                        }
                        finish(activity)
                    }
                }
            }
            return null
        }

        fun setOperations(operations: ExtraOperations?) {
            Companion.operations = operations
        }

        /**
         * 最后一次尝试退出的时间戳
         */
        private var lastExitPressedMills: Long = 0

        /**
         * 距上次尝试退出允许的最大时间差
         */
        private const val MAX_DOUBLE_EXIT_MILLS: Long = 800

        /**
         * 退出APP
         */
        fun onExit() {
            val now = System.currentTimeMillis()
            if (now <= lastExitPressedMills + MAX_DOUBLE_EXIT_MILLS) {
                finishAll()
                if (null != operations) {
                    operations?.onExit()
                }
                System.exit(0)
            } else {
                if (null != peek()) {
                    CustomToast.getInstance(peek()).show("Press again to exit the app")
                }
                lastExitPressedMills = now
            }
        }

        /**
         * 当APP退出的时候，结束所有Activity
         */
        fun finishAll() {
            LogUtils.e(TAG, ">>>>>>>>>>>>>>>>>>> Exit <<<<<<<<<<<<<<<<<<<")
            while (!STACK.isEmpty) {
                val activity = STACK.popFromStack()
                if (activity != null) {
                    LogUtils.e(TAG, activity.toString())
                    activity.finish()
                    if (null != operations) {
                        operations?.onActivityFinish(activity)
                    }
                }
            }
            LogUtils.e(TAG, ">>>>>>>>>>>>>>>>>>> Complete <<<<<<<<<<<<<<<<<<<")
        }

        /**
         * 获取当前显示的activity
         */
        fun peek(): Activity? {
            return if (null != topActivity && null != topActivity?.get()) {
                topActivity?.get()
            } else {
                STACK.peekFromStack()
            }
        }

        fun setTopActivity(topActivity: Activity?) {
            Companion.topActivity = WeakReference(topActivity)
        }

        /**
         * activity堆栈，用以管理APP中的所有activity
         */
        private class ActivityStack {
            // activity堆对象
            private val activityStack = Stack<WeakReference<Activity>>()

            /**
             * 堆是否为空
             */
            val isEmpty: Boolean
                get() = activityStack.isEmpty()

            /**
             * 向堆中push此activity
             */
            fun pushToStack(activity: Activity) {
                activityStack.push(WeakReference(activity))
            }

            /**
             * 从堆栈中pop出一个activity对象
             */
            fun popFromStack(): Activity? {
                while (!activityStack.isEmpty()) {
                    val weak = activityStack.pop()
                    val activity = weak.get()
                    if (activity != null) {
                        return activity
                    }
                }
                return null
            }

            /**
             * 从堆栈中查看一个对象，且不会pop
             */
            fun peekFromStack(): Activity? {
                while (!activityStack.isEmpty()) {
                    val weak = activityStack.peek()
                    val activity = weak.get()
                    if (activity != null) {
                        return activity
                    } else {
                        activityStack.pop()
                    }
                }
                return null
            }

            /**
             * 从堆栈中删除指定对象
             */
            fun removeFromStack(activity: Activity): Boolean {
                for (weak in activityStack) {
                    val act = weak.get()
                    if (act === activity) {
                        return activityStack.remove(weak)
                    }
                }
                return false
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 接口
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////
    // 接口
    ///////////////////////////////////////////////////////////////////////////
    interface ExtraOperations {
        /**
         * APP退出时需要额外处理的事情，例如广播的反注册，服务的解绑
         */
        fun onExit()

        /**
         * activity 销毁时需要额外处理的事情，例如finish动画等
         */
        fun onActivityFinish(activity: Activity?)
    }
}
