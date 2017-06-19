package my.vaadin.XXSProject.views.logView;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.VerticalLayout;

import my.vaadin.XXSProject.databaseClasses.LogTableConnector;
import my.vaadin.XXSProject.databaseEntities.Exercise;
import my.vaadin.XXSProject.databaseEntities.Log;

public class LogShowChart extends VerticalLayout {

	private Chart chart;
	private Exercise currentExercise;
	private List<Log> logsToShow;

	public LogShowChart() {
		logsToShow = new ArrayList<>();
		this.chart = new Chart(ChartType.COLUMN);
		this.addComponent(chart);
	}

	public void showLogsForExercise(Exercise exerciseToShow) {
		this.removeComponent(this.chart);
		this.chart = new Chart(ChartType.COLUMN);

		this.currentExercise = exerciseToShow;

		// Bestehende Daten clearen
		if (!this.logsToShow.isEmpty()) {
			this.logsToShow.clear();
		}

		LogTableConnector logProvider = new LogTableConnector();
		this.logsToShow = logProvider.getLogsForUsernameWorkoutPlanExercise(this.currentExercise.getFkUsername(),
				this.currentExercise.getFkWorkoutplanName(), this.currentExercise.getName());

		com.vaadin.addon.charts.model.Configuration conf = chart.getConfiguration();

		conf.setTitle("Logs für Übung " + this.currentExercise.getName());
		conf.setSubTitle("Workload (Wiederholungen x Gewicht x Sätze)");

		XAxis x = new XAxis();

		for (Log exlog : this.logsToShow) {
			x.addCategory(exlog.getDate().toString());
		}
		conf.addxAxis(x);

		YAxis y = new YAxis();
		y.setMin(0);
		y.setTitle("Werte");
		conf.addyAxis(y);

		Legend legend = new Legend();
		legend.setLayout(LayoutDirection.VERTICAL);
		legend.setBackgroundColor(new SolidColor("#FFFFFF"));
		legend.setAlign(HorizontalAlign.LEFT);
		legend.setVerticalAlign(VerticalAlign.TOP);
		legend.setX(100);
		legend.setY(70);
		legend.setFloating(true);
		legend.setShadow(true);
		conf.setLegend(legend);

		Tooltip tooltip = new Tooltip();
		tooltip.setFormatter("this.x +': '+ this.y");
		conf.setTooltip(tooltip);

		PlotOptionsColumn plot = new PlotOptionsColumn();
		plot.setPointPadding(0.2);
		plot.setBorderWidth(0);

		ListSeries workloadSeries = new ListSeries();

		workloadSeries.setName("Workload");

		for (Log exLog : this.logsToShow) {
			workloadSeries.addData(exLog.getReps() * exLog.getWeight() * exLog.getSets());
		}

		conf.addSeries(workloadSeries);

		chart.drawChart(conf);
		this.addComponent(chart);
	}
}
