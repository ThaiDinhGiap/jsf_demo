package bean;

import model.Opentalk;
import service.OpentalkService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel; 
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
@Named
@RequestScoped
public class ReportBean extends Parent implements Serializable{
    private static final long serialVersionUID = 1L;

    private PieChartModel pieModel;

    @Inject
    private OpentalkService opentalkService;
    
    @PostConstruct
    public void init() {
    	System.out.println("ReportBean has been constructed");
        createPieModel();
    }

	@PreDestroy
	public void destroy() {
	    System.out.println("ReportBean is being destroyed");
	}

    private void createPieModel() {
        pieModel = new PieChartModel();

        List<Opentalk> opentalks = opentalkService.getAll();
        System.out.println("Opentalks: " + (opentalks == null ? "null" : opentalks.size()));

        Map<String, Long> statusCount = opentalks.stream()
                .collect(Collectors.groupingBy(Opentalk::getStatus, Collectors.counting()));

        for (Map.Entry<String, Long> entry : statusCount.entrySet()) {
            pieModel.set(entry.getKey(), entry.getValue());
        }

        pieModel.setTitle("Báo cáo OpenTalk theo trạng thái");
        pieModel.setLegendPosition("e"); 
        pieModel.setShowDataLabels(true);
        pieModel.setSeriesColors("ff6384,36a2eb,ffce56,4bc0c0"); 
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }
}