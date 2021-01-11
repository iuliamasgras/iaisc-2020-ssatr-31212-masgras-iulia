package net.agten.heatersimulator.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.Format;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.agten.heatersimulator.Main;
import net.agten.heatersimulator.controller.Simulator;
import net.agten.heatersimulator.controller.dto.CAAPID16Parameters;
import net.agten.heatersimulator.controller.dto.CAAPID32Parameters;
import net.agten.heatersimulator.controller.dto.FuzzyParameters;
import net.agten.heatersimulator.controller.dto.PID32Parameters;
import net.agten.heatersimulator.controller.dto.SimulationParameters;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;


public class GraphWindow {
	private final static Format DECIMAL_FORMAT = new DecimalFormat("#.00");
	private final static int MAX_TEMP = 300;
	
	private final String[] ALGORITHMS = {"PID32", "CAAPID32", "CAAPID16","Fuzzy"};
	
	private final JFrame frame;
	private final AboutDialog aboutDialog;
	private final ChartPanel chartPanel;
	private final JComboBox algorithmComboBox;
	private final JLabel statusLabel;
	private final SimulationParameters params;
	private final PID32Parameters pidParams;
	private final CAAPID32Parameters caapidParams;
	private final CAAPID16Parameters caapid16Params;
	private final FuzzyParameters fuzzyParams;
	private final Simulator simulator = new Simulator();
	
	public GraphWindow() {
		frame = new JFrame();
		aboutDialog = new AboutDialog(frame);
		params = new SimulationParameters();
		pidParams = new PID32Parameters();
		caapidParams = new CAAPID32Parameters();
		caapid16Params = new CAAPID16Parameters();
		fuzzyParams=new FuzzyParameters();
		chartPanel = createChartPanel();
		statusLabel = new JLabel();
		algorithmComboBox = new JComboBox(ALGORITHMS);
		updateChart();
		initializeComponents(chartPanel);
	}
	
	private void initializeComponents(ChartPanel chartPanel) {
		frame.setLayout(new BorderLayout());
		frame.setTitle(Main.NAME + " v" + Main.VERSION);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(chartPanel, BorderLayout.LINE_START);
		frame.add(createParameterPanel(), BorderLayout.LINE_END);
		frame.add(createStatusBar(), BorderLayout.SOUTH);
		frame.setJMenuBar(createMenuBar());
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;
		
		menu = new JMenu("File");
		/*menuItem = new JMenuItem("Load...");
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Save...");
		menu.add(menuItem);
		menu.addSeparator();*/
		
		menuItem = new JMenuItem("Quit");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hide();
				System.exit(0);
			}
		});
		menu.add(menuItem);
		menuBar.add(menu);
		
		menu = new JMenu("Help");
		menuItem = new JMenuItem("About...");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aboutDialog.show();
			}
		});
		menu.add(menuItem);
		menuBar.add(menu);
		
		return menuBar;
	}
	
	private JPanel createStatusBar() {
		JPanel statusBar = new JPanel();
		statusBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		statusBar.add(statusLabel);
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		return statusBar;
	}
	
	private ChartPanel createChartPanel() {
		JFreeChart chart = ChartFactory.createXYLineChart(
				"PID Controller Simulation", 
				"Time", 
				"Temperature", 
				new XYSeriesCollection(), 
				PlotOrientation.VERTICAL, 
				false, false, false);
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
	
	private JPanel createParameterPanel() {
		JPanel parameterPanel = new JPanel();
		parameterPanel.setLayout(new BoxLayout(parameterPanel, BoxLayout.Y_AXIS));
		parameterPanel.add(createSimulationPanel());
		parameterPanel.add(createEnvironmentPanel());
		parameterPanel.add(createHeaterPanel());
		parameterPanel.add(createThermistorPanel());
		parameterPanel.add(createAlgorithmPanel());
		parameterPanel.add(Box.createVerticalGlue());
		frame.add(parameterPanel, BorderLayout.LINE_END);
		return parameterPanel;
	}
	
	private JPanel createSimulationPanel() {
		JPanel environmentPanel = new JPanel();
		environmentPanel.setLayout(new BoxLayout(environmentPanel, BoxLayout.Y_AXIS));
		environmentPanel.setBorder(BorderFactory.createTitledBorder("Simulation"));
		environmentPanel.add(createSliderPanel("Nb seconds:", params.getNbSeconds(), 1, 1200,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				params.setNbSeconds((int)Math.round(newValue));
			}
		}));
		environmentPanel.add(createSliderPanel("Target temp:", params.getTargetTemperature(), 0, 300,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				params.setTargetTemperature(newValue);
			}
		}));

		return environmentPanel;
	}
	
	private JPanel createEnvironmentPanel() {
		JPanel environmentPanel = new JPanel();
		environmentPanel.setLayout(new BoxLayout(environmentPanel, BoxLayout.Y_AXIS));
		environmentPanel.setBorder(BorderFactory.createTitledBorder("Environment"));
		environmentPanel.add(createSliderPanel("Temperature:", params.getEnvironmentTemperature(), 0, 50,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				params.setEnvironmentTemperature(newValue);
			}
		}));

		return environmentPanel;
	}
	
	private JPanel createHeaterPanel() {
		JPanel heaterPanel = new JPanel();
		heaterPanel.setBorder(BorderFactory.createTitledBorder("Heater"));
		heaterPanel.setLayout(new BoxLayout(heaterPanel, BoxLayout.Y_AXIS));
		
		heaterPanel.add(createSliderPanel("Power:", params.getHeaterPower(), 1, 300, 
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				params.setHeaterPower(newValue);
			}
		}));
		heaterPanel.add(createSliderPanel("Thermal mass:", params.getHeaterThermalMass(), 0, 100, 10,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				params.setHeaterThermalMass(newValue);
			}
		}));
		heaterPanel.add(createSliderPanel("Heat transfer coef.:", params.getHeaterHeatTransferCoeffient(), 0, 1, 100, 
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				params.setHeaterHeatTransferCoeffient(newValue);
			}
		}));
		return heaterPanel;
	}
	
	private JPanel createThermistorPanel() {
		JPanel thermistorPanel = new JPanel();
		thermistorPanel.setBorder(BorderFactory.createTitledBorder("Thermistor"));
		thermistorPanel.setLayout(new BoxLayout(thermistorPanel, BoxLayout.Y_AXIS));
		
		thermistorPanel.add(createSliderPanel("Lag:", params.getThermistorLag(), 0, 20,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				params.setThermistorLag((int)Math.round(newValue));
			}
		}));
		thermistorPanel.add(createSliderPanel("R0:", params.getThermistorR0(), 100, 1000000, 
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				params.setThermistorR0(newValue);
			}
		}));
		thermistorPanel.add(createSliderPanel("T0:", params.getThermistorT0(), 0, 100,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				params.setThermistorT0(newValue);
			}
		}));
		thermistorPanel.add(createSliderPanel("Beta:", params.getThermistorBeta(), 100, 10000,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				params.setThermistorBeta(newValue);
			}
		}));
		thermistorPanel.add(createSliderPanel("R2:", params.getThermistorR2(), 100, 1000000,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				params.setThermistorR2(newValue);
			}
		}));
		thermistorPanel.add(createSliderPanel("Noise:", params.getThermistorNoiseGain(), 0, 100, 10,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				params.setThermistorNoiseGain(newValue);
			}
		}));
		return thermistorPanel;
	}
	
	private JPanel createAlgorithmPanel() {
		final JPanel algorithmPanel = new JPanel();
		algorithmPanel.setBorder(BorderFactory.createTitledBorder("Algorithm"));
		algorithmPanel.setLayout(new BoxLayout(algorithmPanel, BoxLayout.Y_AXIS));
		algorithmPanel.add(algorithmComboBox);
		
		final JPanel algorithmParamsPanel = new JPanel();
		final CardLayout cl = new CardLayout();
		algorithmParamsPanel.setLayout(cl);
		algorithmParamsPanel.add(createPIDPanel(), "PID32");
		algorithmParamsPanel.add(createCAAPIDPanel(), "CAAPID32");
		algorithmParamsPanel.add(createCAAPID16Panel(), "CAAPID16");
		algorithmParamsPanel.add(createFuzzyPanel(), "Fuzzy");
		algorithmPanel.add(algorithmParamsPanel);
		
		algorithmComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String algo = (String)algorithmComboBox.getSelectedItem();
				cl.show(algorithmParamsPanel, algo);
				updateChart();
			}
		});
		
		return algorithmPanel;
	}
	
	private JPanel createPIDPanel() {
		JPanel algorithmPanel = new JPanel();
		algorithmPanel.setLayout(new BoxLayout(algorithmPanel, BoxLayout.Y_AXIS));
		algorithmPanel.add(createSliderPanel("P:", pidParams.getpGain(), 0, 1000,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				pidParams.setpGain((int)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("I:", pidParams.getiGain(), 0, 100,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				pidParams.setiGain((int)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("D:", pidParams.getdGain(), 0, 500,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				pidParams.setdGain((int)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("Windup guard:", pidParams.getIntegralWindupGuard(), 0, 5000,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				pidParams.setIntegralWindupGuard((int)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("Output divisor:", pidParams.getOutputDivisor(), -1024, -1,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				pidParams.setOutputDivisor((int)Math.round(newValue));
			}
		}));
		return algorithmPanel;
	}
	
	private JPanel createCAAPIDPanel() {
		JPanel algorithmPanel = new JPanel();
		algorithmPanel.setLayout(new BoxLayout(algorithmPanel, BoxLayout.Y_AXIS));
		algorithmPanel.add(createSliderPanel("P:", caapidParams.getpGain(), 0, 1000,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				caapidParams.setpGain((int)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("I:", caapidParams.getiGain(), 0, 100,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				caapidParams.setiGain((int)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("D:", caapidParams.getdGain(), 0, 500,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				caapidParams.setdGain((int)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("Output divisor:", caapidParams.getOutputDivisor(), -1024, -1,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				caapidParams.setOutputDivisor((int)Math.round(newValue));
			}
		}));
		return algorithmPanel;
	}
	
	private JPanel createCAAPID16Panel() {
		JPanel algorithmPanel = new JPanel();
		algorithmPanel.setLayout(new BoxLayout(algorithmPanel, BoxLayout.Y_AXIS));
		algorithmPanel.add(createSliderPanel("P:", caapid16Params.getpGain(), 0, 1000,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				caapid16Params.setpGain((short)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("I:", caapid16Params.getiGain(), 0, 100,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				caapid16Params.setiGain((short)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("D:", caapid16Params.getdGain(), 0, 500,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				caapid16Params.setdGain((short)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("Output divisor:", caapid16Params.getOutputDivisor(), -1024, -1,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				caapid16Params.setOutputDivisor((short)Math.round(newValue));
			}
		}));
		return algorithmPanel;
	}
	
	private JPanel createFuzzyPanel() {
		JPanel algorithmPanel = new JPanel();
		algorithmPanel.setLayout(new BoxLayout(algorithmPanel, BoxLayout.Y_AXIS));
		algorithmPanel.add(createSliderPanel("P:", fuzzyParams.getpGain(), 0, 1000,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				fuzzyParams.setpGain((short)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("I:", fuzzyParams.getiGain(), 0, 100,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				fuzzyParams.setiGain((short)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("D:", fuzzyParams.getdGain(), 0, 500,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				fuzzyParams.setdGain((short)Math.round(newValue));
			}
		}));
		algorithmPanel.add(createSliderPanel("Output divisor:", fuzzyParams.getOutputDivisor(), -1024, -1,
				new ValueChangedAction() {
			@Override
			public void valueChanged(double newValue) {
				fuzzyParams.setOutputDivisor((short)Math.round(newValue));
			}
		}));
		return algorithmPanel;
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void hide() {
		frame.setVisible(false);
	}
	
	private void updateChart() {
		long startTime = System.currentTimeMillis();
		JFreeChart chart = ChartFactory.createXYLineChart(
				"PID Controller Simulation", 
				"Time", 
				"Temperature", 
				generateDataset(), 
				PlotOrientation.VERTICAL, 
				false, false, false);
		ValueAxis rangeAxis = chart.getXYPlot().getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0, MAX_TEMP);
		chartPanel.setChart(chart);
		long endTime = System.currentTimeMillis();
		
		setStatus("Generated chart in "+(endTime-startTime)+"ms.");
	}
	
	private XYDataset generateDataset() {
		String algo = (String)algorithmComboBox.getSelectedItem();
		if ("PID32".equals(algo)) {
			return simulator.runSimulation(params, pidParams);
		} else if ("CAAPID32".equals(algo)) {
			return simulator.runSimulation(params, caapidParams);
		} else if ("CAAPID16".equals(algo)){
			return simulator.runSimulation(params, caapid16Params);
		} else if ("Fuzzy".equals(algo)){
			return simulator.runSimulation(params, fuzzyParams);
		} else {
			return null;
		}
	}
	
	private JPanel createSliderPanel(String labelText, double initialValue, int min, int max, 
			final ValueChangedAction action) {
		return createSliderPanel(labelText, initialValue, min, max, 1, action);
	}
	
	private JPanel createSliderPanel(String labelText, double initialValue, int min, int max, final int divisor,
			final ValueChangedAction action) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel textInputPanel = new JPanel();
		textInputPanel.setLayout(new BoxLayout(textInputPanel, BoxLayout.X_AXIS));
		JLabel label = new JLabel(labelText);
		textInputPanel.add(label);
		final JFormattedTextField text = new JFormattedTextField(DECIMAL_FORMAT);
		text.setMaximumSize(new Dimension(80,30));
		text.setValue(initialValue);
		textInputPanel.add(text);
		textInputPanel.add(Box.createHorizontalGlue());
		panel.add(textInputPanel);
		
		final JSlider slider = new JSlider(min*divisor, max*divisor);
		slider.setValue((int)Math.round(initialValue));
		panel.add(slider);
		
		// Action/change listeners
		text.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double value = ((Number)text.getValue()).doubleValue();
				action.valueChanged(value);
				slider.setValue((int)Math.round(value)*divisor);
				updateChart();
			}
		});
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				double value = ((double)slider.getValue()) / divisor;
				action.valueChanged(value);
				text.setValue(value);
				updateChart();
			}
		});
		
		return panel;
	}
	
	private interface ValueChangedAction {
		void valueChanged(double newValue);
	}
	
	private void setStatus(String status) {
		statusLabel.setText(status);
	}
}
