package beans;

import business.ItemsService;
import business.models.MonitorableModel;
import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Named
@RequestScoped
public class GetChartItemBean implements Serializable {

    @Inject
    ItemsService service;

    private LineChartModel lineModel2;

    @PostConstruct
    public void init() {
        createLineModels();
    }

    public LineChartModel getLineModel2() {
        return lineModel2;
    }

    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();
        List<MonitorableModel> results = service.getSpecific("RTX 2060");

        results.forEach(res -> {
            ChartSeries chart = new ChartSeries();
            chart.setLabel(res.url);

            res.states.forEach(state -> {
                chart.set(state.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        Integer.parseInt(state.price.replace(".", "")));
            });

            model.addSeries(chart);
        });

//        ChartSeries emag = new ChartSeries();
//        emag.setLabel("Emag");
//        emag.set("2004", Integer.parseInt(price));
//        emag.set("2005", 4000);
//        emag.set("2006", 440);
//        emag.set("2007", 1500);
//        emag.set("2008", 250);

//        ChartSeries altex = new ChartSeries();
//        altex.setLabel("Altex");
//        altex.set("2004", 5200);
//        altex.set("2005", 6000);
//        altex.set("2006", 1100);
//        altex.set("2007", 900);
//        altex.set("2008", 1200);

//        model.addSeries(chart);
//        model.addSeries(altex);

        return model;
    }

    private void createLineModels() {
        lineModel2 = initCategoryModel();
        lineModel2.setTitle("Price History Chart");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Date"));
        Axis yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Price");
        yAxis.setMin(0);
        yAxis.setMax(7000);
    }


}