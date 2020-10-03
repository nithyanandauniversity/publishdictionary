import java.time.LocalDateTime

import com.codahale.metrics.ConsoleReporter
import com.codahale.metrics.jmx.JmxReporter

object Metrics {

  import java.util.concurrent.TimeUnit
  import com.codahale.metrics.MetricRegistry

  private val metrics = new MetricRegistry
  private val counter = metrics.meter("requests-counter")
  private val counter2 = metrics.meter("requests-counter2")
  private val errors = metrics.meter("requests-error")
  private val timer = metrics.timer(s"request-timer-${LocalDateTime.now()}")

  private lazy val timerContext = timer.time()
  private val reporter = ConsoleReporter.forRegistry(metrics)
    .convertRatesTo(TimeUnit.SECONDS)
    .convertDurationsTo(TimeUnit.MILLISECONDS)
    .build

  private val jmxReporter = JmxReporter.forRegistry(metrics).build

  def start(): Unit = {
    timerContext
    jmxReporter.start()
    reporter.start(10, TimeUnit.SECONDS)
  }

  def stop(): Unit = {
    val t = timerContext.stop()
    println(s"Total Time: ${t}")
    jmxReporter.stop()
    reporter.report()
    reporter.stop()
  }

  def count(): Unit = counter.mark()

  def error(): Unit = errors.mark()
}
