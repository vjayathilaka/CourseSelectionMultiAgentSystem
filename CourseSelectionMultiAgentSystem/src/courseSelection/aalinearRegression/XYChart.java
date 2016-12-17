/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseSelection.aalinearRegression;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.List;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Hiranthi
 */
public class XYChart extends JFrame{
    
    public XYChart(String chartTitle, List<Float> xList, List<Float> yList){
        
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle ,
         "X" ,
         "Y" ,
         createDataset(xList, yList) ,
         PlotOrientation.VERTICAL ,
         true , true , false);
        
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      final XYPlot plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.RED );
      renderer.setSeriesPaint( 1 , Color.GREEN );
      renderer.setSeriesPaint( 2 , Color.YELLOW );
      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
      renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
      renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel );
    }
    
    public XYDataset createDataset(List<Float> xList, List<Float> yList){
      final XYSeries xyData = new XYSeries( "Data Chart" );          

      for(int i=0; i<xList.size(); i++){
          xyData.add(xList.get(i), yList.get(i));
      }
        
      final XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries(xyData);          
      return dataset;
    }
}
