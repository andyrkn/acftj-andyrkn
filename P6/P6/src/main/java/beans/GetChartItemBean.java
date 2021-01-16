package beans;

import business.ItemsService;
import business.models.MonitorableModel;
import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Named
@ApplicationScoped
public class GetChartItemBean implements Serializable {

    @Inject
    NavBean navBean;

    @Inject
    ItemsService service;

    private String itemName;

    private LineChartModel lineModel2;

    @PostConstruct
    public void init() {
        if(itemName!= null) {
            createLineModels();
        }
    }

    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();
        List<MonitorableModel> results = service.getSpecific(itemName);

        results.forEach(res -> {
            ChartSeries chart = new ChartSeries();
            chart.setLabel(res.url);

            res.states.forEach(state -> {
                chart.set(state.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        Integer.parseInt(state.price.replace(".", "")));
            });

            model.addSeries(chart);
        });

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

    public void action(String itemName) throws IOException {
        this.itemName = itemName;
        navBean.toItemChart();
        this.createLineModels();
    }

    public LineChartModel getLineModel2() {
        return lineModel2;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}