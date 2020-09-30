import com.codahale.metrics.ConsoleReporter

object Metrics {

  import java.util.concurrent.TimeUnit
  import com.codahale.metrics.MetricRegistry

  private val metrics = new MetricRegistry
  private val counter = metrics.meter("requests-counter")
  private val errors = metrics.meter("requests-error")

  private val reporter = ConsoleReporter.forRegistry(metrics)
    .convertRatesTo(TimeUnit.SECONDS)
    .convertDurationsTo(TimeUnit.MILLISECONDS)
    .build

  def start(): Unit = reporter.start(10, TimeUnit.SECONDS)
  def stop(): Unit = reporter.stop()

  def count(): Unit = counter.mark()

  def error(): Unit = errors.mark()

}
