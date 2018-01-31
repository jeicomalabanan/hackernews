package com.propertyguru.hackernews.util;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Provides various threading schedulers.
 * REFERENCE: https://proandroiddev.com/understanding-rxjava-subscribeon-and-observeon-744b0c6a41ea
 */
public class SchedulersFacade {

    @Inject
    public SchedulersFacade() {
    }

    /**
     * This is backed by an unbounded thread pool. It is used for non CPU-intensive I/O type work including
     * interaction with the file system, performing network calls, database interactions, etc. This
     * thread pool is intended to be used for asynchronously performing blocking IO.
     *
     * @return
     */
    public Scheduler io() {
        return Schedulers.io();
    }

    /**
     * This is backed by a bounded thread pool with size up to the number of available processors.
     * It is used for computational or CPU-intensive work such as resizing images, processing large
     * data structures, etc. Be careful: when you allocate more computation threads than available
     * cores, performance will degrade due to context switching and thread creation overhead as
     * threads vie for processors’ time.
     *
     * @return
     */
    public Scheduler computation() {
        return Schedulers.computation();
    }

    /**
     * Creates a new thread for each unit of work scheduled. This scheduler is expensive as new thread
     * is spawned every time and no reuse happens.
     *
     * @return
     */
    public Scheduler newThread() {
        return Schedulers.newThread();
    }

    /**
     * Main thread or AndroidSchedulers.mainThread() is provided by the RxAndroid extension library
     * to RxJava. Main thread (also known as UI thread) is where user interaction happens. Care
     * should be taken not to overload this thread to prevent janky non-responsive UI or, worse,
     * Application Not Responding” (ANR) dialog.
     *
     * @return
     */
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    /**
     * This scheduler is backed by a single thread executing tasks sequentially in the order requested.
     *
     * @return
     */
    public Scheduler single() {
        return Schedulers.single();
    }

    /**
     * Executes tasks in a FIFO (First On, First Out) manner by one of the participating worker threads.
     * It’s often used when implementing recursion to avoid growing the call stack.
     *
     * @return
     */
    public Scheduler trampoline() {
        return Schedulers.trampoline();
    }
}