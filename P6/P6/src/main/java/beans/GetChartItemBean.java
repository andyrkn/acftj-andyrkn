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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private List<LineChartModel> modelList;

    @PostConstruct
    public void init() {
        if(itemName!= null) {
            createLineModels();
        }
    }

    private ArrayList<LineChartModel> initCategoryModel() {
        List<MonitorableModel> results = service.getSpecific(itemName);
        ArrayList<LineChartModel> list = new ArrayList<>();

        results.forEach(res -> {
             LineChartModel model = new LineChartModel();
            ChartSeries chart = new ChartSeries();
            chart.setLabel("Price");

            res.states.forEach(state -> {
                chart.set(state.date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        Integer.parseInt(state.price.replace(".", "")));
            });

            model.addSeries(chart);
            model.setTitle(itemName);
            model.setLegendPosition("e");
            model.setShowPointLabels(true);
            model.getAxes().put(AxisType.X, new CategoryAxis("Date"));
            Axis yAxis = model.getAxis(AxisType.Y);
            yAxis.setLabel("Price");
            yAxis.setMin(0);
            yAxis.setMax(7000);

            list.add(model);
        });

        return list;
    }

    private void createLineModels() {
        modelList = initCategoryModel();
    }

    public void action(String itemName) throws IOException {
        this.itemName = itemName;
        navBean.toItemChart();
        this.createLineModels();
    }

    public LineChartModel getLineModel2() {
        return lineModel2;
    }

    public List<LineChartModel> getModelList() {
        return modelList;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
