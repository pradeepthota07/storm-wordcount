package storm.storm_wordcount;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class WordCounter extends BaseBasicBolt {

	Map<String, Integer> counters;
	Integer id;
	String name;
	String fileName;

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {

		String word = input.getString(0);
		if (!counters.containsKey(word)) {
			counters.put(word, 1);
		} else {
			Integer c = counters.get(word) + 1;
			counters.put(word, c);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		this.counters = new HashMap<>();
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
		this.fileName = stormConf.get("dirToWrite").toString() + "output" + "-" + name + id + ".txt";
	}

	@Override
	public void cleanup() {
		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			
			for(Map.Entry<String, Integer> entry : counters.entrySet()) {
				writer.println(entry.getKey()+ " - "+entry.getValue());
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
	}
	

}
