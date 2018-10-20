package storm.example1;

import java.util.Map;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class Bolt extends BaseBasicBolt {

//	private PrintWriter writer;

	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		String fileName = stormConf.get("fileToWrite").toString();
	}

	@Override
	public void cleanup() {
//		this.writer.close();
	}

	public void execute(Tuple input, BasicOutputCollector collector) {
		String symbol = input.getValue(0).toString();
		
		String price = input.getString(1);
		String prevClose = input.getString(2);
		
		Boolean gain = true;
		
		
		collector.emit(new Values(symbol, price, gain));
//		writer.println(symbol +"," + price + "," + gain);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("company", "price", "gain"));
	}

}
