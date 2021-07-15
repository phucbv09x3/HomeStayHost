package open.your.eyes.miichibaseapp.data.scheduler

import io.reactivex.Scheduler

interface ISchedulerProvider {
    val computation: Scheduler
    val io: Scheduler
    val ui: Scheduler
}